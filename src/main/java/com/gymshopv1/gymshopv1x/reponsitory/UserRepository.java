package com.gymshopv1.gymshopv1x.reponsitory;

import com.gymshopv1.gymshopv1x.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
