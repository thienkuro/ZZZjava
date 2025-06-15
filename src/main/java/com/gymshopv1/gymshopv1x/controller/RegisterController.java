// package com.gymshopv1.gymshopv1x.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.gymshopv1.gymshopv1x.entity.User;

// @Controller
// @RequestMapping("/customer")
// public class RegisterController {

// @GetMapping("/dangky")
// public String showRegisterForm(Model model) {
// model.addAttribute("user", new User());
// return "customer/register"; // Gọi tới templates/customer/register.html
// }

// @PostMapping("/dangky")
// public String processRegisterForm(@ModelAttribute("user") User user) {
// System.out.println("Đăng ký từ khách hàng:");
// System.out.println("Họ tên: " + user.getFullname());
// System.out.println("Email: " + user.getEmail());
// System.out.println("SĐT: " + user.getPhone());
// System.out.println("Mật khẩu: " + user.getPassword());

// return "redirect:/dangnhap";
// }
// }
