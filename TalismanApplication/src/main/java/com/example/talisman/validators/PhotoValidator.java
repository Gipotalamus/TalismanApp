package com.example.talisman.validators;

import com.example.talisman.entities.TalismanPhotoEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            errors.rejectValue("title", "validation.photo.title");
        }
        String ext = photoEntity.getPhoto().split("\\.").length < 2?"" : photoEntity.getPhoto().split("\\.")[1];
        Pattern pattern = Pattern.compile("(jpg)|(png)|(jpeg)|(bmp)|(JPG)");
        Matcher matcher = pattern.matcher(ext);
        if (photoEntity.getPhoto().trim().equals("")) errors.rejectValue("photo", "validation.member.photo.empty");
        if (!matcher.matches()) errors.rejectValue("photo", "validation.member.photo.format");
        if (photoEntity.getEvent().getTitle().trim().equals("")) errors.rejectValue("event", "validation.photo.event");
    }
}
