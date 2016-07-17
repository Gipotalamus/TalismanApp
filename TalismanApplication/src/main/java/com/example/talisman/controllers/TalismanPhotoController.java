package com.example.talisman.controllers;


import com.example.talisman.entities.TalismanEventEntity;
import com.example.talisman.entities.TalismanPhotoEntity;
import com.example.talisman.services.TalismanEventService;
import com.example.talisman.services.TalismanPhotoService;
import com.example.talisman.validators.PhotoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    TalismanEventService talismanEventService;

    @Autowired
    PhotoValidator validator;

    @RequestMapping("/")
    public String showAll(Model model) {
        model.addAttribute("photos", talismanPhotoService.findAll());
        model.addAttribute("menu", "photos");
        return "photos";
    }

    @RequestMapping("/edit/{photoId}")
    public String addOrEdit(@PathVariable("photoId") int id, Model model) {
        TalismanPhotoEntity photo = talismanPhotoService.find(id);
        model.addAttribute("talismanPhotoEntity", photo);
        model.addAttribute("events", talismanEventService.getAll());
        return "photo";
    }

    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public String add(@RequestParam(name = "file", required = false) MultipartFile file,
                      TalismanPhotoEntity talismanPhotoEntity, BindingResult result) {
        String[] s = file.getOriginalFilename().split("\\.");
        String photo = "/uploaded/talismanPhotos/" + (talismanPhotoService.getMaxId() + 1) + "." + s[s.length - 1];
        talismanPhotoEntity.setPhoto(photo);
        validator.validate(talismanPhotoEntity, result);
        if (!result.hasErrors()) {
            System.out.println("no errors");
            String filePath = "/home/gipotalamus/Idea/" + photo;
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            talismanPhotoService.addPhoto(talismanPhotoEntity);
            return "redirect:/photos/";
        } else {

            System.out.println(result.toString());
            return "photo";
        }

    }

    @RequestMapping("/remove/{photoId}")
    public String remove(@PathVariable("photoId") int id) {
        talismanPhotoService.remove(id);
        return "redirect:/photos/";
    }
}
