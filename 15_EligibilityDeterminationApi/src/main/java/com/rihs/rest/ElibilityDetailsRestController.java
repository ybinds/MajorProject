package com.rihs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rihs.binding.EligibilityDetailsResponse;
import com.rihs.service.IEligibilityService;

@RestController
@RequestMapping("/v1/api/eligibility")
public class ElibilityDetailsRestController {
	
	@Autowired
	private IEligibilityService service;

	@GetMapping("/check/{caseNum}")
	public ResponseEntity<EligibilityDetailsResponse> determineEligibility(@PathVariable("caseNum") Long caseNum){
		ResponseEntity<EligibilityDetailsResponse> response = null;
		try {
			EligibilityDetailsResponse eligibility = service.determineEligibility(caseNum);
			response = ResponseEntity.ok(eligibility);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return response;
	}
	
	@GetMapping("/get/{caseNum}")
	public ResponseEntity<EligibilityDetailsResponse> getEligibilityDetails(@PathVariable("caseNum") Long caseNum){
		ResponseEntity<EligibilityDetailsResponse> response = null;
		try {
			EligibilityDetailsResponse ed = service.getEligibilityDetails(caseNum);
			response = ResponseEntity.ok(ed);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return response;
	}
	
	@GetMapping("/getApproved")
	public ResponseEntity<List<EligibilityDetailsResponse>> getApprovedEligibilityDetails(){
		List<EligibilityDetailsResponse> edr = service.getApprovedEligibilityDetails();
		return ResponseEntity.ok(edr);
	}
}
