package com.creditcard.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.creditcard.model.User;
import com.creditcard.repository.RoleRepository;
import com.creditcard.repository.UserRepository;
import com.creditcard.util.RoleEnum;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityService securityService;

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Stream.of(roleRepository.findByName(RoleEnum.ROLE_USER.getRole()))
        		.collect(Collectors.toSet()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByUsername() {
    	Optional<String> username = securityService.findLoggedInUsername();
    	if (username.isPresent()) {
            return userRepository.findByUsername(username.get());    		
    	}
		return Optional.empty();
    }

}
