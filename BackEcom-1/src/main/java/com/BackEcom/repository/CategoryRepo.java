package com.BackEcom.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.BackEcom.model.Category;
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
