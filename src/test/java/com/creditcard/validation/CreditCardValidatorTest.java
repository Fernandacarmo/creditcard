package com.creditcard.validation;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.net.BindException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.SupportedSourceVersion;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;

import com.creditcard.model.CreditCard;
import com.creditcard.model.User;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardValidatorTest {

	private CreditCardValidator creditCardValidator;
	private BeanPropertyBindingResult errors;
	private CreditCard card;
	private User user;
	private Calendar calendar2018;
	
	@Before
	public void setUp() {
		user = User.builder().username("fernanda").build();
		calendar2018 = new GregorianCalendar(2018, 0, 31);
		card = CreditCard.builder().number("1234567890").name("Fake Name")
				.expiryDate(calendar2018.getTime()).user(user).build();
		errors = new BeanPropertyBindingResult(card, "CreditCard");
		creditCardValidator = new CreditCardValidator();
	}

	@Test
	public void testSupports() {
		assertTrue(creditCardValidator.supports(CreditCard.class));
	}
	
	@Test
	public void testValidate_shouldReturnError_whenHasInvalidCardNumber() {
		// when
		ValidationUtils.invokeValidator(creditCardValidator, card, errors);
		
		// then
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("number").getCode(), "Invalid.creditCardModel.number");
	}

	@Test
	public void testValidate_shouldReturnError_whenHasNoCardNumber() {
		// given
		card.setNumber(" ");
		
		// when
		ValidationUtils.invokeValidator(creditCardValidator, card, errors);
		
		// then
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("number").getCode(), "NotEmpty");
	}

	@Test
	public void testValidate_shouldReturnError_whenHasWrongCardNumberSize() {
		// given
		card.setNumber("123456789");
		
		// when
		ValidationUtils.invokeValidator(creditCardValidator, card, errors);
		
		// then
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("number").getCode(), "Size.creditCardModel.number");
	}

	@Test
	public void testValidate_shouldReturnError_whenHasWrongCardNameSize() {
		// given
		card.setName("abc");
		
		// when
		ValidationUtils.invokeValidator(creditCardValidator, card, errors);
		
		// then
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("name").getCode(), "Size.creditCardModel.name");
	}

	@Test
	public void testValidate_shouldReturnError_whenHasNoCardName() {
		// given
		card.setName("  ");
		
		// when
		ValidationUtils.invokeValidator(creditCardValidator, card, errors);
		
		// then
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("name").getCode(), "NotEmpty");
	}

	@Test
	public void testValidate_shouldReturnError_whenHasNoCardExpiryDate() {
		// given
		card.setExpiryDate(null);
		
		// when
		ValidationUtils.invokeValidator(creditCardValidator, card, errors);
		
		// then
		assertTrue(errors.hasErrors());
		assertEquals(errors.getFieldError("expiryDate").getCode(), "NotEmpty");
	}
}
