package com.creditcard.repository;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.creditcard.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testSaveUser() {
		// given
		User user = User.builder().username("anotherfernanda").build();
		
		// when
		User user2 = userRepository.save(user);
		
		// then
		assertEquals(entityManager.getId(user2), user.getId());		
	}

	@Test
	public void testFindByUsername_shouldReturnUser_whenExists() {
		// given
		User user = entityManager.merge(User.builder().username("anotheradmin").build());
		
		// when
		Optional<User> user2 = userRepository.findByUsername("anotheradmin");
		
		// then
		assertEquals(user2.get().getUsername(), user.getUsername());
		assertEquals(user2.get().getId(), user.getId());
	}

	@Test
	public void testFindByUsername_shouldReturnEmpty_whenUserDoesntExist() {
		// when
		Optional<User> user = userRepository.findByUsername("userWithNoName");
		
		// then
		assertEquals(user, Optional.empty() );
	}

}
