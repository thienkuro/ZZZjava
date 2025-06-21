package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.reponsitory.ProductRepository;
import com.gymshopv1.gymshopv1x.service.CartService;

import jakarta.servlet.http.HttpSession;

import com.gymshopv1.gymshopv1x.entity.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer/cart")
public class CartController {

    @GetMapping
    public String showCart(HttpSession session, Model model) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        double total = cart.stream().mapToDouble(CartItem::getTotalPrice).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "customer/cart";
    }

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null)
            cart = new ArrayList<>();
        session.setAttribute("cart", cart);

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("success", "Sản phẩm không tồn tại.");
            return "redirect:/customer/product-list";
        }

        Product product = productOpt.get();
        Optional<CartItem> existing = cart.stream().filter(i -> i.getProduct().getId().equals(productId)).findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            cart.add(new CartItem(product, quantity));
        }

        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng!");
        return "redirect:/customer/product-list";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProduct().getId().equals(productId));
            session.setAttribute("cart", cart);
        }
        return "redirect:/customer/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("cart");
        redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công!");
        return "redirect:/customer/cart";
    }
}
