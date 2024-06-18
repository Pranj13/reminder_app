package com.example.demo.controller;
import com.example.demo.entity.User;
import com.example.demo.service.*;
import com.example.demo.service.EmailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserService userService; // Assuming you have a UserService to interact with the user database

    @GetMapping("/sendEmailToUsers")
    public String sendEmailToUsers(@RequestParam String subject, @RequestParam String text) {
        List<User> users = userService.findAllUsers(); // Fetch all users from the database
        for (User user : users) {
            emailService.sendReminderEmail(user, subject, text); // Send email to each user
        }
        return "Emails sent successfully to all users";
    }

   
}
