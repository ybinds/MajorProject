package com.rihs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rihs.binding.CasePlanResponse;
import com.rihs.binding.EducationDetailsRequest;
import com.rihs.binding.IncomeDetailsRequest;
import com.rihs.binding.KidRequest;
import com.rihs.binding.KidsDetailsRequest;
import com.rihs.binding.PlanRequest;
import com.rihs.consumer.CitizenAppConsumerFeign;
import com.rihs.entity.Case;
import com.rihs.entity.CitizenRegistrationApplication;
import com.rihs.entity.Education;
import com.rihs.entity.Income;
import com.rihs.entity.Kid;
import com.rihs.entity.Plan;
import com.rihs.exception.CaseNotFoundException;
import com.rihs.exception.CaseRaisedForApplicationException;
import com.rihs.exception.CitizenApplicationNotFoundException;
import com.rihs.exception.PlanNotFoundException;
import com.rihs.repository.CaseRepository;
import com.rihs.repository.EducationRepository;
import com.rihs.repository.IncomeRepository;
import com.rihs.repository.KidRepository;
import com.rihs.repository.PlanRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

	@Autowired
	private CitizenAppConsumerFeign consumer;

	public CasePlanResponse createCase(Long appId) {
		log.info("Entering into createCase method");
		Case c = new Case();
		ResponseEntity<CitizenRegistrationApplication> application = null;
		try {
			application = consumer.getApplication(appId);
			if (application.getBody() != null) {
				Case appAvailable = crepo.findByAppId(appId);
				if (appAvailable == null) {
					c.setAppId(appId);
					Case savedCase = crepo.save(c); // create a fresh case
					CasePlanResponse response = new CasePlanResponse();
					response.setCaseNumber(savedCase.getCaseNumber());
					List<Plan> plans = prepo.findByActive(true);
					response.setPlan(plans);
					return response;
				} else {
					log.warn("Case is raised for application " + appId + " already");
					throw new CaseRaisedForApplicationException("Case is raised for application " + appId + " already");
				}
			} else {
				log.warn("Application id: " + appId + " did not yield any result");
			}
		} catch (CitizenApplicationNotFoundException cnfe) {
			log.error("Error occurred while retrieving Citizen Application");
			cnfe.printStackTrace();
			throw cnfe;
		}
		log.info("Exiting from createCase method");
		return null;
	}

	public Long addPlan(PlanRequest request) {
		log.info("Entering into addPlan method");
		Case c = null;
		Long caseNumber = request.getCaseNumber();
		if (caseNumber == null || !crepo.existsById(caseNumber)) { // check if the case number sent exists or not
			log.error("No Case was found with the given caseNumber " + caseNumber);
			throw new CaseNotFoundException("CASE " + caseNumber + " NOT FOUND");
		} else {
			c = crepo.findById(caseNumber).get();
			// get plan if exists else throw exception
			Plan plan = prepo.findById(request.getPlanId())
					.orElseThrow(() -> new PlanNotFoundException("PLAN " + request.getPlanId() + " IS NOT FOUND"));
			c.setPlan(plan);
			crepo.save(c); // update the case record with plan
		}
		log.info("Exiting from addPlan method");
		return caseNumber;
	}

	public Long addIncomeDetails(IncomeDetailsRequest request) {
		log.info("Entering into addIncomeDetails method");
		Case c = null;
		Long caseNumber = request.getCaseNumber();
		if (caseNumber == null || !crepo.existsById(caseNumber)) { // check if the case number sent exists or not
			log.error("No Case was found with the given caseNumber " + caseNumber);
			throw new CaseNotFoundException("CASE " + caseNumber + " NOT FOUND");
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
		log.info("Exiting from addIncomeDetails method");
		return caseNumber;
	}

	public Long addEducationDetails(EducationDetailsRequest request) {
		log.info("Entering into addEducationDetails method");
		Case c = null;
		Long caseNumber = request.getCaseNumber();
		if (caseNumber == null || !crepo.existsById(caseNumber)) { // check if the case number sent exists or not
			log.error("No Case was found with the given caseNumber " + caseNumber);
			throw new CaseNotFoundException("CASE " + caseNumber + " NOT FOUND");
		} else {
			c = crepo.findById(caseNumber).get();
			// set education details and save back
			Education education = new Education();
			education.setHighestDegree(request.getHighestDegree());
			education.setGraduationYear(request.getGraduationYear());
			education.setUniversityName(request.getUniversityName());
			Education edu = erepo.save(education);
			c.setEducationDetails(edu);
			crepo.save(c); // update the case record with plan
		}
		log.info("Exiting from addEducationDetails method");
		return caseNumber;
	}

	public Case addKidsDetails(KidsDetailsRequest request) {
		log.info("Entering into addKidsDetails method");
		Case c = null;
		Long caseNumber = request.getCaseNumber();
		if (caseNumber == null || !crepo.existsById(caseNumber)) { // check if the case number sent exists or not
			log.error("No Case was found with the given caseNumber " + caseNumber);
			throw new CaseNotFoundException("CASE " + caseNumber + " NOT FOUND");
		} else {
			c = crepo.findById(caseNumber).get();
			// set income details and save back
			List<Kid> kids = new ArrayList<>();
			for (KidRequest k : request.getKids()) {
				Kid kid = new Kid();
				BeanUtils.copyProperties(k, kid);
				Kid krec = krepo.save(kid);
				kids.add(krec);
			}
			c.setKids(kids);
			c = crepo.save(c); // update the case record with plan
		}
		log.info("Exiting from addKidsDetails method");
		return c;
	}

	@Override
	public Case getCaseDetails(Long caseNumber) {
		return crepo.findById(caseNumber)
				.orElseThrow(() -> new CaseNotFoundException("Case " + caseNumber + " not found"));
	}
}
