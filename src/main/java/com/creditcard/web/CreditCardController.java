package com.creditcard.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.creditcard.model.CreditCard;
import com.creditcard.service.CreditCardService;
import com.creditcard.validation.CreditCardValidator;

@Controller
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardValidator creditCardValidator;

    @GetMapping(value = "/search")
    public String searchCard(Model model, String error) {

        if (error != null) {
            model.addAttribute("cardError", "Invalid.creditCardModel.number");
        }
        return "search";
    }

    @PostMapping(value = "/search")
    public String searchCard(@ModelAttribute("cardNumber") String cardNumber, BindingResult bindingResult, Model model) {

        List<CreditCard> cards = creditCardService.findAllByNumberContaining(cardNumber);

        if (cards.isEmpty()) {
            model.addAttribute("cardError", "Inexistent.creditcard.number");
        } else {
            model.addAttribute("cards", cards);
        }
        return "search";
    }

    @GetMapping(value = "/creditcard")
    public String insertCard(Model model) {

        model.addAttribute("creditCardModel", CreditCard.builder().build());
        return "creditcard";
    }

    @PostMapping(value = "/creditcard")
    public String insertCard(@ModelAttribute("creditCardModel") CreditCard creditCardModel, BindingResult bindingResult, Model model) {

        creditCardValidator.validate(creditCardModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return "creditcard";
        }
        creditCardService.save(creditCardModel);
        return "redirect:/search";
    }
}
