package com.cdodge.remindPharm.Controllers;

import com.cdodge.remindPharm.Repository.MedicationRepo;
import com.cdodge.remindPharm.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller("/medication")
public class MedicationController {

    private UserRepo users;
    private MedicationRepo meds;

    @Autowired
    public MedicationController(UserRepo users, MedicationRepo meds) {
        this.users = users;
        this.meds = meds;
    }

    @GetMapping("/all")
    public String getAllMeds(HttpSession session, Model model){
        return "meds";
    }
}
