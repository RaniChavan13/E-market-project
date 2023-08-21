package com.springboot.emarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.emarket.dto.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, String>{

	Merchant findByEmail(String email);

	Merchant findByMobile(Long mobile);

}
