package com.springboot.emarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.emarket.dto.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{

	Wishlist findByName(String name);

}
