package com.gymshopv1.gymshopv1x.controller;

import com.gymshopv1.gymshopv1x.auth.RoleChecker;
import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.entity.User;
import com.gymshopv1.gymshopv1x.entity.Order;
import com.gymshopv1.gymshopv1x.entity.OrderDTO;
import com.gymshopv1.gymshopv1x.reponsitory.OrderRepository;
import com.gymshopv1.gymshopv1x.reponsitory.ProductRepository;
import com.gymshopv1.gymshopv1x.reponsitory.UserRepository;
import com.gymshopv1.gymshopv1x.service.OrderService;
import com.gymshopv1.gymshopv1x.service.ProductService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (!RoleChecker.isAdmin(user)) {
            return "redirect:/customer/home";
        }

        List<Product> products = productService.findAll();
        model.addAttribute("username", user.getUsername());
        model.addAttribute("products", products);
        return "admin/dashboard";
    }

    @GetMapping("/admin/manage-orders")
    public String listOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());

            // Lấy tên người dùng từ userId
            userRepository.findById(order.getUserId())
                    .ifPresent(user -> dto.setUserFullName(user.getFullName()));

            // Lấy tên sản phẩm và giá từ productId
            productRepository.findById(order.getProductId())
                    .ifPresent(product -> {
                        dto.setProductName(product.getTitle());
                        dto.setProductPrice(product.getPrice()); // 👈 Thêm giá
                        dto.setTotalPrice(product.getPrice() * order.getQuantity()); // 👈 Nếu cần tính tổng
                    });

            dto.setQuantity(order.getQuantity());
            dto.setOrderDate(order.getOrderDate());
            dto.setStatus(order.getStatus());
            dto.setReceiverName(order.getReceiverName());
            dto.setReceiverPhone(order.getReceiverPhone());
            dto.setShippingAddress(order.getShippingAddress());
            dto.setNote(order.getNote());

            orderDTOs.add(dto);
        }

        model.addAttribute("orders", orderDTOs);
        return "admin/manage-orders";
    }

}
