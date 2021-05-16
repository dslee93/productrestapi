package com.fdmgroup.ProductApi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.ProductApi.model.Product;
import com.fdmgroup.ProductApi.repositories.ProductsRepository;


@Service
public class ProductRestService {

	@Autowired
	ProductsRepository productrepository;
	
	public void addProduct(Product product) {
		productrepository.save(product);
	}
	
	public void deleteProduct(int id) {
		productrepository.deleteById(id);
	}
	
	public Optional<Product> getProduct(int id) {
		Optional<Product> product = productrepository.findById(id);
		return product;
	}
	
	public List<Product> getAllProducts() {
		List<Product> products = productrepository.findAll();
		return products;
	}
	
	public List<Product> getProductByName(String name) {
		List<Product> products = productrepository.findByName(name);
		return products;
	}
}
