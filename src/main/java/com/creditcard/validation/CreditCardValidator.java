package com.creditcard.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.creditcard.model.CreditCard;

@Component
public class CreditCardValidator implements Validator {
	
    private static final Logger logger = LoggerFactory.getLogger(CreditCardValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return CreditCard.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreditCard creditCard = (CreditCard) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "NotEmpty");
        if (creditCard.getNumber().length() < 10 || creditCard.getNumber().length() > 16) {
            errors.rejectValue("number", "Size.creditCardModel.number");
        
        } else if (!isCreditCardNumberValid(creditCard.getNumber())) {
            errors.rejectValue("number", "Invalid.creditCardModel.number");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (creditCard.getName().length() < 6 || creditCard.getName().length() > 20) {
            errors.rejectValue("name", "Size.creditCardModel.name");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expiryDate", "NotEmpty");
    }

    /**
     * Validates the credit card number according to Luhn algorithm.
     * @param ccNumber credit card number.
     * @return true if the number is valid, false if it is not valid or not a number.
     */
    private boolean isCreditCardNumberValid(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        try {
            for (int i = ccNumber.length() - 1; i >= 0; i--) {
                int n = Integer.parseInt(ccNumber.substring(i, i + 1));
                if (alternate) {
                    n *= 2;
                    if (n > 9) {
                        n = (n % 10) + 1;
                    }
                }
                sum += n;
                alternate = !alternate;
            }
            return (sum % 10 == 0);
        	
        } catch (NumberFormatException e) {
        	logger.error("Credit card number: " + ccNumber, e.getMessage());
        	return false;
        }
    }
}
