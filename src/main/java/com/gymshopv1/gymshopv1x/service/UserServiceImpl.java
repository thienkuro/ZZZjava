package com.gymshopv1.gymshopv1x.service;

import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean register(User user) {
        // Kiểm tra trùng username hoặc email
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            return false;
        }

        // Không mã hóa mật khẩu
        user.setCreatedAt(new Date());
        user.setAdmin(false); // mặc định không phải admin
        user.setStatus("active");

        // Lưu user vào DB
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

}
