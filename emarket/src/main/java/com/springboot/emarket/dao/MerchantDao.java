package com.springboot.emarket.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.emarket.dto.Merchant;
import com.springboot.emarket.dto.Product;
import com.springboot.emarket.repository.MerchantRepository;
import com.springboot.emarket.repository.ProductRepository;

@Repository
public class MerchantDao {
	
	@Autowired
	MerchantRepository merchantRepository;
	
	@Autowired
	ProductRepository productRepository;

	public Merchant findByEmail(String email) {
		return merchantRepository.findByEmail(email);
	}

	public Merchant save(Merchant merchant) {
		// TODO Auto-generated method stub
		return merchantRepository.save(merchant);
	}

	public Object findByMobile(Long mobile) {
		// TODO Auto-generated method stub
		return merchantRepository.findByMobile(mobile);
	}

	public Product findProductByName(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByName(name);
	}

	public Product findProductById(int id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElse(null);
	}

	public void removeProduct(Product product) {
		// TODO Auto-generated method stub
		productRepository.delete(product);
	}

}
