package com.example.talisman.controllers;

import com.example.talisman.entities.TalismanEntity;
import com.example.talisman.entities.TalismanEventEntity;
import com.example.talisman.services.TalismanEventService;
import com.example.talisman.services.TalismanService;
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
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by gipotalamus on 17.07.16.
 */
@Controller
@RequestMapping("/")
public class TalismanController {

    @Autowired
    TalismanEventService talismanEventService;

    @Autowired
    TalismanService talismanService;



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
        pageable = new PageRequest(currentPage, entitiesPerPageCount, sort);
        Page<TalismanEventEntity> page = talismanEventService.getAll(pageable);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pagesCount", page.getTotalPages()==0?1:page.getTotalPages());
        model.addAttribute("events", page);
        return "start";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        TalismanEntity talismanEntity = talismanService.get();
        if (talismanEntity == null) {
            talismanEntity = new TalismanEntity();
        }
        model.addAttribute("talisman", talismanEntity);
        return "about";
    }

    @RequestMapping("/contacts")
    public String contacts(Model model) {
        TalismanEntity talismanEntity = talismanService.get();
        if (talismanEntity == null) {
            talismanEntity = new TalismanEntity();
        }
        model.addAttribute("talisman", talismanEntity);
        return "contacts";
    }

    @RequestMapping("/edit")
    public String edit(Model model) {
        TalismanEntity talismanEntity = talismanService.get();
        if (talismanEntity == null) {
            talismanEntity = new TalismanEntity();
        }
        model.addAttribute("talismanEntity",talismanEntity);
        return "talisman";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String save(TalismanEntity talismanEntity) {
        talismanService.saveOrUpdate(talismanEntity);
        return "redirect:/";
    }

}
