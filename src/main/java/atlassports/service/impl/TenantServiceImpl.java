package atlassports.service.impl;

import atlassports.enums.EntityStatus;
import atlassports.mappers.TenantMapper;
import atlassports.model.Tenant;
import atlassports.model.User;
import atlassports.model.dto.TenantDto;
import atlassports.model.dto.UpsertTenantDto;
import atlassports.model.dto.UserDto;
import atlassports.repository.TenantRepository;
import atlassports.repository.UserRepository;
import atlassports.service.TenantService;
import atlassports.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper, UserRepository userRepository, UserService userService) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public TenantDto createTenant(UpsertTenantDto body) {
        User user = userRepository.findById(body.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User with id " + body.getUserId() + " doesn't exist."));
        Tenant tenant = tenantMapper.toEntity(body);
        tenant.setUser(user);

        Tenant savedTenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(savedTenant);
    }

    @Override
    public Page<TenantDto> getTenants(Pageable pageable, String searchQuery) {
        Specification<Tenant> spec = (root, query, cb) -> {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                return cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%");
            }
            return cb.conjunction();
        };

        return tenantRepository.findAll(spec, pageable)
                .map(tenantMapper::toDto);
    }


    @Override
    public TenantDto getTenant(Long tenantId) {
        Tenant tenant = findTenantByIdOrThrowException(tenantId);

        return tenantMapper.toDto(tenant);
    }

    @Override
    public TenantDto updateTenant(Long tenantId, UpsertTenantDto body) {
        Tenant tenant = findTenantByIdOrThrowException(tenantId);

        tenantMapper.toEntity(body, tenant);

        Tenant savedTenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(savedTenant);
    }

    @Override
    public TenantDto changeTenantStatus(Long tenantId, EntityStatus status) {
        Tenant tenant = findTenantByIdOrThrowException(tenantId);
        tenant.setStatus(status);
        Tenant savedTenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(savedTenant);
    }

    @Override
    public Tenant getCurrentTenant() {
        UserDto user = userService.getMe();

        return tenantRepository.findByUser_Id(user.getId()).orElseThrow(() -> new NoSuchElementException("Tenant for user with id: " + user.getId() + "not found."));
    }

    private Tenant findTenantByIdOrThrowException(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NoSuchElementException("Tenant with id " + tenantId + " doesn't exist."));
    }
}
