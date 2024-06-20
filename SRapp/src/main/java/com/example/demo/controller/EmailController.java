package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/sendEmailToUsers")
    public String sendEmailToUsers(@RequestParam String subject, @RequestParam String text) {
        List<User> users = userService.findAllUsers(); // Fetch all users from the database
        for (User user : users) {
            emailService.sendEmail(user.getEmail(), subject, text); // Send email to each user
        }
        return "Emails sent successfully to all users";
    }
}
