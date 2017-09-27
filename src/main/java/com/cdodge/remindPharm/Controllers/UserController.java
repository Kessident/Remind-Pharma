package com.cdodge.remindPharm.Controllers;

import com.cdodge.remindPharm.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserRepo users;

    @Autowired
    public UserController(UserRepo users) {
        this.users = users;
    }

    @GetMapping("/register")
    public String loginGet(){
        return "login";
    }

    @PostMapping("/register")
    public String loginPost(String name, String email, String password, String passwordConfirm, String phoneNumber, Model model){
        List<String> errors = new ArrayList<>();
        if (name == null || name.isEmpty()){
            errors.add("Please enter a name.");
        }
        if (email == null || email.isEmpty()){
            errors.add("Please enter an email.");
        }
        if (password == null || password.isEmpty()){
            errors.add("Please enter a password.");
        } else if (passwordConfirm == null || passwordConfirm.isEmpty()){
            errors.add("Please enter a password confirmation.");
        } else if (!password.equals(passwordConfirm)){
            errors.add("Passwords do not match.");
        }

        if (errors.size() > 0){
            model.addAttribute("errorMessages", errors);
            return "register";
        }
        return null;
    }
}
