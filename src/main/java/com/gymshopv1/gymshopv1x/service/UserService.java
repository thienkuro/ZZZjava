package com.gymshopv1.gymshopv1x.service;

import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.reponsitory.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
