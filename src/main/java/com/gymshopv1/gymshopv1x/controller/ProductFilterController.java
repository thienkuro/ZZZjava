package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/filter")
public class ProductFilterController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String filterProducts(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "sortPrice", required = false) String sortPrice,
            @RequestParam(name = "view", defaultValue = "home") String view,
            Model model) {

        List<Product> products;

        // Bước 1: lọc theo từ khóa và/hoặc danh mục
        if (keyword != null && !keyword.isEmpty() && category != null && !category.isEmpty()) {
            products = productService.searchByTitleAndCategory(keyword, category);
        } else if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchByTitle(keyword);
        } else if (category != null && !category.isEmpty()) {
            products = productService.findByCategory(category);
        } else {
            products = productService.findAll();
        }

        // Bước 2: sắp xếp theo giá nếu có
        if ("asc".equals(sortPrice)) {
            products = products.stream()
                    .sorted(Comparator.comparingInt(Product::getPrice))
                    .collect(Collectors.toList());
        } else if ("desc".equals(sortPrice)) {
            products = products.stream()
                    .sorted(Comparator.comparingInt(Product::getPrice).reversed())
                    .collect(Collectors.toList());
        }

        // Truyền lại dữ liệu để giữ trạng thái form
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("sortPrice", sortPrice);
        model.addAttribute("view", view); // ✅ cần dòng này

        // Trả về trang đang lọc
        if ("list".equals(view)) {
            return "customer/product-list";
        } else if ("admin".equals(view) || "dashboard".equals(view)) {
            return "admin/dashboard";
        } else {
            return "customer/home";
        }

    }
}
