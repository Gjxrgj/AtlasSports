package atlassports.api;

import atlassports.model.dto.AuthRequest;
import atlassports.model.dto.UpsertUserDto;
import atlassports.model.dto.UserDto;
import atlassports.service.UserService;
import atlassports.service.impl.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@RestController
@RequestMapping("${openapi.atlasSports.base-path:/api/auth}")
@AllArgsConstructor
public class AuthApiController implements AuthApi {

    private final NativeWebRequest request;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private JwtService jwtService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<String> authLoginPost(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken(authRequest.getEmail()));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserDto> authRegisterPost(@Valid @RequestBody UpsertUserDto body) {
        return ResponseEntity.ok(userService.createUser(body));
    }
}
