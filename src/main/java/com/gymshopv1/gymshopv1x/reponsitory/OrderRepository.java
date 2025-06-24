package com.gymshopv1.gymshopv1x.reponsitory;

import com.gymshopv1.gymshopv1x.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Có thể thêm các hàm custom sau này nếu cần
}
