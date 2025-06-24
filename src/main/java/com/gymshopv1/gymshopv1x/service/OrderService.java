package com.gymshopv1.gymshopv1x.service;

import java.util.List;

import com.gymshopv1.gymshopv1x.entity.Order;

public interface OrderService {
    void save(Order order);

    List<Order> findAll();

}
