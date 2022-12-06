package com.mochi.service.user;

import com.mochi.model.user.Role;
import com.mochi.model.user.RoleName;

public interface IRoleService {
    Role save(Role role);

    Role findByName(RoleName name);

    void addRoleToUser(String username, RoleName roleName);

    void removeRoleFromUser(String username, RoleName roleName);
}
