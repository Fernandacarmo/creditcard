package com.creditcard.service;

import com.creditcard.model.User;
import com.creditcard.repository.RoleDAO;
import com.creditcard.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.creditcard.repository.RoleDAO.ROLE_USER;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Stream.of(roleDAO.findByName(ROLE_USER)).collect(Collectors.toSet()));
        userDAO.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
