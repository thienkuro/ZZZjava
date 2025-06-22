package com.gymshopv1.gymshopv1x.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gymshopv1.gymshopv1x.entity.Product;
import com.gymshopv1.gymshopv1x.reponsitory.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        log.info("Lấy tất cả sản phẩm");
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        log.info("Tìm sản phẩm với ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
    }

    @Override
    public Product save(Product product) {
        log.info("Lưu sản phẩm: {}", product.getTitle());
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Xóa sản phẩm với ID: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Lấy tất cả sản phẩm (getAllProducts)");
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchByTitle(String keyword) {
        log.info("Tìm sản phẩm theo từ khóa: {}", keyword);
        return productRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> searchByTitleAndCategory(String title, String category) {
        return productRepository.findByTitleContainingIgnoreCaseAndCategory(title, category);
    }

    @Override
    public List<Product> findAllOrderByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Product> findAllOrderByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

}
