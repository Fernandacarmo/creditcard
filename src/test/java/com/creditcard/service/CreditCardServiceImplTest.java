package com.creditcard.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.creditcard.model.CreditCard;
import com.creditcard.model.Role;
import com.creditcard.model.User;
import com.creditcard.repository.CreditCardDAO;
import com.creditcard.util.RoleEnum;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardServiceImplTest {

	@Mock
	private CreditCardDAO creditCardDAO;
	@Mock
	private UserService userService;
	@Mock
	private SecurityService securityService;
	@InjectMocks
	private CreditCardServiceImpl cardServiceImpl;

	private CreditCard creditCard, oldCard;
	private User user, admin;
	private Role roleUser, roleAdmin;
	private Calendar calendar2018, calendar2017;

	@Before
	public void setUp() {
		roleUser = Role.builder().name(RoleEnum.ROLE_USER.getRole()).build();
		user = User.builder().id(1L).username("fernanda")
				.roles(Stream.of(roleUser).collect(Collectors.toSet())).build();
		given(userService.findByUsername()).willReturn(Optional.of(user));

		calendar2018 = new GregorianCalendar(2018, 0, 31);
		creditCard = CreditCard.builder().id(1L).number("1234567890").name("Fake Name")
				.expiryDate(calendar2018.getTime()).user(user).build();

		calendar2017 = new GregorianCalendar(2017, 0, 31);
		oldCard = CreditCard.builder().id(2L).number("1234567890").name("Fake Name 2")
				.expiryDate(calendar2017.getTime()).user(user).build();
	}

	@Test
	public void testSave_shouldSaveNewCard_whenHasNoCardWithSameNumber() {
		// given
		given(creditCardDAO.findByNumberAndUserId(anyString(), anyLong()))
		.willReturn(Optional.empty());
		given(creditCardDAO.save(any(CreditCard.class))).willReturn(creditCard);

		// when
		CreditCard card = cardServiceImpl.save(creditCard);

		// then
		assertEquals(card.getNumber(), "1234567890");
		assertEquals(card.getName(), "Fake Name");
		assertTrue(card.getExpiryDate().compareTo(calendar2018.getTime()) == 0);
		assertEquals(card.getUser().getId().longValue(), 1L);
	}

	@Test
	public void testSave_shouldUpdateOldCard_whenHasCardWithSameNumber() {
		// given
		given(creditCardDAO.findByNumberAndUserId(anyString(), anyLong()))
		.willReturn(Optional.of(oldCard));
		given(creditCardDAO.save(any(CreditCard.class))).willReturn(oldCard);

		// when
		CreditCard card = cardServiceImpl.save(creditCard);

		// then
		assertEquals(card.getNumber(), "1234567890");
		assertEquals(card.getName(), "Fake Name 2");
		assertTrue(card.getExpiryDate().compareTo(calendar2018.getTime()) == 0);
		assertEquals(card.getUser().getId().longValue(), 1L);
	}

	@Test
	public void testSave_shouldReturnNull_whenCannotFindUserLoggedIn() {
		// given
		given(userService.findByUsername()).willReturn(Optional.empty());

		// when
		CreditCard card = cardServiceImpl.save(creditCard);

		// then
		assertNull(card);
	}

	@Test
	public void testFindByNumberAndUserId_shouldReturnCard_WhenItExists() {
		// given
		given(creditCardDAO.findByNumberAndUserId(anyString(), anyLong()))
		.willReturn(Optional.of(creditCard));

		// when
		Optional<CreditCard> card = cardServiceImpl.findByNumberAndUserId("12", user.getId());

		// then
		assertTrue(card.get().getNumber().contains("12"));
	}

	@Test
	public void testFindAllByNumberContaining_ShouldReturnTwoCards_WhenHasAdminRole() {
		// given
		given(securityService.hasAdminRole()).willReturn(true);
		roleAdmin = Role.builder().name(RoleEnum.ROLE_SYSADMIN.getRole()).build();
		admin = User.builder().id(2L).username("fernanda 2")
				.roles(Stream.of(roleAdmin).collect(Collectors.toSet())).build();
		oldCard.setUser(admin);

		given(creditCardDAO.findAllByNumberContaining(anyString()))
		.willReturn(Arrays.asList(creditCard, oldCard));

		// when
		List<CreditCard> cards = cardServiceImpl.findAllByNumberContaining("1");

		// then
		assertTrue(cards.get(0).getNumber().contains("1"));
		assertTrue(cards.get(0).getUser().getRoles().contains(roleUser));
		assertTrue(cards.get(1).getNumber().contains("2"));
		assertTrue(cards.get(1).getUser().getRoles().contains(roleAdmin));
	}

	@Test
	public void testFindAllByNumberContaining_ShouldReturnOneCard_WhenHasUserRole() {
		// given
		given(securityService.hasAdminRole()).willReturn(false);
		oldCard.setUser(admin);

		given(creditCardDAO.findAllByNumberContainingAndUserId(anyString(), anyLong()))
		.willReturn(Arrays.asList(creditCard));

		// when
		List<CreditCard> cards = cardServiceImpl.findAllByNumberContaining("1");

		// then
		assertTrue(cards.get(0).getNumber().contains("1"));
		assertTrue(cards.get(0).getUser().getRoles().contains(roleUser));
	}

	@Test
	public void testFindAllByNumberContaining_ShouldReturnEmptyList_WhenCannotFindUser() {
		// given
		given(securityService.hasAdminRole()).willReturn(false);
		given(userService.findByUsername()).willReturn(Optional.empty());

		// when
		List<CreditCard> cards = cardServiceImpl.findAllByNumberContaining("1");

		// then
		assertEquals(cards.size(), 0);
	}
	
}
