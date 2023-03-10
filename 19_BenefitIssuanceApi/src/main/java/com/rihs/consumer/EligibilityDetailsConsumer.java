package com.rihs.consumer;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.rihs.binding.EligibilityDetailsResponse;

@FeignClient("ELIGIBILITY-DETERMINATION-API")
public interface EligibilityDetailsConsumer {

	@GetMapping("/v1/api/eligibility/getApproved")
	public ResponseEntity<List<EligibilityDetailsResponse>> getApprovedEligibilityDetails();
}
