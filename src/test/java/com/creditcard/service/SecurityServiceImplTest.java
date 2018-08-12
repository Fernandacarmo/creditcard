package com.creditcard.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration
@WithMockUser("fernanda")
public class SecurityServiceImplTest {
	
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @InjectMocks
    private SecurityServiceImpl securityServiceImpl;

	@Test
	public void testFindLoggedInUsername() {        
        // when
        Optional<String> username = securityServiceImpl.findLoggedInUsername();
		
        // then
        assertEquals(username.get(), "fernanda");
	}

	@Test
	public void testHasAdminRole() {
		// when
		boolean hasAdminRole = securityServiceImpl.hasAdminRole();
		
		// then
		assertFalse(hasAdminRole);
	}

	@Test
	public void testAutologin() {
		// given
        UserDetails userDetails = new User("fernanda", "password",
				Stream.of(new SimpleGrantedAuthority("ROLE_USER")).collect(Collectors.toSet()));
        given(userDetailsService.loadUserByUsername(anyString())).willReturn(userDetails);
        
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities());
		given(authenticationManager.authenticate(any(Authentication.class))).willReturn(usernamePasswordAuthenticationToken);
		
		// when
		securityServiceImpl.autologin("fernanda", "password");
		
		// then
		assertTrue(usernamePasswordAuthenticationToken.isAuthenticated());
	}
}
