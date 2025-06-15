package com.gymshopv1.gymshopv1x.service;

import java.util.List;

import com.gymshopv1.gymshopv1x.entity.Product;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}

