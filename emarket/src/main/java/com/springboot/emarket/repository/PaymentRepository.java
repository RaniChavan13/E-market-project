package com.springboot.emarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.emarket.dto.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	Payment findByName(String name);

}
