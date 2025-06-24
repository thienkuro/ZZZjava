package com.gymshopv1.gymshopv1x.service;

import com.gymshopv1.gymshopv1x.entity.Order;
import com.gymshopv1.gymshopv1x.reponsitory.OrderRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

}
