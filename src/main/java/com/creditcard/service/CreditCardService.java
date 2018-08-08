package com.creditcard.service;

import java.util.List;
import java.util.Optional;

import com.creditcard.model.CreditCard;

public interface CreditCardService {

    CreditCard save(CreditCard creditCard);
	Optional<CreditCard> findByNumberAndUserId(String number, Long userId);
	List<CreditCard> findAllByNumberContaining(String number);
}
