package com.creditcard.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.creditcard.model.User;
import com.creditcard.repository.RoleDAO;
import com.creditcard.repository.UserDAO;
import com.creditcard.util.RoleEnum;

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
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Stream.of(roleDAO.findByName(RoleEnum.ROLE_USER.getRole()))
        		.collect(Collectors.toSet()));
        return userDAO.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public Optional<User> findByUsername() {
    	Optional<String> username = securityService.findLoggedInUsername();
    	if (username.isPresent()) {
            return userDAO.findByUsername(username.get());    		
    	} else {
    		return Optional.empty();
    	}
    }

}
