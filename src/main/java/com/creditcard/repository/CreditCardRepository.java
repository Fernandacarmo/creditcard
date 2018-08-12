package com.creditcard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.creditcard.model.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    @SuppressWarnings("unchecked")
	CreditCard save(CreditCard creditCard);
        
    @Query("FROM CreditCard cc WHERE cc.number = ?1 AND cc.user.id = ?2")
    Optional<CreditCard> findByNumberAndUserId(String number, Long userId);

    List<CreditCard> findAllByNumberContaining(String number);

    @Query("FROM CreditCard cc WHERE cc.number LIKE %?1% AND cc.user.id = ?2")
    List<CreditCard> findAllByNumberContainingAndUserId(String number, Long userId);

 }
