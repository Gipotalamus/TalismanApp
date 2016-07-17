package com.example.talisman.controllers;

import com.example.talisman.entities.TalismanEventEntity;
import com.example.talisman.services.TalismanEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by gipotalamus on 17.07.16.
 */
@Controller
@RequestMapping("/")
public class FrontController {

    @Autowired
    TalismanEventService talismanEventService;

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
        Page<TalismanEventEntity> page = talismanEventService.getPaginatedEvents(pageable);
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("pagesCount", page.getTotalPages()==0?1:page.getTotalPages());
        page.forEach(talismanEventEntity -> System.out.println(talismanEventEntity.getDate()));
        model.addAttribute("events", page);
        return "start";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("menu", "about");
        return "about";
    }

    @RequestMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("menu", "contacts");
        return "about";
    }

}
