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
public class MyController {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public MyController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    } 

    @GetMapping("/")
    public String demo() {
        return "Welcome to subscription reminder app";
    }

    @GetMapping("/getallusers")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/sendTestEmail")
    public String sendTestEmail(@RequestParam String to) {
        try {
            emailService.sendEmail(to, "Test Email", "This is a test email.");
            return "Test email sent to " + to;
        } catch (Exception e) {
            return "Failed to send test email to " + to + ": " + e.getMessage();
        }
    }
}

