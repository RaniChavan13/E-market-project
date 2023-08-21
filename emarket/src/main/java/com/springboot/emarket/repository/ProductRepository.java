package com.springboot.emarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.emarket.dto.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findByStatus(boolean flag);

	Product findByName(String name);

}
