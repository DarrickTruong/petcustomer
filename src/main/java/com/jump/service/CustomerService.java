package com.jump.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jump.exception.CustomerNotFoundException;
//import com.jump.exception.CustomerNotFound;
import com.jump.model.Customer;
import com.jump.model.Pet;
import com.jump.repository.CustomerRepository;

@Service
public class CustomerService {
	
	
	@Autowired
	CustomerRepository cr;
	
	
	public Customer addCustomer(Customer customer) {
		return cr.save(customer);
	}

	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return cr.findAll();
	}

	public List<Customer> getCustomers() {
		return cr.findAll();
	}
	
	public Customer getCustomer(Integer id) {
		//return cr.findById(id)//.orElseThrow(CustomerNotFound::new);
		return cr.findById(id).orElseThrow(CustomerNotFoundException::new);
	}
	
	
	
	public void deleteCustomer(Integer id) {
		getCustomer(id);
		cr.deleteById(id);
	}

	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return cr.save(customer);
	}

}
