package com.rihs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rihs.binding.CasePlanResponse;
import com.rihs.binding.EducationDetailsRequest;
import com.rihs.binding.IncomeDetailsRequest;
import com.rihs.binding.KidRequest;
import com.rihs.binding.KidsDetailsRequest;
import com.rihs.binding.PlanRequest;
import com.rihs.entity.Case;
import com.rihs.entity.Education;
import com.rihs.entity.Income;
import com.rihs.entity.Kid;
import com.rihs.entity.Plan;
import com.rihs.exception.CaseNotFoundException;
import com.rihs.exception.PlanNotFoundException;
import com.rihs.repository.CaseRepository;
import com.rihs.repository.EducationRepository;
import com.rihs.repository.IncomeRepository;
import com.rihs.repository.KidRepository;
import com.rihs.repository.PlanRepository;

@Service
public class CaseServiceImpl implements ICaseService {

	@Autowired
	private CaseRepository crepo;

	@Autowired
	private PlanRepository prepo;
	
	@Autowired
	private IncomeRepository irepo;
	
	@Autowired
	private EducationRepository erepo;
	
	@Autowired
	private KidRepository krepo;

	public CasePlanResponse createCase(Long appId) {
		Case c = new Case();

		// I have to verify if the given appId is valid or not, for that I need to
		// inter-communicate with CitizenRegistrationApplicationApi
		// But since I am not running, for now I skipped that once that test is done,
		// then I will add the Feign and set
		// inter-communication
		c.setAppId(appId);
		Case savedCase = crepo.save(c); // create a fresh case
		CasePlanResponse response = new CasePlanResponse();
		response.setCaseNumber(savedCase.getCaseNumber());
		List<Plan> plans = prepo.findAll();
		response.setPlan(plans);
		return response;
	}

	public Long addPlan(PlanRequest request) {
		Case c = null;
		Long caseNumber = request.getCaseNumber();
		if (caseNumber == null || !crepo.existsById(caseNumber)) { // check if the case number sent exists or not
			throw new CaseNotFoundException("Case " + caseNumber + " IS NOT FOUND");
		} else {
			c = crepo.findById(caseNumber).get();
			// get plan if exists else throw exception
			Plan plan = prepo.findById(request.getPlanId()).orElseThrow(() -> new PlanNotFoundException("PLAN " + request.getPlanId() + " IS NOT FOUND"));
			c.setPlan(plan);
			crepo.save(c); // update the case record with plan 
		}
		return caseNumber;
	}

	public Long addIncomeDetails(IncomeDetailsRequest request) {
		Case c = null;
		Long caseNumber = request.getCaseNumber();
		if (caseNumber == null || !crepo.existsById(caseNumber)) { // check if the case number sent exists or not
			throw new CaseNotFoundException("Case " + caseNumber + " IS NOT FOUND");
		} else {
			c = crepo.findById(caseNumber).get();
			// set income details and save back
			Income income = new Income();
			income.setPropertyIncome(request.getPropertyIncome());
			income.setRentIncome(request.getRentIncome());
			income.setSalaryIncome(request.getSalaryIncome());
			Income inc = irepo.save(income);
			c.setIncomeDetails(inc);
			crepo.save(c); // update the case record with plan 
		}
		return caseNumber;
	}

	public Long addEducationDetails(EducationDetailsRequest request) {
		Case c = null;
		Long caseNumber = request.getCaseNumber();
		if (caseNumber == null || !crepo.existsById(caseNumber)) { // check if the case number sent exists or not
			throw new CaseNotFoundException("Case " + caseNumber + " IS NOT FOUND");
		} else {
			c = crepo.findById(caseNumber).get();
			// set education details and save back
			Education education= new Education();
			education.setHighestDegree(request.getHighestDegree());
			education.setGraduationYear(request.getGraduationYear());
			education.setUniversityName(request.getUniversityName());
			Education edu = erepo.save(education);
			c.setEducationDetails(edu);
			crepo.save(c); // update the case record with plan 
		}
		return caseNumber;
	}
	
	public String addKidsDetails(KidsDetailsRequest request) {
		Case c = null;
		Long caseNumber = request.getCaseNumber();
		if (caseNumber == null || !crepo.existsById(caseNumber)) { // check if the case number sent exists or not
			throw new CaseNotFoundException("Case " + caseNumber + " IS NOT FOUND");
		} else {
			c = crepo.findById(caseNumber).get();
			// set income details and save back
			List<Kid> kids = new ArrayList<>();
			for(KidRequest k: request.getKids()) {
				Kid kid = new Kid();
				BeanUtils.copyProperties(k, kid);
				Kid krec = krepo.save(kid);
				kids.add(krec);
			}
			c.setKids(kids);
			crepo.save(c); // update the case record with plan 
		}
		return "CASE " + caseNumber + " IS SUCCESSFULLY CREATED";
	}
}
