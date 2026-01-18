package atlassports.service.impl;

import atlassports.model.Role;
import atlassports.repository.RoleRepository;
import atlassports.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleById(Long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with id " + id + " not found."));
    }
}
