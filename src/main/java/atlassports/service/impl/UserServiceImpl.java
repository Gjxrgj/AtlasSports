package atlassports.service.impl;

import atlassports.enums.RoleEnum;
import atlassports.mappers.UserMapper;
import atlassports.model.Role;
import atlassports.model.User;
import atlassports.model.dto.PatchUserDto;
import atlassports.model.dto.UpsertUserDto;
import atlassports.model.dto.UserDto;
import atlassports.repository.RoleRepository;
import atlassports.repository.UserRepository;
import atlassports.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found."));
    }

    @Override
    public UserDto createUser(UpsertUserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        Role role = roleRepository.findByName(RoleEnum.CLIENT)
                .orElseThrow(() -> new RuntimeException("No role found with name CLIENT"));

        userDto.setRoleIds(new ArrayList<>(List.of(role.getId())));

        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("No authenticated user!");
        }

        return (User) authentication.getPrincipal();
    }

    @Override
    public UserDto getMe() {
            User me = getLoggedInUser();

        return userMapper.toDto(me);
    }

    @Override
    public ResponseEntity<UserDto> updateMe(PatchUserDto body) {
        User me = getLoggedInUser();

        if (body.getFirstName() != null) {
            me.setFirstName(body.getFirstName());
        }

        if (body.getLastName() != null) {
            me.setLastName(body.getLastName());
        }

        if (body.getEmail() != null) {
            me.setEmail(body.getEmail());
        }

        User savedUser = userRepository.save(me);

        String newToken = jwtService.generateToken(savedUser.getEmail());

        UserDetails userDetails = loadUserByUsername(savedUser.getEmail());
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + newToken)
                .body(userMapper.toDto(savedUser));
    }

}
