package com.example.talisman.controllers;

import com.example.talisman.entities.Role;
import com.example.talisman.entities.TalismanUser;
import com.example.talisman.services.CustomUserDetailsService;
import com.example.talisman.validators.UserValidator;
import org.springframework.security.config.authentication.CachingUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by gipotalamus on 25.05.16.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Inject
    UserValidator userValidator;

    @Inject
    CustomUserDetailsService userDetailsService;

    @RequestMapping("/add")
    public String signUp(Model model){
        TalismanUser user = new TalismanUser();
        model.addAttribute("talismanUser", user);
        return "signUp";
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addUser(TalismanUser talismanUser, BindingResult result, Model model){
        userValidator.validate(talismanUser, result);
        if (result.hasErrors()) {
            return "signUp";
        }
        talismanUser.setRole("ROLE_USER");
        userDetailsService.saveUser(talismanUser);
        return "redirect:/";
    }
}
