package com.creditcard.repository;

import com.creditcard.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {

    static String ROLE_USER = "USER";
    static String ROLE_SYSADMIN = "SYSADMIN";

    Role findByName(String name);
}
