package com.creditcard.repository;

import com.creditcard.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.SimpleDateFormat;

public interface CreditCardDAO extends JpaRepository<CreditCard, Long> {

    static SimpleDateFormat formatExpiryDate = new SimpleDateFormat("yy/MM");
}
