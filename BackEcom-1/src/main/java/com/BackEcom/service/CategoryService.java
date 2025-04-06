package com.BackEcom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackEcom.model.Category;
import com.BackEcom.repository.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo categoryRepository;

	//getALL
	public List<Category>getAllCategorys(){
		return categoryRepository.findAll();
	}
	//getById
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Categorie non trouvé"));
	}

	//POST
	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	//DELETE
	public void deleteCategory(Long id) {

		categoryRepository.deleteById(id);
	}

	//UPDATE
	public Category updateCategory(Long id, Category category) {
		Category existingCategory = categoryRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Categorie non trouvé"));
		existingCategory.setDescription(category.getDescription());
		existingCategory.setName(category.getName());

		return categoryRepository.save(existingCategory);
	}


}
