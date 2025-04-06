package com.BackEcom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackEcom.model.CartItem;

	public interface CartItemRepo extends JpaRepository<CartItem, Long> {

		Optional<CartItem> findByCartIdAndProductId(Long cart_id, Long product_id);
	    void deleteByCart_id(Long cart_id);
	}
