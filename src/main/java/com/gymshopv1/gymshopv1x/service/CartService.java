package com.gymshopv1.gymshopv1x.service;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.entity.CartItem;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CartService {
    private Map<Long, CartItem> cartItems = new HashMap<>();

    public void addToCart(Product product, int quantity) {
        CartItem item = cartItems.get(product.getId());
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cartItems.put(product.getId(), new CartItem(product, quantity));
        }
    }

    public void removeFromCart(Long productId) {
        cartItems.remove(productId);
    }

    public Collection<CartItem> getCartItems() {
        return cartItems.values();
    }

    public int getTotal() {
        return cartItems.values().stream()
            .mapToInt(CartItem::getTotalPrice)
            .sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}
