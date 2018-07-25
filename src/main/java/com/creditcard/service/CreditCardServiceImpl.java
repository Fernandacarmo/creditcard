package com.creditcard.service;

import com.creditcard.model.CreditCard;
import com.creditcard.repository.CreditCardDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import static com.creditcard.repository.CreditCardDAO.formatExpiryDate;

@Service
public class CreditCardServiceImpl implements ICreditCardService {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    @Autowired
    private CreditCardDAO creditCardDAO;

    @Override
    public void save(CreditCard creditCard) {
        try {
            String correctDate = formatExpiryDate.format(creditCard.getExpiryDate());
            creditCard.setExpiryDate(formatExpiryDate.parse(correctDate));
            creditCardDAO.save(creditCard);
        } catch (ParseException e) {
            logger.info(e.getMessage());
        }

    }
}
