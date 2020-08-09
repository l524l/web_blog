package com.l524l.web_blog.controller;

import com.l524l.web_blog.models.User;
import com.l524l.web_blog.models.enumes.Role;
import com.l524l.web_blog.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class RegController {
    private static final Logger log = Logger.getLogger(RegController.class);

    private UserServiceImpl userService;

    public RegController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String reg(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "registration";
    }
    @PostMapping("/registration")
    public String addUsr(@ModelAttribute User user,
                         @RequestParam(name = "passwordcomfirm") String confirmPass,
                         Model model){
        if(confirmPass.equals(user.getPassword())){
            if (userService.saveUser(user)==null){
                model.addAttribute("error","Пользователь с таким именем существует");
                return "registration";
            }
        }else {
            model.addAttribute("error","Пароли не совпадают");
            return "registration";
        }
        return "redirect:/login";
    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActive = userService.activateUser(code);

        if (isActive) {
            model.addAttribute("message","Аккаунт активирован");
        } else {
            model.addAttribute("message","Аккаунт не активирован");
        }

        return "login";
    }

}
