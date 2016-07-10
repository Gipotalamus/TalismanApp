package com.example.talisman.controllers;


import com.example.talisman.entities.TalismanMemberEntity;
import com.example.talisman.services.TalismanMemberService;
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
    TalismanMemberService talismanMemberService;

    @RequestMapping("/")
    public String getAllMembers(Model model) {
        model.addAttribute("members", talismanMemberService.getAll());
        return "stuff";
    }

    @RequestMapping("/{memberId}")
    public String getMember (@PathVariable("memberId") int memberId, Model model){
        TalismanMemberEntity member = talismanMemberService.getMember(memberId);
        model.addAttribute("member", member);
        return "member";
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.GET)
    public String addMember(Model model) {
        model.addAttribute("talismanMemberEntity", new TalismanMemberEntity());
    return "talismanMember";
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public String addMember(@RequestParam(name = "file", required = false) MultipartFile file,
                            @Valid TalismanMemberEntity talismanMemberEntity, BindingResult result, HttpServletRequest request) {
        if (!result.hasErrors()) {
            String[] s = file.getOriginalFilename().split("\\.");
            String photo = "/uploaded/" + (talismanMemberService.getMaxId() + 1) + "." + s[s.length -1];
            String filePath = "/home/gipotalamus/Idea/" + photo;
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            talismanMemberEntity.setPhoto(photo);
            talismanMemberService.addMember(talismanMemberEntity);

            return "redirect:/";
        }
        else return "talismanMember";

    }

    @RequestMapping("/remove/{memberId}")
    public String deleteMember(@PathVariable("memberId") int id) {
        talismanMemberService.deleteMember(id);
        System.out.println("deleted");
        return "redirect:/members/";
    }

    @RequestMapping("/edit/{memberId}")
    public String editMember(@PathVariable("memberId") int id, Model model) {
        TalismanMemberEntity member = talismanMemberService.getMember(id);
        model.addAttribute("talismanMemberEntity", member);
        return "talismanMember";
    }
}
