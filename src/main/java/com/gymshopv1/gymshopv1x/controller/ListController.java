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
            @RequestParam(name = "sold", required = false) Integer sold, // <-- THÊM DÒNG NÀY
            Model model,
            HttpSession session) {

        // Lấy người dùng từ session
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        // Lọc sản phẩm theo các tiêu chí
        List<Product> products;
        boolean hasKeyword = keyword != null && !keyword.isEmpty();
        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasSold = sold != null;

        if (hasKeyword && hasCategory && hasSold) {
            products = productService.searchByTitleAndCategoryAndSold(keyword, category, sold);
        } else if (hasKeyword && hasCategory) {
            products = productService.searchByTitleAndCategory(keyword, category);
        } else if (hasKeyword) {
            products = productService.searchByTitle(keyword);
        } else if (hasCategory) {
            products = productService.findByCategory(category);
        } else if (hasSold) {
            products = productService.findBySold(sold);
        } else {
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("sold", sold);

        return "customer/product-list";
    }
}
