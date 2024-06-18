package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.*;

@RestController
public class MyController {
	
	@Autowired
    private UserService userService;
	
	  @GetMapping("/")
	  public String demo() {
	        return "Welcome to subscription reminder app";
	  }
	  
	  @GetMapping("/getallusers")
	  public List<User> getAllUsers() {
	        return userService.findAllUsers();
	  }
	  
	  @Autowired
	    private EmailService emailService;

	    @GetMapping("/sendTestEmail")
	    public String sendTestEmail(@RequestParam String to) {
	        emailService.sendReminders();
	        return "Test email sent to " + to;
	    }
}
