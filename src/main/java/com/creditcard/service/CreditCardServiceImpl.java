package com.creditcard.service;

import com.creditcard.model.CreditCard;
import com.creditcard.model.User;
import com.creditcard.repository.CreditCardDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

import static com.creditcard.repository.CreditCardDAO.formatExpiryDate;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private static final Logger logger = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    @Autowired
    private CreditCardDAO creditCardDAO;

    @Autowired
    private UserService userService;

    @Override
    public void save(CreditCard creditCard) {
        User user = userService.findByUsername();
        creditCard.setUser(user);

        creditCardDAO.save(creditCard);
        try {
            String correctDate = formatExpiryDate.format(creditCard.getExpiryDate());
            creditCard.setExpiryDate(formatExpiryDate.parse(correctDate));
        } catch (ParseException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public CreditCard findByNumber(String number) {
        return creditCardDAO.findByNumber(number);
    }

    @Override
    public List<CreditCard> getAllByNumberStartingWith(String number) {
        return creditCardDAO.getAllByNumberStartingWith(number);
    }

    @Override
    public List<CreditCard> findAll() {
        return creditCardDAO.findAll();
    }

    @Override
    public List<CreditCard> findAllByUser() {
        User user = userService.findByUsername();
        return creditCardDAO.findAllByUser(user);
    }
}
