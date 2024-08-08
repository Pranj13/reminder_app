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
    private final String[] defaultRecipients = {"sprajakta783@gmail.com", "joshipornima9@gmail.com"};

    @Autowired
    public EmailService(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 13 11 * * ?")  // Runs every day at 12:05 PM
    public void sendReminders() {
        LocalDate today = LocalDate.now();
        LocalDate upcomingDate = today.plusDays(15);  // Adjust as needed

        List<User> users = userRepository.findByEndDateBetween(today, upcomingDate);

        for (User user : users) {
            try {
                // Send reminder to user
                sendEmail(user.getEmail(), "Subscription Reminder", "Dear " + user.getUsername() + ",\n\nYour subscription is ending on " 
                    + user.getEndDate() + ". Please renew your subscription.\n\nThank you!");
                logger.info("Reminder email sent to {}", user.getEmail());

                // Send notification to default recipients
                String summaryText = "User " + user.getUsername() + " (Email: " + user.getEmail() + ") has a subscription ending on " 
                    + user.getEndDate() + ".";
                sendSummaryEmail(summaryText);
            } catch (Exception e) {
                logger.error("Failed to send email to {}", user.getEmail(), e);
            }
        }
    }

    private void sendSummaryEmail(String text) {
        for (String recipient : defaultRecipients) {
            try {
                sendEmail(recipient, "Subscription Ending Notification", text);
                logger.info("Summary email sent to {}", recipient);
            } catch (Exception e) {
                logger.error("Failed to send summary email to {}", recipient, e);
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
