package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.Post;
import com.l524l.web_blog.models.User;
import com.l524l.web_blog.models.enumes.Role;
import com.l524l.web_blog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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

    @PostMapping("/userData")
    public String changeRole(@RequestParam(name = "id") Long id,
                             @RequestParam(name = "name") String name,
                             @RequestParam(name = "role") Role role,
                             Model model){
        User user = userService.getById(id);
        user.setName(name);
        user.getRoles().clear();
        user.getRoles().add(role);
        userService.updateUser(user);
        return "redirect:/god-panel";
    }

    @PostMapping
    public String delUser(@RequestParam(name = "id") Long id, Model model){
        userService.deleteUser(id);
        return "redirect:/god-panel";
    }
}
