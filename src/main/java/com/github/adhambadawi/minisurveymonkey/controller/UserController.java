package com.github.adhambadawi.minisurveymonkey.controller;

import com.github.adhambadawi.minisurveymonkey.model.User;
import com.github.adhambadawi.minisurveymonkey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
