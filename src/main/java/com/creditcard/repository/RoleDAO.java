package com.creditcard.repository;

import com.creditcard.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
