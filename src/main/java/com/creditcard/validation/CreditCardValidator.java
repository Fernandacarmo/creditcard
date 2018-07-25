package com.creditcard.validation;

import com.creditcard.model.CreditCard;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CreditCardValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CreditCard.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreditCard creditCard = (CreditCard) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "NotEmpty");
        if (!checkCreditCard(creditCard.getNumber())) {
            errors.rejectValue("number", "Invalid.creditCardModel.number");
        }
    }

    private boolean checkCreditCard(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
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
    }
}
