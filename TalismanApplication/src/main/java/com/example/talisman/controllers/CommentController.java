package com.example.talisman.controllers;

import com.example.talisman.entities.Comment;
import com.example.talisman.entities.TalismanEventEntity;
import com.example.talisman.entities.TalismanUser;
import com.example.talisman.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by gipotalamus on 29.06.16.
 */
@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(Comment comment, Model model) {
        commentService.save(comment);
        TalismanEventEntity event = comment.getEvent();
        model.addAttribute("event", event);
        return "redirect:/talismanEvents/view/" + event.getId();
    }

    @RequestMapping("/remove/{id}")
    public String deleteComment(@PathVariable("id") int id, Model model) {
        TalismanEventEntity event = commentService.get(id).getEvent();
        model.addAttribute("event", event);
        commentService.delete(id);
        return "detailedEvent";
    }

}
