package com.BackEcom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackEcom.model.User;
import com.BackEcom.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepository;

	//getALL
	public List<User>getAllUsers(){
		return userRepository.findAll();
	}
	//getById
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Utilisateur non trouvé"));
	}

	//POST
	public User addUser(User user) {

		return userRepository.save(user);
	}

	//DELETE
	public void deleteUser(Long id) {

		userRepository.deleteById(id);
	}

	//UPDATE
	public User updateUser(Long id, User user) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Utilisateur non trouvé"));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setRole(user.getRole());
		return userRepository.save(existingUser);
	}


}
