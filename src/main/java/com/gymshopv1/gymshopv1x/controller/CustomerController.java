package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.CartItem;
import com.gymshopv1.gymshopv1x.entity.Order;
import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.service.OrderService;
import com.gymshopv1.gymshopv1x.service.ProductService;
import com.gymshopv1.gymshopv1x.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    // ======= TRANG CHỦ =======
    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        return "customer/home";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "customer/login";
    }

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
                session.setAttribute("username", user.getUsername());

                if (Boolean.TRUE.equals(user.getAdmin())) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/customer/product-list";
                }
            }
        }

        model.addAttribute("error", "Sai email hoặc mật khẩu");
        return "customer/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/customer/home";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "customer/register";
    }

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

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/customer/login";
        }
        model.addAttribute("user", user);
        return "customer/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            return "redirect:/customer/login";
        }

        currentUser.setFullName(updatedUser.getFullName());
        currentUser.setAddress(updatedUser.getAddress());
        currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setPassword(updatedUser.getPassword());
        currentUser.setUpdatedAt(new Date());

        userService.save(currentUser);
        session.setAttribute("loggedInUser", currentUser);
        redirectAttributes.addFlashAttribute("success", "Thông tin cá nhân đã được cập nhật thành công!");
        return "redirect:/customer/profile";
    }

}
