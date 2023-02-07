package com.rihs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rihs.entity.EligibilityDetails;
import com.rihs.service.IEligibilityService;

@RestController
@RequestMapping("/v1/api/eligibility")
public class ElibilityDetailsRestController {
	
	@Autowired
	private IEligibilityService service;

	@GetMapping("/check/{caseNum}")
	public ResponseEntity<EligibilityDetails> determineEligibility(@PathVariable("caseNum") Long caseNum){
		ResponseEntity<EligibilityDetails> response = null;
		try {
			EligibilityDetails eligibility = service.determineEligibility(caseNum);
			response = ResponseEntity.ok(eligibility);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return response;
	}
}
