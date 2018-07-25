package com.creditcard.service;

import com.creditcard.model.User;

public interface IUserService {

    void save(User user);
    User findByUsername(String username);
}
