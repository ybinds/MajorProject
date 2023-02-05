package com.rihs.service;

import com.rihs.binding.CasePlanResponse;
import com.rihs.binding.EducationDetailsRequest;
import com.rihs.binding.IncomeDetailsRequest;
import com.rihs.binding.KidsDetailsRequest;
import com.rihs.binding.PlanRequest;
import com.rihs.binding.SummaryResponse;

public interface ICaseService {

	CasePlanResponse createCase(Long appId);
	Long addPlan(PlanRequest request);
	Long addIncomeDetails(IncomeDetailsRequest request);
	Long addEducationDetails(EducationDetailsRequest request);
	SummaryResponse addKidsDetails(KidsDetailsRequest request);
}
