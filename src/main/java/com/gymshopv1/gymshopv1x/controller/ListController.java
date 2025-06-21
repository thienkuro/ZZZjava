package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListController {

    @Autowired
    private ProductService productService;

    // Hiển thị trang giỏ hàng
    @GetMapping("/customer/product-list")
    public String showCartPage(Model model, HttpSession session) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        // Thêm tên người dùng nếu đã đăng nhập
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("username", loggedInUser.getUsername());
        }

        return "customer/product-list"; // trỏ đến templates/customer/cart.html
    }
}
