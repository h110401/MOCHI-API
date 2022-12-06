package com.mochi.service.user;

import com.mochi.model.user.User;
import com.mochi.service.IGenericService;

import java.util.List;

public interface IUserService extends IGenericService<User> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    User findByUsernameOrEmail(String search);
    User findByUsername(String username);

    List<User> search(String search);
}
