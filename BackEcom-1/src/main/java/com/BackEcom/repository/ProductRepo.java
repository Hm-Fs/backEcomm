package com.BackEcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackEcom.model.Product;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
	
	
	//search by name
	//List<Product> findByNameContaing(String name);

}
