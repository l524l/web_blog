package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.User;
import com.l524l.web_blog.models.enumes.Role;
import com.l524l.web_blog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class RegController {


    private UserServiceImpl userService;

    @Autowired
    public RegController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String reg(Model model){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUsr(@RequestParam(name = "username") String log, @RequestParam(name = "password") String pass, Model model){
        User user = new User();
        user.setRoles(Collections.singleton(Role.USER));
        user.setName(log);
        user.setPassword(pass);

        userService.saveUser(user);

        return "redirect:/login";
    }
}
