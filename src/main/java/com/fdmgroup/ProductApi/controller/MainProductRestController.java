package com.fdmgroup.ProductApi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.ProductApi.exceptions.ProductNotFoundException;
import com.fdmgroup.ProductApi.model.Product;
import com.fdmgroup.ProductApi.services.ProductRestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class MainProductRestController {
	
	@Autowired 
	private ProductRestService productservice;

	@Operation(summary = "Fetch single product from the database.")
	@GetMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Fetched product from Db",
			content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404",
			description = "Not Available",
			content = @Content)
			})
	public Product getProduct(@PathVariable int id) throws ProductNotFoundException {
		Optional<Product> product = productservice.getProduct(id);
		 if(product.isEmpty())
			 throw new ProductNotFoundException("Product not found");
		return product.get();
	}
	
	@Operation(summary = "This is to fetch all the products in the database.")
	@GetMapping("")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
			description = "Fetched All the products from Db",
			content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404",
			description = "Not Available",
			content = @Content)
			})
	public List<Product> getProducts() {
		System.out.println("here");
		List<Product> products = productservice.getAllProducts();
		if(products == null)
			 throw new ProductNotFoundException("Product not found");
		return products;
	}
	
//	@GetMapping("/search")
//	public List<Product> getProductsByName(@RequestBody String name) {
//		List<Product> products = productservice.getProductByName(name);
//		return products;
//	}
	
	
	@Operation(summary = "This is to persist a product in the database.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201",
			description = "Persist to database",
			content = {@Content(mediaType = "application/json")})
			})
	@PostMapping
	public Product addProductToDatabase(@RequestBody Product product) {
		productservice.addProduct(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId()).toUri();
		ResponseEntity.created(location).build();
		return product;
	}
	
	@Operation(summary = "This is to delete the product from the database")
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable int id) {
		if (productservice.getProduct(id).isEmpty()) {
			throw new ProductNotFoundException("product does not exist");
		}
		productservice.deleteProduct(id);
	}
	
}
