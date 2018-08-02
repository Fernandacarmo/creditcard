package com.creditcard.repository;

import com.creditcard.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.SimpleDateFormat;
import java.util.List;

public interface CreditCardDAO extends JpaRepository<CreditCard, Long> {

    static SimpleDateFormat formatExpiryDate = new SimpleDateFormat("yy/MM");

    CreditCard findByNumber(String number);

    List<CreditCard> getAllByNumberStartingWith(String number);

}
