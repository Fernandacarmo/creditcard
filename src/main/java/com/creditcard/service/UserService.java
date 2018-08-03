package com.creditcard.service;

import com.creditcard.model.User;

public interface UserService {

    void save(User user);
    User findByUsername(String username);
    User findByUsername();
}
