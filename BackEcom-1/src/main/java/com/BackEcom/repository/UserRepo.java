package com.BackEcom.repository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackEcom.model.User;

@Repository
public interface UserRepo extends JpaRepository <User,Long> {

	 Optional<User> findByUsername(String username);

}
