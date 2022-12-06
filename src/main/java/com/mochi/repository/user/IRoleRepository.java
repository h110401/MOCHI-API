package com.mochi.repository.user;

import com.mochi.model.user.Role;
import com.mochi.model.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName name);
}
