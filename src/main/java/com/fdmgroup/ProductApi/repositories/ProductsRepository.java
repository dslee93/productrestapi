package com.fdmgroup.ProductApi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.ProductApi.model.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
	
//	@Query("select p from Product p where upper(p.name) like concat ('%', upper(:name), '%')")
	public List<Product> findByName(String name);

}
