package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 10 11 * * ?")  // Runs every day at 12:05 PM
    public void sendReminders() {
        LocalDate today = LocalDate.now();
        LocalDate upcomingDate = today.plusDays(15);  // Adjust as needed

        List<User> users = userRepository.findByEndDateBetween(today, upcomingDate);

        for (User user : users) {
            try {
                sendEmail(user.getEmail(), "Subscription Reminder", "Dear " + user.getUsername() + ",\n\nYour subscription is ending on " 
                    + user.getEndDate() + ". Please renew your subscription.\n\nThank you!");
                logger.info("Reminder email sent to {}", user.getEmail());
            } catch (Exception e) {
                logger.error("Failed to send email to {}", user.getEmail(), e);
            }
        }
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
