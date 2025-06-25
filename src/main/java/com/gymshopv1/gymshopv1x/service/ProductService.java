package com.gymshopv1.gymshopv1x.service;

import java.util.List;
import com.gymshopv1.gymshopv1x.entity.Product;

public interface ProductService {

    // CRUD cơ bản
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product);
    void deleteById(Long id);

    // Truy vấn nâng cao
    List<Product> getAllProducts(); // tương đương findAll(), có thể bỏ nếu không cần

    // Tìm kiếm theo tiêu đề (keyword) - không phân biệt hoa thường
    List<Product> searchByTitle(String keyword);
    Product findByTitle(String title); // hoặc findByTitleIgnoreCase

    // Tìm kiếm theo category hoặc kết hợp
    List<Product> findByCategory(String category);
    List<Product> searchByTitleAndCategory(String title, String category);
    List<Product> searchByTitleAndCategoryAndSold(String title, String category, int sold);

    // Tìm theo số lượng đã bán
    List<Product> findBySold(int sold);
    

    // Sắp xếp theo giá
    List<Product> findAllOrderByPriceAsc();
    List<Product> findAllOrderByPriceDesc();

    // Sắp xếp theo số lượng đã bán
    List<Product> findAllOrderBySoldAsc();
    List<Product> findAllOrderBySoldDesc();

    
}
