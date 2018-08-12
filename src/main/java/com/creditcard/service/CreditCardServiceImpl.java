package com.creditcard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditcard.model.CreditCard;
import com.creditcard.model.User;
import com.creditcard.repository.CreditCardRepository;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private SecurityService securityService;

    @Override
    @Transactional
    public CreditCard save(CreditCard creditCard) {
        Optional<User> user = userService.findByUsername();
        
        if (user.isPresent()) {
            Optional<CreditCard> oldCard = findByNumberAndUserId(creditCard.getNumber(), user.get().getId());
            
            if (oldCard.isPresent()) {
            	logger.debug("Card already exists: " + oldCard.get().getNumber());
            	oldCard.get().setExpiryDate(creditCard.getExpiryDate());
            	return creditCardRepository.save(oldCard.get());
            } 
            creditCard.setUser(user.get());        	
            return creditCardRepository.save(creditCard);
            
        } else {
        	logger.error("Couldn't find logged in user when saving card: " + creditCard.getNumber());
        	return null;
        }
    }

    @Override
    public Optional<CreditCard> findByNumberAndUserId(String number, Long userId) {
        return creditCardRepository.findByNumberAndUserId(number, userId);
    }

    @Override
    public List<CreditCard> findAllByNumberContaining(String number) {
    	
    	if (securityService.hasAdminRole()) {
    		return creditCardRepository.findAllByNumberContaining(number);
    	
    	} else {	
            Optional<User> user = userService.findByUsername();
            if (user.isPresent()) {
                return creditCardRepository.findAllByNumberContainingAndUserId(number, user.get().getId());            	
            } else {
            	return new ArrayList<CreditCard>();
            }
    	}    	
    }

}
