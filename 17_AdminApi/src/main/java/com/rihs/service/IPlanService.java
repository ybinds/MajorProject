package com.rihs.service;

import java.util.List;

import com.rihs.binding.PlanRequest;
import com.rihs.entity.Plan;

public interface IPlanService {

	String savePlan(PlanRequest request);
	List<Plan> getAllPlans();
	Plan getOnePlan(Integer id);
	String deletePlan(Integer id);
	String togglePlan(Integer id);
}
