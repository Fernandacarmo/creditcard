package com.creditcard.service;

import java.util.Optional;

import com.creditcard.model.User;

public interface UserService {

    User save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsername();
}
