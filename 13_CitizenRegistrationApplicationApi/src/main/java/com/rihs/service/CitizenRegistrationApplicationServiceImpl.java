package com.rihs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.rihs.binding.CitizenRegistrationApplicationRequest;
import com.rihs.entity.CitizenRegistrationApplication;
import com.rihs.exception.CitizenDoesNotBelongException;
import com.rihs.repository.CitizenRegistrationApplicationRepository;

public class CitizenRegistrationApplicationServiceImpl implements ICitizenRegistrationApplicationService {

	private static final String REST_URL="http://ssawebapi-env.eba-k88bsahp.ap-south-1.elasticbeanstalk.com/ssn/{ssn}";
	
	private static final Logger logger = LoggerFactory.getLogger(CitizenRegistrationApplicationServiceImpl.class);
	
	@Autowired
	private CitizenRegistrationApplicationRepository repo;
	
	@Override
	public String registerCitizenApplication(CitizenRegistrationApplicationRequest request) {
		logger.info("Entering into register service method");
		
		String message = null;
		//RestTemplate rt = new RestTemplate();
		//ResponseEntity<String> response = rt.getForEntity(REST_URL, String.class, request.getSsn());
		//if("Rhode Island".equals(response.getBody())) {
		WebClient webClient = WebClient.create();
		String stateName = webClient.get()
				.uri(REST_URL, request.getSsn())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		if("Rhode Island".equals(stateName)) {
			CitizenRegistrationApplication citizen = new CitizenRegistrationApplication();
			BeanUtils.copyProperties(request,citizen);
			CitizenRegistrationApplication savedCitizen = repo.save(citizen);
			message = "Application with id: " + savedCitizen.getCitizenAppId() + " is created successfully";
		} else {
			logger.warn("Warning: Citizen does not belong to Rhode Island State");
			throw new CitizenDoesNotBelongException("Citizen does not belong to Rhode Island");
		}
		logger.info("Exiting from register service method");
		return message;
	}
}