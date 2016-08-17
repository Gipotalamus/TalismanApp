package com.example.talisman.controllers;

import com.example.talisman.entities.TalismanUser;
import com.example.talisman.services.CustomUserDetailsService;
import com.example.talisman.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

/**
 * Created by gipotalamus on 25.05.16.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserValidator userValidator;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @RequestMapping("/")
    public String getUsers(Model model) {
        model.addAttribute("menu", "users");
        model.addAttribute("users", userDetailsService.getAll());
        return "users";
    }

    @RequestMapping("/add")
    public String signUp(Model model){
        TalismanUser user = new TalismanUser();
        model.addAttribute("talismanUser", user);
        return "signUp";
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addUser(TalismanUser talismanUser, BindingResult result, Model model, HttpServletRequest request){
        userValidator.validate(talismanUser, result);
        if (result.hasErrors()) {
            return "signUp";
        }
        String path = request.getRequestURL().toString().replace("/add", "");
        System.out.println(path);
        userDetailsService.addUser(talismanUser, path);
        return "redirect:/";
    }

    @RequestMapping("/confirm")
    public String confirmRegistration(@QueryParam("code") String code) {
        System.out.println("controller confirm");
        userDetailsService.checkCode(code);
        return "redirect:/";
    }

    @RequestMapping("/remove/{name}")
    public String remove(@PathVariable("name") String name) {
        userDetailsService.remove(name);
        return "redirect:/";
    }

    @PreDestroy
    public void setUsersOffline(){
        userDetailsService.setUsersOffline();
    }

}
