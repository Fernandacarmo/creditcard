package com.creditcard.service;

import com.creditcard.model.CreditCard;

import java.text.ParseException;
import java.util.List;

public interface CreditCardService {

    void save(CreditCard creditCard);
    CreditCard findByNumber(String number);
    List<CreditCard> getAllByNumberStartingWith(String number);
    List<CreditCard> findAll();
    List<CreditCard> findAllByUser();
}
