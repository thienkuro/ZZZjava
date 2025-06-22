package com.gymshopv1.gymshopv1x.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gymshopv1.gymshopv1x.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
     List<Product> findByCategory(String category);

     List<Product> findByTitleContainingIgnoreCase(String keyword);

     // ✅ Thêm mới để lọc kết hợp cả tên và danh mục
     List<Product> findByTitleContainingIgnoreCaseAndCategory(String title, String category);

     List<Product> findAllByOrderByPriceAsc();

     List<Product> findAllByOrderByPriceDesc();

}
