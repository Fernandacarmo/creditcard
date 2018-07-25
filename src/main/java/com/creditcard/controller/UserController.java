package com.creditcard.controller;

import com.creditcard.model.User;
import com.creditcard.service.ISecurityService;
import com.creditcard.service.IUserService;
import com.creditcard.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userModel", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userModel") User userModel, BindingResult bindingResult, Model model) {
        userValidator.validate(userModel, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userModel);

        securityService.autologin(userModel.getUsername(), userModel.getPassword());

        return "redirect:/search";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password are invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

}
