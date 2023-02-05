package com.rihs.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rihs.binding.CitizenRegistrationApplicationRequest;
import com.rihs.exception.CitizenDoesNotBelongException;
import com.rihs.service.ICitizenRegistrationApplicationService;

@RestController
public class CitizenRegistrationApplicationRestController {

	@Autowired
	private ICitizenRegistrationApplicationService service;
	
	private static final Logger logger=LoggerFactory.getLogger(CitizenRegistrationApplicationRestController.class);
	
	@PostMapping("/register")
	public ResponseEntity<String> registerCitizen(
			@RequestBody CitizenRegistrationApplicationRequest request){
		logger.info("Entering into register controller method");
		String regMsg;
		try {
			regMsg = service.registerCitizenApplication(request);
			logger.info("Exiting from register controller method");
		} catch(CitizenDoesNotBelongException cdbe) {
			logger.error("Error occurred in the register method -- " + cdbe.getMessage());
			cdbe.printStackTrace();
			throw cdbe;
		}
		return ResponseEntity.ok(regMsg);
	}
}