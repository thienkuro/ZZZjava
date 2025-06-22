package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.service.ProductService;
import com.gymshopv1.gymshopv1x.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // ======= TRANG CHỦ =======
    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("username", user.getUsername()); // để hiện tên tài khoản
        }

        return "customer/home";
    }

    // ======= FORM ĐĂNG NHẬP =======
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "customer/login";
    }

    // ======= XỬ LÝ ĐĂNG NHẬP =======
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                session.setAttribute("loggedInUser", user);

                // Điều hướng theo vai trò
                if (Boolean.TRUE.equals(user.getAdmin())) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/customer/product-list";
                }
            }
        }

        // Trường hợp sai
        model.addAttribute("error", "Sai email hoặc mật khẩu");
        return "customer/login";
    }

    // ======= ĐĂNG XUẤT =======
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/customer/home";
    }

    // ======= FORM ĐĂNG KÝ =======
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "customer/register";
    }

    // ======= XỬ LÝ ĐĂNG KÝ =======
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user,
            RedirectAttributes redirectAttributes,
            Model model) {
        boolean success = userService.register(user);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công!");
            return "redirect:/customer/home";
        } else {
            model.addAttribute("errorMessage", "Email hoặc tên đăng nhập đã tồn tại!");
            return "customer/register";
        }
    }
}
