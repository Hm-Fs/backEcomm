package com.BackEcom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackEcom.model.Product;
import com.BackEcom.repository.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepository;

	//getALL
	public List<Product>getAllProducts(){
		return productRepository.findAll();
	}
	//getById
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Produit non trouvé"));
	}

	//POST
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	//DELETE
	public void deleteProduct(Long id) {

		productRepository.deleteById(id);
	}

	//UPDATE
	public Product updateProduct(Long id, Product product) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Produit non trouvé"));
		existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
		return productRepository.save(existingProduct);
	}

	
	// public List<Product> searchProducts(String name){
		// if (name !=null && !name.isEmpty()) {
			// return productRepository.findByNameContaing(name);
		 //}
		 //return productRepository.findAll();
	// }


}
