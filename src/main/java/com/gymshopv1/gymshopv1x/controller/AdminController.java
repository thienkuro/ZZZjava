package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.auth.RoleChecker;
// import com.gymshopv1.gymshopv1x.entity.Order;
import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.entity.User;
// import com.gymshopv1.gymshopv1x.service.OrderService;
import com.gymshopv1.gymshopv1x.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    // @Autowired
    // private OrderService orderService;

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!RoleChecker.isAdmin(user)) {
            return "redirect:/customer/home";
        }

        // Lấy danh sách sản phẩm
        List<Product> products = productService.findAll();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("products", products); // Gửi sản phẩm xuống view
        return "admin/dashboard";
    }

    @GetMapping("/admin/manage-users")
    public String manageUsers(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!RoleChecker.isAdmin(user)) {
            return "redirect:/customer/home";
        }

        return "admin/manage-users";
    }

    // @GetMapping("/admin/manage-orders")
    // public String manageOrders(HttpSession session, Model model) {
    // User user = (User) session.getAttribute("loggedInUser");
    // if (!RoleChecker.isAdmin(user)) {
    // return "redirect:/customer/home";
    // }

    // List<Order> orders = orderService.getAllOrders();
    // model.addAttribute("orders", orders);
    // return "admin/manage-orders";
    // }

}
