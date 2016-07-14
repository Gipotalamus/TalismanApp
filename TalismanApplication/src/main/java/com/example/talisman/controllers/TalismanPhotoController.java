package com.example.talisman.controllers;


import com.example.talisman.entities.TalismanPhotoEntity;
import com.example.talisman.services.TalismanPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * Created by gipotalamus on 14.07.16.
 */
@Controller
@RequestMapping("/photos")
public class TalismanPhotoController {

    @Autowired
    TalismanPhotoService talismanPhotoService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPhoto(@RequestParam(name = "file", required = false) MultipartFile file,
                           TalismanPhotoEntity talismanPhotoEntity, BindingResult result) {
        String[] s = file.getOriginalFilename().split("\\.");
        String photo = "/uploaded/talismanPhotos/" + (talismanPhotoService.getMaxId() + 1) + "." + s[s.length - 1];
        talismanPhotoEntity.setPhoto(photo);
//        validator.validate(talismanPhotoEntity, result);
        if (!result.hasErrors()) {
            String filePath = "/home/gipotalamus/Idea/" + photo;
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            talismanPhotoService.addPhoto(talismanPhotoEntity);
            return "redirect:/";
        } else return "talismanMember";

    }

    @RequestMapping("/")
    public String showAllPhotos(Model model) {
        model.addAttribute("photos", talismanPhotoService.findAll());
        return "photos";
    }

}
