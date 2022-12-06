package com.mochi.model.user;

import com.mochi.dto.request.SignUpDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class UserFactory {

    @Value("${default.avatar}")
    private String defaultAvatar;

    public User build(SignUpDTO signUpDTO, Role role) {
        Collection<Role> roles = new HashSet<>();
        roles.add(role);
        return new User(
                null,
                signUpDTO.getFirstName() == null ? "" : signUpDTO.getFirstName().trim(),
                signUpDTO.getLastName().trim(),
                signUpDTO.getUsername(),
                signUpDTO.getEmail(),
                signUpDTO.getPassword(),
                defaultAvatar,
                roles,
                null
        );
    }
}
