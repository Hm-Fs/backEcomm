package com.BackEcom.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackEcom.model.OrderItem;
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

	List<OrderItem> findByOrderId(Long orderId); // Récupérer les articles d'une commande

}
