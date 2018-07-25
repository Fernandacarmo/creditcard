package com.creditcard.repository;

import com.creditcard.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

public interface UserDAO extends JpaRepository<User, Long>{

    User findByUsername(String username);
}
