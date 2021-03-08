package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.service.UserRepr;
import ru.geekbrains.service.UserService;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login_form";
    }

    @GetMapping("/access_denied")
    public String accessDenied(){
        return "access_denied";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
//        logger.info("Register new user request");

        model.addAttribute("user", new UserRepr());
        return "user_register";
    }

    @PostMapping("/user_register")
    public String userRegister(@ModelAttribute("user") UserRepr user, Model model){
        userService.save(user);
        return "redirect:/user";
    }
}
