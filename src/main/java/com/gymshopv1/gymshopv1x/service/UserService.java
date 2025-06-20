package com.gymshopv1.gymshopv1x.service;

import java.util.Optional;

import com.gymshopv1.gymshopv1x.entity.User;

public interface UserService {
    boolean register(User user);
    Optional<User> findByUsername(String username);
}
