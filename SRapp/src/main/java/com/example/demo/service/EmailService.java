package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(cron = "0 05 15 * * ?")  // Runs every day at 9 AM
    public void sendReminders() {
        LocalDate today = LocalDate.now();
        LocalDate upcomingDate = today.plusDays(15);  // Adjust as needed

        List<User> users = userRepository.findByEndDateBetween(today, upcomingDate);

        for (User user : users) {
            sendReminderEmail(user, "Subscription Reminder", "Dear " + user.getUsername() + ",\n\nYour subscription is ending on " 
                + user.getEnddate() + ". Please renew your subscription.\n\nThank you!");
        }
    }

    public void sendReminderEmail(User user, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }



}