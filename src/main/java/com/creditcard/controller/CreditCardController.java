package com.creditcard.controller;

import com.creditcard.model.CreditCard;
import com.creditcard.service.ICreditCardService;
import com.creditcard.validation.CreditCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditCardController {

    @Autowired
    private ICreditCardService creditCardService;

    @Autowired
    private CreditCardValidator creditCardValidator;

    @RequestMapping(value = "/creditcard", method = RequestMethod.GET)
    public String insertCard(Model model) {
        model.addAttribute("creditCardModel", new CreditCard());

        return "creditcard";
    }

    @RequestMapping(value = "/creditcard", method = RequestMethod.POST)
    public String insertCard(@ModelAttribute("creditCardModel") CreditCard creditCardModel, BindingResult bindingResult, Model model) {
        creditCardValidator.validate(creditCardModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return "creditcard";
        }
        creditCardService.save(creditCardModel);

        return "redirect:/search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchCard(Model model, String error, String logout) {
        if (error != null) {

            model.addAttribute("error", "Your credit card number is invalid.");
        }
        return "search";
    }

//    @RequestMapping(value = "/search", method = RequestMethod.POST)
//    public String searchCard(Model model, String error, String logout) {
//        if (error != null) {
//            model.addAttribute("error", "Your credit card number is invalid.");
//        }
//        return "search";
//    }


}
