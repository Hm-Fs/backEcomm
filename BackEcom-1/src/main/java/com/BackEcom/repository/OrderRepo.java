package com.BackEcom.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackEcom.model.Order;
public interface OrderRepo extends JpaRepository<Order, Long> {

	List<Order> findByUserId(Long userId); // Récupérer les commandes d'un utilisateur

}
