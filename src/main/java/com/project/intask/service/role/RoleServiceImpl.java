package com.project.intask.service.role;

import com.project.intask.model.Role;
import com.project.intask.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role createByName(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.getRoleByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Cannot find role with name: " + name));
    }
}
