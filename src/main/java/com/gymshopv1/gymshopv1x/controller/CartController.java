package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.entity.CartItem;
import com.gymshopv1.gymshopv1x.entity.Order;
import com.gymshopv1.gymshopv1x.entity.OrderStatus;
import com.gymshopv1.gymshopv1x.reponsitory.ProductRepository;
import com.gymshopv1.gymshopv1x.service.OrderService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

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
        Optional<CartItem> existing = cart.stream()
                .filter(i -> i.getProduct().getId().equals(productId)).findFirst();

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

    // ======= XỬ LÝ CHECKOUT =======
    @PostMapping("/checkout")
    public String checkout(
            HttpSession session,
            @RequestParam String receiverName,
            @RequestParam String receiverPhone,
            @RequestParam String shippingAddress,
            @RequestParam(required = false) String note,
            RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/customer/login";
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn đang trống!");
            return "redirect:/customer/cart";
        }

        for (CartItem item : cart) {
            Order order = new Order();
            order.setUserId(user.getId()); // chỉ lưu ID
            order.setProductId(item.getProduct().getId()); // chỉ lưu ID
            order.setQuantity(item.getQuantity());
            order.setOrderDate(LocalDate.now());
            order.setStatus(OrderStatus.Đã_đặt);

            order.setReceiverName(receiverName);
            order.setReceiverPhone(receiverPhone);
            order.setShippingAddress(shippingAddress);
            order.setNote(note);

            orderService.save(order); // Lưu vào DB
        }

        session.removeAttribute("cart");
        redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công!");

        return "redirect:/customer/cart";
    }

    @GetMapping("/checkout")
    public String showCheckoutForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/customer/login";
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn đang trống!");
            return "redirect:/customer/cart";
        }

        double total = cart.stream().mapToDouble(CartItem::getTotalPrice).sum();
        model.addAttribute("total", total);
        return "customer/checkout";
    }

}
