package com.rihs.service;

import java.util.List;

import com.rihs.binding.EligibilityDetailsResponse;

public interface IEligibilityService {

	EligibilityDetailsResponse determineEligibility(Long caseNumber);

	EligibilityDetailsResponse getEligibilityDetails(Long caseNum);
	
	List<EligibilityDetailsResponse> getApprovedEligibilityDetails();
}
