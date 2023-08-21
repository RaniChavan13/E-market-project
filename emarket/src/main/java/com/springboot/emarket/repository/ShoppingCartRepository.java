package com.springboot.emarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.emarket.dto.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>
{

} 
