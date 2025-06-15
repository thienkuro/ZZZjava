package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/customer/login")
    public String showLoginForm(Model model) {
        return "customer/login"; // Đường dẫn file HTML: templates/customer/login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {

        // Dùng username thay vì email để đăng nhập
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            session.setAttribute("loggedInUser", user.get());
            return "redirect:/products"; // hoặc trang chính
        } else {
            model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            return "customer/login";
        }
    }
}
