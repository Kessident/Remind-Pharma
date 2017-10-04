package com.cdodge.remindPharm.Controllers;

import com.cdodge.remindPharm.Models.*;
import com.cdodge.remindPharm.Repository.MedicationRepo;
import com.cdodge.remindPharm.Repository.UserRepo;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String getAllMeds(HttpSession session, Model model) {
        List<Medication> meds = users.findOne((int) session.getAttribute("userID")).getMedicationsTaking();

        model.addAttribute("meds", meds);
        return "meds";
    }

    @GetMapping("/{medicationID}")
    public String getOneMed(HttpSession session, Model model, @PathVariable int medicationID) {
        User loggedIn = users.findOne((int) session.getAttribute("userID"));
        if (medicationID < loggedIn.getMedicationsTaking().size()) {
            Medication taking = loggedIn.getMedicationsTaking().get(medicationID);
            model.addAttribute("medication", taking);
        } else {
            model.addAttribute("message", "User is not taking that a medication with that ID");
        }
        return "singleMed";
    }

    //add
    @GetMapping("/add")
    public String addMedGet(Model model) {
        return "editMed";
    }

    @PostMapping("/add")
    public String addMedPost(HttpSession session, Model model, String name, String deliveryMethod, String measure, int measureAmount, int timesPerDay, int numberOfDays, boolean shouldbeTakenWithFood, String description) {
        User loggedIn = users.findOne((int) session.getAttribute("userID"));
        Medication newMed = new Medication();
        newMed.setName(name);
        newMed.setDeliveryMethod(DeliveryMethod.valueOf(deliveryMethod));
        newMed.setMeasure(Measure.valueOf(measure));
        newMed.setMeasureAmount(measureAmount);
        newMed.setSchedule(new Schedule(timesPerDay, numberOfDays));
        newMed.setShouldBeTakenWithFood(shouldbeTakenWithFood);
        newMed.setDescription(description);

        loggedIn.addMedication(newMed);

        meds.save(newMed);
        users.save(loggedIn);

        return "redirect:/index";
    }

    @GetMapping("/edit/{medicationID}")
    public String editMedGet(@PathVariable int medicationID, Model model, HttpSession session) {
        User loggedIn = users.findOne((int) session.getAttribute("userID"));
        model.addAttribute("medicationTaking", loggedIn.getMedicationsTaking().get(medicationID));
        return "editMed";
    }

    @PostMapping("/edit/{medicationID}")
    public String editMedPost(@PathVariable int medicationID, HttpSession session, Model model, String name, String deliveryMethod, String measure, Integer measureAmount, Integer timesPerDay, Integer numberOfDays, boolean shouldbeTakenWithFood, String description) {
        User loggedIn = users.findOne((int) session.getAttribute("userID"));
        Medication medicationEditing = loggedIn.getMedicationsTaking().get(medicationID);
        if (isValidString(name)){
            medicationEditing.setName(name);
        }
        if (isValidString(deliveryMethod)){
            medicationEditing.setDeliveryMethod(DeliveryMethod.valueOf(deliveryMethod.toUpperCase()));
        }
        if (isValidString(measure)){
            medicationEditing.setMeasure(Measure.valueOf(measure.toUpperCase()));
        }
        if (isValidInteger(measureAmount)){
            medicationEditing.setMeasureAmount(measureAmount);
        }
        if (isValidInteger(timesPerDay)){
            medicationEditing.getSchedule().setTimesPerDay(timesPerDay);
        }
        if (isValidInteger(numberOfDays)){
            medicationEditing.getSchedule().setNumberOfDays(numberOfDays);
        }

        medicationEditing.setShouldBeTakenWithFood(shouldbeTakenWithFood);

        if (isValidString(description)){
            medicationEditing.setDescription(description);
        }
        loggedIn.getMedicationsTaking().set(medicationID, medicationEditing);
        users.save(loggedIn);
        model.addAttribute("message", "Medication updated successfully");
        return "index";
    }
    //delete
    private boolean isValidString(String validateMe){
        return !(validateMe == null || validateMe.isEmpty());
    }
    private boolean isValidInteger(Integer validateMe){
        return !(validateMe == null || validateMe <= 0);
    }
}
