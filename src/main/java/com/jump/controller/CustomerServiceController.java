package com.jump.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jump.exception.CustomerIdMismatchException;
import com.jump.model.Customer;
import com.jump.model.Pet;
import com.jump.service.CustomerService;
import com.jump.service.PetService;

@RestController
//@RequestMapping("customer")
//@CrossOrigin(origins = "http://localhost:3001/")
public class CustomerServiceController {

	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	PetService ps;
	

	@GetMapping
	public ResponseEntity<List<Customer>> getAll() {
		List<Customer> customers = customerService.getCustomers();

		for (Customer c : customers) {
			c.setPetList((ps.findPetsByCustomerId(c.getId())));
		}
		return ResponseEntity.ok(customers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
		
		Customer customer = customerService.getCustomer(id);
		customer.setPetList(ps.findPetsByCustomerId(customer.getId()));
		return ResponseEntity.ok(customer);
	}
	
	@PostMapping
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws URISyntaxException {
		System.out.println(customer);
		Customer result = customerService.addCustomer(customer);
		
		List<Pet> petList = customer.getPetList();
		for(Pet p : petList) {
			p.setOwnerId(result.getId());
			ps.addPet(p);
		}
		
		
    	result.setPetList(ps.findPetsByCustomerId(result.getId()));
    	
    	
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(result.getId())
				.toUri();
		
		return ResponseEntity
				.created(location)
				.build();
	}
	
	@PostMapping("/only")
	public ResponseEntity<Customer> addCustomerOnly(@RequestBody Customer customer) throws URISyntaxException {
		
		
		Customer result = customerService.addCustomer(customer);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(result.getId())
				.toUri();
		
		return ResponseEntity
				.created(location)
				.build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LinkedHashMap<String, String> user) throws LoginException {
		System.out.println("user " + user.get("name"));
		List<Customer> customers = customerService.getCustomers();
		
		for (Customer c : customers) {
			if (c.getName().equals(user.get("name"))) {
				if (c.getPhone().equals(user.get("phone"))) {
					c.setPetList(ps.findPetsByCustomerId(c.getId()));
					return ResponseEntity.ok(c);
				} else {
					throw new LoginException();
				}
			} else {
				throw new LoginException();
			}
		}
		return null;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
		customerService.getCustomer(id);
		if( id == customer.getId()) {
			customerService.updateCustomer(customer);
			return ResponseEntity.ok("Profile Updated!");
		} else {
			 throw new CustomerIdMismatchException();
			
		}
	}
}
