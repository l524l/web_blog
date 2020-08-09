package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.User;
import com.l524l.web_blog.models.enumes.Role;
import com.l524l.web_blog.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/god-panel")
@PreAuthorize("hasAuthority('GOD')")
public class GodController {
    private static final Logger log = Logger.getLogger(GodController.class);

    final private UserServiceImpl userService;

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
        if (id != null && name.trim() != ""){
            User user = userService.getById(id);
            user.setName(name.trim());
            user.getRoles().clear();
            user.getRoles().add(role);
            userService.updateUser(user);
        }
        return "redirect:/god-panel";
    }

    @PostMapping
    public String delUser(@RequestParam(name = "id") Long id, Model model){
        userService.deleteUser(id);
        return "redirect:/god-panel";
    }
}
