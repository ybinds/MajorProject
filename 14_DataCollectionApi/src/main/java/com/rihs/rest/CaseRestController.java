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
import com.rihs.exception.CaseNotFoundException;
import com.rihs.service.ICaseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api/case")
@Slf4j
public class CaseRestController {

	@Autowired
	private ICaseService service;
	
	@GetMapping("/create/{appId}")
	public ResponseEntity<CasePlanResponse> createCaseAndShowPlans(@PathVariable("appId") Long appId){
		log.info("Entering into creatCaseAndShowPlans method");
		CasePlanResponse response = service.createCase(appId);
		log.info("Exiting from createCaseAndShowPlans method");
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/addPlan")
	public ResponseEntity<Long> addPlanAndShowIncomeDetails(
			@RequestBody PlanRequest request){
		log.info("Entering into addPlanAndShowIncomeDetails method");
		Long caseNum = null;
		try {
			caseNum = service.addPlan(request);
		} catch(Exception e) {
			log.error("Error occurred while adding plan");
			e.printStackTrace();
			throw e;
		}
		log.info("Exiting from addPlanAndShowIncomeDetails method");
		return ResponseEntity.ok(caseNum);
	}
	
	@PostMapping("/addIncome")
	public ResponseEntity<Long> addIncomeDetailsAndShowEducationDetails(
			@RequestBody IncomeDetailsRequest request){
		log.info("Entering into addIncomeDetailsAndShowEducationDetails method");
		Long caseNum = null;
		try {
			caseNum = service.addIncomeDetails(request);
		} catch(Exception e) {
			log.error("Error occurred while adding Income Details");
			e.printStackTrace();
			throw e;
		}
		log.info("Exiting from addIncomeDetailsAndShowEducationDetails method");
		return ResponseEntity.ok(caseNum);
	}
	
	@PostMapping("/addEducation")
	public ResponseEntity<Long> addEducationDetailsAndShowKidsDetails(
			@RequestBody EducationDetailsRequest request){
		log.info("Entering into addEducationDetailsAndShowKidsDetails method");
		Long caseNum = null;
		try {
			caseNum = service.addEducationDetails(request);
		} catch(Exception e) {
			log.error("Error occurred while adding Education Details");
			e.printStackTrace();
			throw e;
		}
		log.info("Exiting from addEducationDetailsAndShowKidsDetails method");
		return ResponseEntity.ok(caseNum);
	}
	
	@PostMapping("/addKids")
	public ResponseEntity<Case> addKidsDetailsAndShowSummary(
			@RequestBody KidsDetailsRequest request){
		log.info("Entering into addKidsDetailsAndShowSummary method");
		Case c = null;
		try {
			c = service.addKidsDetails(request);
		} catch(Exception e) {
			log.info("Error occurred while adding Kids Details");
			e.printStackTrace();
			throw e;
		}
		log.info("Exiting from addKidsDetailsAndShowSummary method");
		return ResponseEntity.ok(c);
	}
	
	@GetMapping("/get/{caseNumber}")
	public ResponseEntity<Case> getCaseInfo(@PathVariable("caseNumber") Long caseNumber){
		log.info("Entering into getCaseInfo method");
		ResponseEntity<Case> response = null;
		try {
			Case c = service.getCaseDetails(caseNumber);
			response = ResponseEntity.ok(c);
		} catch(CaseNotFoundException cnfe) {
			log.error("Error occurred while retrieving Case Details");
			cnfe.printStackTrace();
			throw cnfe;
		}
		log.info("Exiting from getCaseInfo method");
		return response;
	}
}
