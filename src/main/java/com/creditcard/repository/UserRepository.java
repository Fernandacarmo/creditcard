package com.creditcard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creditcard.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@SuppressWarnings("unchecked")
	User save(User user);
	
    Optional<User> findByUsername(String username);
}
