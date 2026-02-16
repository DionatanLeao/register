package com.tdd.register.service;

import com.tdd.register.domain.User;
import com.tdd.register.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        var age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();

        if(age < 18) {
            throw new RuntimeException("Age not allowed");
        }
        return userRepository.save(user);
    }
}
