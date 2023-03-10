package com.project.intask.service.role;

import com.project.intask.model.Role;

public interface RoleService {

    Role createByName(String name);

    Role getByName(String name);
}
