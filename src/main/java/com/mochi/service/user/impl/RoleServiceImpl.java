package com.mochi.service.user.impl;

import com.mochi.model.user.Role;
import com.mochi.model.user.RoleName;
import com.mochi.model.user.User;
import com.mochi.repository.user.IRoleRepository;
import com.mochi.repository.user.IUserRepository;
import com.mochi.service.user.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void addRoleToUser(String username, RoleName roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public void removeRoleFromUser(String username, RoleName roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        for (Role r : user.getRoles()) {
            if (Objects.equals(r.getId(), role.getId())) {
                user.getRoles().remove(r);
                break;
            }
        }
    }
}
