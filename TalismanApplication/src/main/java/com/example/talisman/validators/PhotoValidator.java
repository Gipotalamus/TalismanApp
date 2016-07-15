package com.example.talisman.validators;

import com.example.talisman.entities.TalismanPhotoEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by gipotalamus on 15.07.16.
 */
@Component
public class PhotoValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isInstance(TalismanPhotoEntity.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TalismanPhotoEntity photoEntity = (TalismanPhotoEntity) o;
        if (photoEntity.getTitle().trim().equals("")) {
            System.out.println("error");
            errors.rejectValue("title", "validation.photo.title");
        }
    }
}
