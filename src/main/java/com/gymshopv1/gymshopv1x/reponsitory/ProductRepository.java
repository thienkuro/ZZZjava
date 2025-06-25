package com.gymshopv1.gymshopv1x.reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gymshopv1.gymshopv1x.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
     List<Product> findByCategory(String category);

     List<Product> findByTitleContainingIgnoreCase(String keyword);

     // ✅ Thêm mới để lọc kết hợp cả tên và danh mục
     List<Product> findByTitleContainingIgnoreCaseAndCategory(String title, String category);

     List<Product> findAllByOrderByPriceAsc();

     List<Product> findAllByOrderByPriceDesc();

     Optional<Product> findByTitle(String title); // nếu phân biệt hoa thường

     Optional<Product> findByTitleIgnoreCase(String title); // nếu không phân biệt

     List<Product> findByTitleContainingIgnoreCaseAndCategoryAndSold(String title, String category, int sold);

     List<Product> findAllByOrderBySoldAsc();

     List<Product> findAllByOrderBySoldDesc();

     List<Product> findBySold(int sold);

}
