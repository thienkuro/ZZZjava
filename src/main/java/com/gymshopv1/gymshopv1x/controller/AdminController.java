package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.auth.RoleChecker;
import com.gymshopv1.gymshopv1x.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!RoleChecker.isAdmin(user)) {
            return "redirect:/customer/home";
        }

        model.addAttribute("username", user.getUsername());
        return "admin/dashboard";
    }

    @GetMapping("/admin/manage-products")
    public String manageProducts(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!RoleChecker.isAdmin(user)) {
            return "redirect:/customer/home";
        }

        return "admin/manage-products";
    }

    @GetMapping("/admin/manage-users")
    public String manageUsers(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!RoleChecker.isAdmin(user)) {
            return "redirect:/customer/home";
        }
    
        return "admin/manage-users";
    }
    @GetMapping("/admin/manage-orders")
    public String manageOrders(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!RoleChecker.isAdmin(user)) {
            return "redirect:/customer/home";
        }

        return "admin/manage-orders";
    }
}
