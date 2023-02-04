package com.rihs.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rihs.exception.CitizenDoesNotBelongException;

@RestControllerAdvice
public class CitizenRegAppExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<String> showCustomExceptionMessage(CitizenDoesNotBelongException cdbe){
		return new ResponseEntity<String>(cdbe.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
