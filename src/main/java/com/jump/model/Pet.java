package com.jump.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
	
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String type;
	private String subtype;
	private Integer age;
	private Integer ownerId;
	
}
