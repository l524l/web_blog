package com.l524l.web_blog.controller;

import com.l524l.web_blog.exception.EmailExistError;
import com.l524l.web_blog.exception.PasswordConfirmError;
import com.l524l.web_blog.exception.UserExistError;
import com.l524l.web_blog.models.User;
import com.l524l.web_blog.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String addUsr(@Valid User user,
                         BindingResult bindingResult,
                         Model model){
        if(!bindingResult.hasErrors()){
            try {
                userService.saveUser(user);
            } catch (PasswordConfirmError passwordConfirmError) {
                model.addAttribute("user",user);
                model.addAttribute("confirmError", true);
                return "registration";
            } catch (UserExistError userExistError) {
                model.addAttribute("user",user);
                model.addAttribute("error","Пользователь с таким именем существует");
                return "registration";
            } catch (EmailExistError emailExistError) {
                model.addAttribute("user",user);
                model.addAttribute("error","Email занят");
                return "registration";
            }
            return "redirect:/login";
        }else {
            boolean nameErrors = !bindingResult
                    .getFieldErrors("name")
                    .isEmpty();
            boolean emailErrors = !bindingResult
                    .getFieldErrors("email")
                    .isEmpty();
            boolean passwordErrors = !bindingResult
                    .getFieldErrors("password")
                    .isEmpty();

            model.addAttribute("nameError", nameErrors);
            model.addAttribute("emailError", emailErrors);
            model.addAttribute("passwordError", passwordErrors);
            model.addAttribute("user",user);
            return "registration";
        }
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
