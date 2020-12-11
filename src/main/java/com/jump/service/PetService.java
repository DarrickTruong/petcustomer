package com.jump.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jump.model.Pet;

@FeignClient(name = "pet-microservice")
public interface PetService {

	@PostMapping
	Pet addPet(@RequestBody Pet pet);
	
	@GetMapping("customer/{id}")
	List<Pet> findPetsByCustomerId(@PathVariable Integer id);
}
