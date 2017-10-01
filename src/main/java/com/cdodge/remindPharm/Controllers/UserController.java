package com.cdodge.remindPharm.Controllers;

import com.cdodge.remindPharm.Models.User;
import com.cdodge.remindPharm.Repository.UserRepo;
import org.jetbrains.annotations.Contract;
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

    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/register")
    public String loginGet(){
        return "register";
    }

    @PostMapping("/register")
    public String loginPost(String name, String email, String password, String passwordConfirm, String phoneNumber, Model model){
        List<String> errors = new ArrayList<>();
        if (isInvalid(name)){
            errors.add("Please enter a name.");
        }
        if (isInvalid(email)){
            errors.add("Please enter an email.");
        }
        if (isInvalid(password)){
            errors.add("Please enter a password.");
        } else if (isInvalid(passwordConfirm)){
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

    @GetMapping("/login")
    public String loginGET(HttpSession session){
        if (session.getAttribute("userID") != null) {
            return "index";
        } else {
            return "login";
        }
    }

    @PostMapping("/login")
    public String loginPost(String email, String password, HttpSession session, Model model){
        User found = users.findByEmail(email);
        if (found == null) {
            model.addAttribute("error", "User not found");
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

    @PostMapping("/edit")
    public String editUserInfo(String password, String passwordConfirm, String oldPassword, String phoneNumber, String phoneNumberConfirm, HttpSession session, Model model){
        if (session.getAttribute("userID") == null){
            model.addAttribute("error", "You must be logged in to do that");
            return "login";
        }

        List<String> messages = new ArrayList<>();

        User loggedIn = users.findOne( (int) session.getAttribute("userID"));

        if (isValid(password) && isValid(passwordConfirm) && isValid(oldPassword)){
            if (password.equals(passwordConfirm)){
                if (BCrypt.checkpw(oldPassword, loggedIn.getPassword())){
                    loggedIn.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                    messages.add("Password successfully changed");
                } else {
                    messages.add("Incorrect password");
                }
            } else {
                messages.add("Passwords do not match");
            }
        }

        if (isValid(phoneNumber) && isValid(phoneNumberConfirm)){
            if (phoneNumber.equals(phoneNumberConfirm)){
                loggedIn.setPassword(phoneNumber);
                messages.add("phone number successfully changed");
            } else {
                messages.add("Phone numbers do not match");
            }
        }

        model.addAttribute("messages", messages);
        return "index";
    }

    @Contract(value = "null -> false", pure = true)
    private boolean isValid(String toBeValidated){
        return !(toBeValidated == null || toBeValidated.isEmpty());
    }


    @Contract(value = "null -> true", pure = true)
    private boolean isInvalid(String toBeValidated){
        return toBeValidated == null || toBeValidated.isEmpty();
    }
}