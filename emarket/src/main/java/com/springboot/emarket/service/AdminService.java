package com.springboot.emarket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.springboot.emarket.dto.Customer;
import com.springboot.emarket.dto.Merchant;
import com.springboot.emarket.dto.Payment;
import com.springboot.emarket.dto.Product;
import com.springboot.emarket.helper.Login;
import com.springboot.emarket.repository.CustomerRepository;
import com.springboot.emarket.repository.MerchantRepository;
import com.springboot.emarket.repository.PaymentRepository;
import com.springboot.emarket.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	MerchantRepository merchantRepository;

	@Autowired
	PaymentRepository paymentRepository;

	public String login(Login login, ModelMap model, HttpSession session) {
		// TODO Auto-generated method stub
		model.put("name", "Admin");

		if (login.getEmail().equals("admin")) {
			if (login.getPassword().equals("admin")) {
				session.setAttribute("admin", "admin");
				model.put("pass", "Login Success");
				return "AdminHome";
			} else {
				model.put("fail", "Incorrect Password");
			}
		} else {
			model.put("fail", "Incorrect Email");
		}
		return "AdminLogin";
	}

	public String fetchAllProducts(ModelMap model) {
		// TODO Auto-generated method stub

		List<Product> list = productRepository.findAll();
		if (list.isEmpty()) {
			model.put("fail", "No Product Found");
			return "AdminHome";
		} else {
			model.put("products", list);
			return "AdminDisplayProduct";
		}
	}

	public String changeStatus(ModelMap model, int id) {
		// TODO Auto-generated method stub

		Product product = productRepository.findById(id).orElse(null);
		if (product.isStatus()) {
			product.setStatus(false);
		} else {
			product.setStatus(true);
		}
		productRepository.save(product);
		model.put("pass", "status changed Success");
		List<Product> list = productRepository.findAll();
		if (list.isEmpty()) {
			model.put("fail", "No product found");
			return "AdminHome";
		} else {
			model.put("products", list);
			return "AdminDisplayProduct";
		}
	}

	public String viewCustomers(ModelMap model) {
		// TODO Auto-generated method stub
		List<Customer> list = customerRepository.findAll();
		if (list.isEmpty()) {
			model.put("fail", "no customer Data Found");
			return "AdminHome";
		} else {
			model.put("customers", list);
			return "AdminDisplayCustomer";
		}
	}

	public String viewMerchants(ModelMap model) {
		// TODO Auto-generated method stub
		List<Merchant> list = merchantRepository.findAll();
		if (list.isEmpty()) {
			model.put("fail", "No Merchant Found");
			return "AdminHome";
		} else {
			model.put("merchants", list);
			return "AdminDisplayMerchant";
		}
	}

	public String addPaymentpage(Payment payment, ModelMap model) {
		// TODO Auto-generated method stub
		Payment payment2 = paymentRepository.findByName(payment.getName());
		if (payment2 == null) {
			paymentRepository.save(payment);
			model.put("pass", "payment method added successfully");
			return "AdminHome";
		} else {
			model.put("fail", "Payment method already exists");
			return "AddPaymentOption";
		}
	}
}
