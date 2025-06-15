package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.service.ProductService;
import com.gymshopv1.gymshopv1x.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/customer/home")
    public String home(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products); // Đưa list sản phẩm vào view
        return "customer/home"; // trỏ tới src/main/resources/templates/customer/home.html
    }
}
