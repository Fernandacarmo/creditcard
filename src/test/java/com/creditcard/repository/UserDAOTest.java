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
public class UserDAOTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserDAO userDAO;

	@Test
	public void testSaveUser() {
		// given
		User user = User.builder().id(3L).username("anotherfernanda").build();
		
		// when
		User user2 = userDAO.save(user);
		
		// then
		assertEquals(entityManager.getId(user2), user.getId());
		
	}

	@Test
	public void testFindByUsername() {
		// given
		User user = entityManager.merge(User.builder().id(4L).username("anotheradmin").build());
		
		// when
		Optional<User> user2 = userDAO.findByUsername("anotheradmin");
		
		// then
		assertEquals(user2.get().getUsername(), user.getUsername());
	}

}
