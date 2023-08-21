package com.springboot.emarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.emarket.dto.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{

	Customer findByMobile(long mobile);

	Customer findByEmail(String email);

}
