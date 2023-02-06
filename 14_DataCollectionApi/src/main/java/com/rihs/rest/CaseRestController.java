package com.rihs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rihs.binding.CasePlanResponse;
import com.rihs.binding.EducationDetailsRequest;
import com.rihs.binding.IncomeDetailsRequest;
import com.rihs.binding.KidsDetailsRequest;
import com.rihs.binding.PlanRequest;
import com.rihs.entity.Case;
import com.rihs.service.ICaseService;

@RestController
@RequestMapping("/v1/api/case")
public class CaseRestController {

	@Autowired
	private ICaseService service;
	
	@GetMapping("/create/{appId}")
	public ResponseEntity<CasePlanResponse> createCaseAndShowPlans(@PathVariable("appId") Long appId){
		CasePlanResponse response = service.createCase(appId);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/addPlan")
	public ResponseEntity<Long> addPlanAndShowIncomeDetails(
			@RequestBody PlanRequest request){
		Long caseNum = null;
		try {
			caseNum = service.addPlan(request);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.ok(caseNum);
	}
	
	@PostMapping("/addIncome")
	public ResponseEntity<Long> addIncomeDetailsAndShowEducationDetails(
			@RequestBody IncomeDetailsRequest request){
		Long caseNum = null;
		try {
			caseNum = service.addIncomeDetails(request);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.ok(caseNum);
	}
	
	@PostMapping("/addEducation")
	public ResponseEntity<Long> addEducationDetailsAndShowKidsDetails(
			@RequestBody EducationDetailsRequest request){
		Long caseNum = null;
		try {
			caseNum = service.addEducationDetails(request);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.ok(caseNum);
	}
	
	@PostMapping("/addKids")
	public ResponseEntity<Case> addKidsDetailsAndShowSummary(
			@RequestBody KidsDetailsRequest request){
		Case c = null;
		try {
			c = service.addKidsDetails(request);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return ResponseEntity.ok(c);
	}
}
