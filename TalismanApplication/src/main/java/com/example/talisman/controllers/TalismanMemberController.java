package com.example.talisman.controllers;


import com.example.talisman.entities.TalismanMemberEntity;
import com.example.talisman.services.TalismanMemberService;
import com.example.talisman.validators.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;


/**
 * Created by мир on 27.03.2016.
 */
@Controller
@RequestMapping("/members/*")
public class TalismanMemberController {

    @Autowired
    MemberValidator validator;

    @Autowired
    TalismanMemberService talismanMemberService;

    @RequestMapping("/")
    public String getAll(Model model) {
        model.addAttribute("members", talismanMemberService.getAll());
        return "stuff";
    }

    @RequestMapping("/{memberId}")
    public String get(@PathVariable("memberId") int memberId, Model model) {
        TalismanMemberEntity member = talismanMemberService.find(memberId);
        model.addAttribute("member", member);
        return "member";
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("talismanMemberEntity", new TalismanMemberEntity());
        return "talismanMember";
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public String add(@RequestParam(name = "file", required = false) MultipartFile file,
                            TalismanMemberEntity member, BindingResult result) {
        String[] s = file.getOriginalFilename().split("\\.");
        String photo = "/uploaded/" + (talismanMemberService.getMaxId() + 1) + "." + s[s.length - 1];
        member.setPhoto(photo);
        validator.validate(member, result);
        if (!result.hasErrors()) {
            String filePath = "/home/gipotalamus/Idea/" + photo;
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            talismanMemberService.add(member);
            return "redirect:/";
        } else return "talismanMember";

    }

    @RequestMapping("/remove/{memberId}")
    public String delete(@PathVariable("memberId") int id) {
        talismanMemberService.delete(id);
        System.out.println("deleted");
        return "redirect:/members/";
    }

    @RequestMapping("/edit/{memberId}")
    public String edit(@PathVariable("memberId") int id, Model model) {
        TalismanMemberEntity member = talismanMemberService.find(id);
        model.addAttribute("talismanMemberEntity", member);
        return "talismanMember";
    }
}
