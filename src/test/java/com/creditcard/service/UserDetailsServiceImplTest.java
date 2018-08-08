package com.creditcard.service;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.creditcard.model.Role;
import com.creditcard.model.User;
import com.creditcard.repository.UserDAO;
import com.creditcard.util.RoleEnum;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
	
	@Mock
	private UserDAO userDAO;
	@InjectMocks
	UserDetailsServiceImpl userDetailsService;
	
	@Test
	public void testLoadUserByUsername_shouldReturnUserDetails_WhenUserExists() {
		// given
		Role roleUser = Role.builder().name(RoleEnum.ROLE_USER.getRole()).build();
		User user = User.builder().id(1L).username("fernanda").password("password")
				.roles(Stream.of(roleUser).collect(Collectors.toSet())).build();
		given(userDAO.findByUsername(anyString())).willReturn(Optional.of(user));
		
		// when
		UserDetails userDetails = userDetailsService.loadUserByUsername("fernanda");
		
		// then
		assertEquals(userDetails.getUsername(), user.getUsername());	
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsername_shouldReturnException_WhenUserDoesntExist() {
		// given
		given(userDAO.findByUsername(anyString())).willReturn(Optional.empty());
		
		// when
		userDetailsService.loadUserByUsername("fernanda");		
	}

}
