package com.example.talisman.validators;

import com.example.talisman.entities.TalismanMemberEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gipotalamus on 11.07.16.
 */
@Component
public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isInstance(TalismanMemberEntity.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TalismanMemberEntity member = (TalismanMemberEntity) o;
        if (member.getName().trim().equals("")) errors.rejectValue("name", "validation.member.name");
        Pattern pattern1 = Pattern.compile("\\d{10}");
        Matcher matcher1 = pattern1.matcher(member.getPhone());
        if (!matcher1.matches()) errors.rejectValue("phone", "validation.member.phone");
        String photo = member.getPhoto();
        String ext = photo.split("\\.").length < 2?"" : photo.split("\\.")[1];
        System.out.println(ext);
        Pattern pattern2 = Pattern.compile("(jpg)|(png)|(jpeg)|(bmp)");
        Matcher matcher2 = pattern2.matcher(ext);
        if (photo.trim().equals("")) errors.rejectValue("photo", "validation.member.photo.empty");
        if (!matcher2.matches()) errors.rejectValue("photo", "validation.member.photo.format");
        if (member.getStory().trim().equals("")) errors.rejectValue("story", "validation.member.story");

    }
}
