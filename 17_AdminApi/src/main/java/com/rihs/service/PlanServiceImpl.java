package com.rihs.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rihs.binding.PlanRequest;
import com.rihs.entity.Plan;
import com.rihs.exception.PlanNotFoundException;
import com.rihs.repository.PlanRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlanServiceImpl implements IPlanService{
	
	@Autowired
	private PlanRepository repo;
	
	public String savePlan(PlanRequest request) {
		log.info("Entering into savePlan method");
		Plan plan = new Plan();
		BeanUtils.copyProperties(request, plan);
		plan.setActive(true);
		Integer id = repo.save(plan).getId();
		log.info("Exiting from savePlan method");
		return "Plan with id: " + id + " saved successfully";
	}

	public List<Plan> getAllPlans() {
		return repo.findAll();
	}

	public Plan getOnePlan(Integer id) {
		return repo.findById(id).orElseThrow(() -> new PlanNotFoundException("Plan with id: " + id + " not found"));
	}

	public String deletePlan(Integer id) {
		repo.delete(getOnePlan(id));
		return "Plan with id: " + id + " deleted successfully";
	}

	public String togglePlan(Integer id) {
		Plan plan = getOnePlan(id);
		String msg = null;
		if(plan.isActive()) {
			plan.setActive(false);
			msg = "Plan is de-activated";
		} else {
			plan.setActive(true);
			msg = "Plan is activated";
		}
		repo.save(plan);
		return msg;
	}
}
