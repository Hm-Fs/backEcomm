package com.BackEcom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackEcom.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>{
	Optional<Cart> findByUserId(Long userId);
}
