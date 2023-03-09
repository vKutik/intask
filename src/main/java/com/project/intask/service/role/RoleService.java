package com.example.test.service.role;

import com.example.test.model.Role;

public interface RoleService {

    Role createByName(String name);

    Role getByName(String name);
}
