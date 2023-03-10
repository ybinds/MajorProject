package com.rihs.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rihs.binding.CitizenRegistrationApplicationRequest;
import com.rihs.entity.CitizenRegistrationApplication;
import com.rihs.exception.CitizenApplicationNotFoundException;
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
		} catch(CitizenDoesNotBelongException cdbe) {
			logger.error("Error occurred in the register method -- " + cdbe.getMessage());
			cdbe.printStackTrace();
			throw cdbe;
		}
		logger.info("Exiting from register controller method");
		return ResponseEntity.ok(regMsg);
	}
	
	@GetMapping("/fetch/{appId}")
	public ResponseEntity<CitizenRegistrationApplication> getApplication(
			@PathVariable("appId") Long appId){
		logger.info("Entering into getApplication controller method");
		ResponseEntity<CitizenRegistrationApplication> response = null;
		try {
			CitizenRegistrationApplication application = service.getApplication(appId);
			response = ResponseEntity.ok(application);
		} catch(CitizenApplicationNotFoundException cnfe) {
			logger.error("Error occurred in the getApplication method -- " + cnfe.getMessage());
			cnfe.printStackTrace();
			throw cnfe;
		}
		logger.info("Exiting from getApplication controller method");
		return response;
	}
}
