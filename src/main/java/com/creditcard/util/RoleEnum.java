package com.creditcard.util;

public enum RoleEnum {
	
    ROLE_USER ("USER"),
    ROLE_SYSADMIN ("SYSADMIN")
    ;

	private final String role;
	
	private RoleEnum (String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
}
