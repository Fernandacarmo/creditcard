package com.creditcard.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.creditcard.model.Role;
import com.creditcard.model.User;
import com.creditcard.repository.RoleRepository;
import com.creditcard.repository.UserRepository;
import com.creditcard.util.RoleEnum;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	
	@Mock
    private UserRepository userRepository;
	@Mock
    private RoleRepository roleRepository;
	@Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
    private SecurityService securityService;
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	private User user;
	private Role roleUser;
	
	@Before
	public void setUp() {	
		roleUser = Role.builder().name(RoleEnum.ROLE_USER.getRole()).build();
		user = User.builder().id(1L).username("fernanda").password("xyz")
				.roles(Stream.of(roleUser).collect(Collectors.toSet())).build();		
	}

	@Test
	public void testSave_shouldReturnUser_whenUserValid() {
		// given
		given(bCryptPasswordEncoder.encode(any(CharSequence.class))).willReturn("xyz");
		given(roleRepository.findByName(anyString())).willReturn(roleUser);
		given(userRepository.save(any(User.class))).willReturn(user);

		// when
		User user2 = userServiceImpl.save(user);
		
		// then
		assertEquals(user2.getPassword(), user.getPassword());
		assertEquals(user2.getRoles(), user.getRoles());
	}

	@Test
	public void testFindByUsernameString_shouldReturnUser_whenExists() {
		// given
		given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));

		// when
		Optional<User> user2 = userServiceImpl.findByUsername(user.getUsername());
		
		// then
		assertEquals(user2.get().getUsername(), user.getUsername());
	}

	@Test
	public void testFindByUsername_shouldReturnUser_WhenExists() {
		// given
		given(securityService.findLoggedInUsername()).willReturn(Optional.of("fernanda"));
		given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));
		
		// when
		Optional<User> user2 = userServiceImpl.findByUsername();
		
		// then
		assertEquals(user2.get().getUsername(), user.getUsername());
	}
	
	@Test
	public void testFindByUsername_shouldReturnEmpty_WhenDoesntExist() {
		// given
		given(securityService.findLoggedInUsername()).willReturn(Optional.empty());
		
		// when
		Optional<User> user2 = userServiceImpl.findByUsername();
		
		// then
		assertEquals(user2, Optional.empty());
	}
}
