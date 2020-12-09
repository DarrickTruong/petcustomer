package com.jump.controller.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jump.exception.CustomerIdMismatchException;
import com.jump.exception.CustomerNotFoundException;


import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class CustomerControllerAdvice {

		@ExceptionHandler(CustomerNotFoundException.class)
		public ResponseEntity<ExceptionResponse> handleNotFound(CustomerNotFoundException e) {
			ExceptionResponse response = new ExceptionResponse("Customer Not Found", "Customer-404", new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		@ExceptionHandler(CustomerIdMismatchException.class)
		public ResponseEntity<ExceptionResponse> handleTodoIdMismatch(CustomerIdMismatchException e) {
			ExceptionResponse response = 
					new ExceptionResponse("ID in path and Request body do not match", "TODO-400", new Date());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
}
