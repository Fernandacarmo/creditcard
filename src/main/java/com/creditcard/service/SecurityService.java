package com.creditcard.service;

import org.springframework.security.core.Authentication;

public interface SecurityService {

    String findLoggedInUsername();
    boolean hasAdminRole();
    void autologin(String username, String password);
    Authentication getAuthentication();
}
