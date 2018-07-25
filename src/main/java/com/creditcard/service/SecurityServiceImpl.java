package com.creditcard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements ISecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    @Qualifier(value="customAuthenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public String findLoggedInUsername() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User) {//(userDetails instanceof UserDetails) {
            return ((User) user).getUsername();//return ((UserDetails)userDetails).getUsername();
        } else if (user instanceof String) {
            return (String) user;
        } else {
            logger.debug("Can't find user logged in!");
            return "FAIL";
        }
    }

    @Override
    public void autologin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        } else {
            logger.debug(String.format("Auto login %s fail!!!!!", username));
        }
    }
}
