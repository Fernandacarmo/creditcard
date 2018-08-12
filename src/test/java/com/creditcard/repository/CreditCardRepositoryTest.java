package com.creditcard.repository;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.creditcard.model.CreditCard;
import com.creditcard.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CreditCardRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private CreditCardRepository cardRepository;

	@Test
	public void testSaveCreditCard() {
		// given
		User user = User.builder().username("anotherfernanda").build();		
		CreditCard card = CreditCard.builder().number("3456789012").user(user).build();
		
		// when
		CreditCard card2 = cardRepository.save(card);
		
		// then
		assertEquals(entityManager.getId(card2), card.getId());
		assertEquals(entityManager.getId(card2.getUser()), user.getId());
	}

	@Test
	public void testFindByNumberAndUserId_shouldReturnCard_whenCardAndUserExist() {
		// given
		User user = entityManager.merge(User.builder().username("anotherfernanda").build());		
		CreditCard card = entityManager.merge(CreditCard.builder().number("3456789012").user(user).build());
		
		// when
		Optional<CreditCard> card2 = cardRepository.findByNumberAndUserId("3456789012", user.getId());
		
		// then
		assertEquals(card2.get().getNumber(), card.getNumber());
		assertEquals(card2.get().getUser().getId(), user.getId());
	}

	@Test
	public void testFindByNumberAndUserId_shouldReturnEmpty_whenUserDoesntExist() {
		// given
		CreditCard card = entityManager.merge(CreditCard.builder().number("3456789012").build());
		
		// when
		Optional<CreditCard> card2 = cardRepository.findByNumberAndUserId("3456789012", 100L);
		
		// then
		assertEquals(card2, Optional.empty());
	}

	@Test
	public void testFindByNumberAndUserId_shouldReturnEmpty_whenCardDoesntExist() {
		// given
		User user = entityManager.merge(User.builder().username("anotherfernanda").build());		
		
		// when
		Optional<CreditCard> card = cardRepository.findByNumberAndUserId("5555555555", user.getId());
		
		// then
		assertFalse(card.isPresent());
	}

	@Test
	public void testFindAllByNumberContaining_shouldReturnCards_whenExist() {
		// given
		User user = entityManager.merge(User.builder().username("anotherfernanda").build());		
		CreditCard card = entityManager.merge(CreditCard.builder().number("3456789012").user(user).build());
		CreditCard card2 = entityManager.merge(CreditCard.builder().number("4567890123").user(user).build());
		
		// when
		List<CreditCard> cardList = cardRepository.findAllByNumberContaining("456");
		
		// then
		assertThat(cardList.size(), is(2));
		assertTrue(cardList.contains(card));
		assertTrue(cardList.contains(card2));
	}

	@Test
	public void testFindAllByNumberContaining_shouldReturnEmpty_whenDoesntExist() {
		// when
		List<CreditCard> cardList = cardRepository.findAllByNumberContaining("88888");
		
		// then
		assertThat(cardList.size(), is(0));
	}

	@Test
	public void testFindAllByNumberContainingAndUserId_shouldReturnCards_whenTheyExist() {
		// given
		User user = entityManager.merge(User.builder().username("anotherfernanda").build());		
		CreditCard card = entityManager.merge(CreditCard.builder().number("3456789012").user(user).build());
		CreditCard card2 = entityManager.merge(CreditCard.builder().number("4567890123").user(user).build());
		
		// when
		List<CreditCard> cardList = cardRepository.findAllByNumberContainingAndUserId("456", user.getId());
		
		// then
		assertThat(cardList.size(), is(2));
		assertTrue(cardList.contains(card));
		assertTrue(cardList.contains(card2));
	}

	@Test
	public void testFindAllByNumberContainingAndUserId_shouldReturnEmpty_whenUserDoesntExist() {
		// given
		User user = entityManager.merge(User.builder().username("user1").build());		
		CreditCard card = entityManager.merge(CreditCard.builder().number("3456789012").user(user).build());
		CreditCard card2 = entityManager.merge(CreditCard.builder().number("4567890123").user(user).build());
		User user2 = entityManager.merge(User.builder().username("user2").build());		
		
		// when
		List<CreditCard> cardList = cardRepository.findAllByNumberContainingAndUserId("456", user2.getId());
		
		// then
		assertThat(cardList.size(), is(0));
	}
}
