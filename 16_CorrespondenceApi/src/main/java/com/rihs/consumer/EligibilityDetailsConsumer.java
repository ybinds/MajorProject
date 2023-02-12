package com.rihs.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rihs.binding.EligibilityDetailsResponse;

@FeignClient("ELIGIBILITY-DETERMINATION-API")
public interface EligibilityDetailsConsumer {

	@GetMapping("/v1/api/eligibility/get/{caseNum}")
	public ResponseEntity<EligibilityDetailsResponse> getEligibilityDetails(@PathVariable("caseNum") Long caseNum);
}
