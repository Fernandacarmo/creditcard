package com.creditcard.web;

import com.creditcard.model.User;
import com.creditcard.service.SecurityService;
import com.creditcard.service.UserService;
import com.creditcard.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = {"/", "/login"})
    public String login(Model model, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Invalid.userModel.credentials");
        }
        if (logout != null) {
            model.addAttribute("logout", "Logout");
        }
        return "login";
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("userModel", new User());

        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("userModel") User userModel, BindingResult bindingResult, Model model) {
        userValidator.validate(userModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        String passwordBeforeEncode = userModel.getPassword();
        userService.save(userModel);
        securityService.autologin(userModel.getUsername(), passwordBeforeEncode);

        return "redirect:/search";
    }

}