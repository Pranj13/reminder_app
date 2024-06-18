package com.example.demo.entity;


import jakarta.persistence.Column;
//import java.time.LocalDateTime; // Import for date and time
import java.time.LocalDate; // Import for date
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;
    
    @Column(name = "start_date")
    private LocalDate startDate; // Use LocalDate for date

    @Column(name = "end_date")
    private LocalDate endDate;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getStartdate() {
        return startDate;
    }

    public void setPassword(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEnddate() {
        return endDate;
    }

    public void setEnddate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}

