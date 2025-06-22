package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/manage-products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ Hiển thị danh sách sản phẩm cho admin
    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Product> products;

        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchByTitle(keyword);
        } else {
            products = productService.getAllProducts();
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "admin/manage-products"; // templates/admin/manage-products.html
    }

    // ✅ Hiển thị form thêm sản phẩm
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-form"; // templates/admin/product-form.html
    }

    // ✅ Hiển thị form chỉnh sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        try {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
            return "admin/product-form";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "redirect:/admin/manage-products";
        }
    }

    // ✅ Lưu sản phẩm (thêm hoặc cập nhật)
    @PostMapping("/save")
    public String save(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/admin/manage-products";
    }

    // ✅ Xoá sản phẩm
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/admin/manage-products";
    }
}
