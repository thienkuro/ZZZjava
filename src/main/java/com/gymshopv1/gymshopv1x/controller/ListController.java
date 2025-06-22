package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListController {

    @Autowired
    private ProductService productService;

    @GetMapping("/customer/product-list")
    public String showProductList(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            Model model,
            HttpSession session) {

        // ✅ Lấy người dùng từ session và thêm username vào model
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        // ✅ Lọc sản phẩm theo keyword và category
        List<Product> products;

        if (keyword != null && !keyword.isEmpty() && category != null && !category.isEmpty()) {
            products = productService.searchByTitleAndCategory(keyword, category);
        } else if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchByTitle(keyword);
        } else if (category != null && !category.isEmpty()) {
            products = productService.findByCategory(category);
        } else {
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        return "customer/product-list";
    }
}
