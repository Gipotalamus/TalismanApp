package com.example.talisman.controllers;


import com.example.talisman.entities.Comment;
import com.example.talisman.entities.TalismanEventEntity;
import com.example.talisman.services.CommentService;
import com.example.talisman.services.TalismanEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by мир on 15.03.2016.
 */
@Controller
public class TalismanEntityController {

    @Autowired
    TalismanEventService talismanEventService;

    @Autowired
    CommentService commentService;

    @Value("${pagination.count}")
    private int entitiesPerPageCount;

    @RequestMapping("/")
    public String start(@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Sort sort = (Sort)session.getAttribute("sort");
        if (sort == null) {
            sort = new Sort("date");
        }
        if (pageable.getSort() != null) {
            sort = pageable.getSort();
        }
        session.setAttribute("sort", sort);
        int currentPage = pageable.getPageNumber();
        Sort sortCriteria = (Sort)session.getAttribute("sort");
        pageable = new PageRequest(currentPage, entitiesPerPageCount, sortCriteria);
        Page page = talismanEventService.getPaginatedEvents(pageable);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("pagesCount", page.getTotalPages()==0?1:page.getTotalPages());
        model.addAttribute("events", page);
        return "start";
    }

    @RequestMapping("/talismanEvents/edit/{eventId}")
    public String talismanEventForm(@PathVariable(value = "eventId") int id, Model model) {
        model.addAttribute("talismanEventEntity", talismanEventService.get(id));
        return "talismanEvent";
    }

    @RequestMapping(path = "/talismanEvents/saveOrUpdate", method = RequestMethod.POST)
    public String saveOrUpdateEvent(@Valid TalismanEventEntity talismanEventEntity, BindingResult bindingResult) {
        System.out.println(talismanEventEntity.getTitle());
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "talismanEvent";
        }
        talismanEventService.saveOrUpdate(talismanEventEntity);
        return "redirect:/";
    }

    @RequestMapping("/talismanEvents/remove/{eventId}")
    public String removeEvent(@PathVariable("eventId") int id) {
        talismanEventService.remove(id);
        return "redirect:/";
    }

    @RequestMapping("/talismanEvents/view/{eventId}")
    public String viewDetailedEvent(@PathVariable("eventId") int id, Model model) {
        TalismanEventEntity event= talismanEventService.get(id);
        talismanEventService.increaseViews(id);
        model.addAttribute("event", event);
        return "detailedEvent";
    }

    @RequestMapping("/talismanEvents/comment/{eventId}")
    public String commentEvent(@PathVariable("eventId") int id, Model model) {
        TalismanEventEntity event= talismanEventService.get(id);
        Comment comment = new Comment();
        comment.setEvent(event);
        model.addAttribute("comment", comment);
        return "comment";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


}
