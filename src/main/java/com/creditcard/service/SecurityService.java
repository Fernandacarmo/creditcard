package com.creditcard.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;

public interface SecurityService {

    Optional<String> findLoggedInUsername();
    boolean hasAdminRole();
    void autologin(String username, String password);
    Authentication getAuthentication();
}
