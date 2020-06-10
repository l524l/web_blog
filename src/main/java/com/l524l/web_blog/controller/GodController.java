package com.l524l.web_blog.controller;

import com.l524l.web_blog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/god-panel")
@PreAuthorize("hasAuthority('GOD')")
public class GodController {

    final private UserServiceImpl userService;

    @Autowired
    public GodController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping
    public String godPanelPage(Model model){
        model.addAttribute("users", userService.getAll());
        return "god_page";
    }
}
