package com.gymshopv1.gymshopv1x.service;

import java.util.List;
import java.util.Optional;

import com.gymshopv1.gymshopv1x.entity.Product;

public interface ProductService {
    List<Product> findAll();

    Product findById(Long id);

    Product save(Product product);

    void deleteById(Long id);

    List<Product> getAllProducts();

    List<Product> searchByTitle(String keyword);

    List<Product> findByCategory(String category);

    List<Product> searchByTitleAndCategory(String title, String category);

    List<Product> findAllOrderByPriceAsc();

    List<Product> findAllOrderByPriceDesc();

    Product findByTitle(String title); // hoặc findByTitleIgnoreCase

}
