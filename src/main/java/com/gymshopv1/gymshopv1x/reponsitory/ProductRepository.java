package com.gymshopv1.gymshopv1x.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gymshopv1.gymshopv1x.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
     List<Product> findByCategory(String category);
}

