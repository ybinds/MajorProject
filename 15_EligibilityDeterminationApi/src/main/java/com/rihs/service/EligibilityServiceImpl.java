package com.rihs.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rihs.binding.EligibilityDetailsResponse;
import com.rihs.consumer.CasePlanApplicationConsumer;
import com.rihs.consumer.CitizenAppConsumerFeign;
import com.rihs.entity.Case;
import com.rihs.entity.CitizenRegistrationApplication;
import com.rihs.entity.EligibilityDetails;
import com.rihs.entity.Triggers;
import com.rihs.exception.CaseNotFoundException;
import com.rihs.exception.CitizenApplicationNotFoundException;
import com.rihs.repository.EligibilityDetailsRepository;
import com.rihs.repository.TriggersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EligibilityServiceImpl implements IEligibilityService {

	@Autowired
	private CasePlanApplicationConsumer consumer;
	
	@Autowired
	private CitizenAppConsumerFeign citizenConsumer;
	
	@Autowired
	private EligibilityDetailsRepository repo;

	@Autowired
	private TriggersRepository trepo;
	
	public EligibilityDetailsResponse determineEligibility(Long caseNumber) {
		log.info("Entering into determineEligibility method");
		// get case info
		ResponseEntity<Case> caseInfo = consumer.getCaseInfo(caseNumber);
		Case c = null;
		EligibilityDetails ed = new EligibilityDetails();
		if (caseInfo.getBody() == null) {
			log.error("CASE WITH " + caseNumber + " IS NOT FOUND");
			throw new CaseNotFoundException("CASE WITH " + caseNumber + " IS NOT FOUND");
		}
		c = caseInfo.getBody();
		
		// retrieve citizen application
		ResponseEntity<CitizenRegistrationApplication> response = citizenConsumer.getApplication(c.getAppId());
		CitizenRegistrationApplication citizen = null;
		if(response.getBody()==null) {
			log.error("CITIZEN APPLICATION WITH " + c.getAppId() + " IS NOT FOUND");
			throw new CitizenApplicationNotFoundException("CITIZEN APPLICATION WITH " + c.getAppId() + " IS NOT FOUND");
		}
		citizen=response.getBody();
		String reason = checkEligible(c,citizen);
		
		// if no reason for denial
		if(reason.isBlank()) {
			ed.setPlanStatus("Approved");
			ed.setBenefitAmount(350.0);
			LocalDate startDate = LocalDate.now().plusDays(1);
			ed.setPlanStartDate(startDate);
			ed.setPlanEndDate(startDate.plusMonths(3));
		} else { // in case a reason for denial
			ed.setPlanStatus("Denied");
			ed.setDenialReason(reason);
		}
		ed.setPlanName(c.getPlan().getName());
		ed.setCaseNum(caseNumber);
		ed.setHolderName(citizen.getCitizenFullName());
		ed.setHolderSsn(citizen.getCitizenSsn());
		EligibilityDetails savedED = repo.save(ed);
		
		// save in triggers table as well
		Triggers t = new Triggers();
		t.setCaseNum(caseNumber);
		t.setTriggerPdf(null);
		t.setTriggerStatus("Pending");
		trepo.save(t);
		
		EligibilityDetailsResponse EDResponse = new EligibilityDetailsResponse();
		BeanUtils.copyProperties(savedED, EDResponse);
		log.info("Exiting from determineEligibility method");
		return EDResponse;
	}

	private String checkEligible(Case c, CitizenRegistrationApplication citizen) {
		log.info("Entering into checkEligibility method");
		String reason = "";
		Integer planId = c.getPlan().getId();
		if(planId == 1 || planId == 2 || planId == 3) {
			if(c.getIncomeDetails().getSalaryIncome() > 300) {
				reason = "Salary Income criteria failed";
			} else if(planId == 2 && c.getKids().stream().count() <= 0){
				reason = "Kids count criteria failed";
			} else if(planId == 2 && c.getKids().stream().anyMatch(k -> k.getKidAge() > 16)) {
				reason = "Kids age criteria failed";
			} else if(planId == 3 && (c.getIncomeDetails().getPropertyIncome() > 0 || c.getIncomeDetails().getRentIncome() > 0)) {
				reason = "Property & Rent Income criteria failed";
			}
		} else if(planId == 4){
			Period p = Period.between(citizen.getCitizenDob(), LocalDate.now());
			if(p.getYears()<65) {
				reason = "Age criteria failed";
			}
		} else if(planId==6) {
			if(c.getIncomeDetails().getSalaryIncome() > 0) {
				reason = "Employment criteria failed";
			} else if(c.getEducationDetails().getGraduationYear()==null) {
				reason = "Graduation criteria failed";
			}
		} 
		log.info("Exiting from checkEligibility method");
		return reason;
	}

	@Override
	public EligibilityDetailsResponse getEligibilityDetails(Long caseNum) {
		EligibilityDetailsResponse edResponse = new EligibilityDetailsResponse();
		EligibilityDetails ed = repo.findByCaseNum(caseNum).orElseThrow(() -> new CaseNotFoundException("CASE WITH " + caseNum + " IS NOT FOUND"));
		BeanUtils.copyProperties(ed, edResponse);
		return edResponse;
	}

}
