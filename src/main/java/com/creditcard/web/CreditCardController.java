package com.creditcard.web;

import com.creditcard.model.CreditCard;
import com.creditcard.model.User;
import com.creditcard.service.ICreditCardService;
import com.creditcard.service.ISecurityService;
import com.creditcard.service.IUserService;
import com.creditcard.validation.CreditCardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

@Controller
public class CreditCardController {

    @Autowired
    private ICreditCardService creditCardService;

    @Autowired
    private CreditCardValidator creditCardValidator;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/search")
    public String searchCard(Model model, String error) {

        if (error != null) {
            model.addAttribute("cardError", "Invalid.creditCardModel.number");
        }

        return "search";
    }

    @PostMapping(value = "/search")
    public String searchCard(@ModelAttribute(value = "cardNumber") String cardNumber, BindingResult bindingResult, Model model) {

        if (cardNumber.isEmpty() ) {
            model.addAttribute("error", "NotEmpty");
            return "search";
        }
        List<CreditCard> cards = creditCardService.getAllByNumberStartingWith(cardNumber);

        if (cards.isEmpty()) {
            model.addAttribute("error", "Inexistent.creditcard.number");
        } else {
            model.addAttribute("cards", cards);
        }
        return "search";
    }


    @GetMapping(value = "/creditcard")
    public String insertCard(Model model) {

        model.addAttribute("creditCardModel", new CreditCard());
        return "creditcard";
    }

    @PostMapping(value = "/creditcard")
    public String insertCard(@ModelAttribute("creditCardModel") CreditCard creditCardModel, BindingResult bindingResult, Model model) {

        creditCardValidator.validate(creditCardModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return "creditcard";
        }
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        creditCardModel.setUser(user);
        creditCardService.save(creditCardModel);

        return "redirect:/search";
    }

}
