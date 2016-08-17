package com.example.talisman.validators;

import com.example.talisman.entities.TalismanUser;
import com.example.talisman.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by gipotalamus on 10.07.16.
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isInstance(TalismanUser.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TalismanUser incomingUser = (TalismanUser)o;
        TalismanUser user = userDetailsService.findOneByName(incomingUser.getName());
        if (incomingUser.getEmail().trim().equals(""))
            errors.rejectValue("email", "validation.user.email");

        if (incomingUser.getName().trim().equals("")) {
            errors.rejectValue("name", "validation.user.name");
        }
        if (user!=null) {
            errors.rejectValue("name", "validation.user.exists");
        }
        if (incomingUser.getPassword().trim().length()<6) {
            errors.rejectValue("password", "validation.user.password");
        }

    }
}
