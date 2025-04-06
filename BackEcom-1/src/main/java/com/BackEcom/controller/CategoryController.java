package com.BackEcom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BackEcom.model.Category;
import com.BackEcom.service.CategoryService;

@RestController
@RequestMapping("/api/categorys")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	//getAll products
	@GetMapping
	public List<Category>getAllCategory(){
		return categoryService.getAllCategorys();
	}
	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable Long id) {
		return categoryService.getCategoryById(id);
	}

	@PostMapping
	public Category addCategory(@RequestBody Category category) {
		return categoryService.addCategory(category);

	}
	@PutMapping("/{id}")
	public Category updateCategory(@PathVariable Long id, @RequestBody Category cateogry) {
		return categoryService.updateCategory(id, cateogry);
	}

	@DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
		categoryService.deleteCategory(id);
    }

}
