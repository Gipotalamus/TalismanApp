package com.example.talisman.controllers;

import com.example.talisman.entities.Comment;
import com.example.talisman.entities.TalismanEventEntity;
import com.example.talisman.services.CommentService;
import com.example.talisman.services.TalismanEventService;
import com.example.talisman.services.TalismanPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

/**
 * Created by мир on 15.03.2016.
 */
@Controller
@RequestMapping("/talismanEvents")
public class TalismanEventController {

    @Autowired
    TalismanEventService talismanEventService;

    @Autowired
    CommentService commentService;

    @Autowired
    TalismanPhotoService photoService;

    @RequestMapping("/edit/{eventId}")
    public String addOrEdit(@PathVariable(value = "eventId") int id, Model model) {
        model.addAttribute("talismanEventEntity", talismanEventService.get(id));
        return "talismanEvent";
    }

    @RequestMapping(path = "/saveOrUpdate", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid TalismanEventEntity talismanEventEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "talismanEvent";
        }
        talismanEventService.saveOrUpdate(talismanEventEntity);
        return "redirect:/";
    }

    @RequestMapping("/remove/{eventId}")
    public String remove(@PathVariable("eventId") int id) {
        talismanEventService.delete(id);
        return "redirect:/";
    }

    @RequestMapping("/view/{eventId}")
    public String viewDetailed(@PathVariable("eventId") int id, Model model) {
        TalismanEventEntity event= talismanEventService.get(id);
        talismanEventService.increaseViews(id);
        model.addAttribute("event", event);
        return "detailedEvent";
    }

    @RequestMapping("/comment/{eventId}")
    public String comment(@PathVariable("eventId") int id, Model model) {
        TalismanEventEntity event= talismanEventService.get(id);
        Comment comment = new Comment();
        comment.setEvent(event);
        model.addAttribute("comment", comment);

        return "comment";
    }

}
