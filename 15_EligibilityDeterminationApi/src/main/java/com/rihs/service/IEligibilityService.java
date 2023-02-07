package com.rihs.service;

import com.rihs.entity.EligibilityDetails;

public interface IEligibilityService {

	EligibilityDetails determineEligibility(Long caseNumber);
}
