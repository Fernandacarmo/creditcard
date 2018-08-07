package com.creditcard.service;

import static com.creditcard.repository.RoleDAO.ROLE_USER;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.creditcard.model.User;
import com.creditcard.repository.RoleDAO;
import com.creditcard.repository.UserDAO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityService securityService;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Stream.of(roleDAO.findByName(ROLE_USER)).collect(Collectors.toSet()));
        userDAO.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public Optional<User> findByUsername() {
        return userDAO.findByUsername(securityService.findLoggedInUsername());
    }

}
