package com.cdodge.remindPharm.Controllers;

import com.cdodge.remindPharm.Models.User;
import com.cdodge.remindPharm.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
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

        if (errors.size() > 0) {
            model.addAttribute("errorMessages", errors);
            return "register";
        }

        User exists = users.findByEmail(email);
        if (exists != null){
            model.addAttribute("alreadyExists", "User already exists");
            return "register";
        } else {
            User newUser = new User(name, email, BCrypt.hashpw(password, BCrypt.gensalt()));
            if (!(phoneNumber == null || phoneNumber.isEmpty())){
                newUser.setPhoneNumber(phoneNumber);
            }

            users.save(newUser);
            model.addAttribute("registered", "Successfully registered");
            return "login";
        }
    }

    @GetMapping("login")
    public String loginGET(HttpSession session){
        if (session.getAttribute("userID") != null) {
            return "index";
        } else {
            return "login";
        }
    }

    @PostMapping("login")
    public String loginPost(String email, String password, HttpSession session, Model model){
        User found = users.findByEmail(email);
        if (found == null) {
            model.addAttribute("notFound", "User not found");
            return "login";
        }

        boolean rightPass = BCrypt.checkpw(password, found.getPassword());

        if (rightPass){
            session.setAttribute("userID", found.getUserID());
            return "index";
        } else {
            model.addAttribute("wrongPass", "Incorrect username/password combination");
            return "login";
        }
    }
}
