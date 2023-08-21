package com.book.store.bookstore.controller;

import com.book.store.bookstore.dto.UserDto;
import com.book.store.bookstore.dto.UserUpdateDto;
import com.book.store.bookstore.service.UserService;
import com.book.store.bookstore.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;

    private final UserValidator userValidator;

    @Autowired
    public AuthController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = UserDto.builder().build();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration( @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        userValidator.validate(user, result);
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/profile")
    public String getProfile(Model model){
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        UserUpdateDto user = userService.getUser(loggedInUserEmail);
        model.addAttribute("user", user);
        return "profile";
    }


    @PostMapping("/profile/save")
    public String updateUser( @ModelAttribute("user") UserUpdateDto userUpdateDto,
                                BindingResult result,
                                Model model) throws Exception {

        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        //userValidator.validate(userUpdateDto, result);
        if (result.hasErrors()) {
            model.addAttribute("user", userUpdateDto);
            return "profile";
        }

        userService.updateUser(userUpdateDto, loggedInUserEmail);
        return "redirect:/items";
    }

}
