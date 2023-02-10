package com.rihs.service;

import com.rihs.binding.EligibilityDetailsResponse;

public interface IEligibilityService {

	EligibilityDetailsResponse determineEligibility(Long caseNumber);

	EligibilityDetailsResponse getEligibilityDetails(Long caseNum);
}
