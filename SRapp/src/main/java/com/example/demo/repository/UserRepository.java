package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.User;
import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods (if needed)
    List<User> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
    User findByUsername(String username);
}
