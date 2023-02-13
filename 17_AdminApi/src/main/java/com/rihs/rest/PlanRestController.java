package com.rihs.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rihs.binding.PlanRequest;
import com.rihs.entity.Plan;
import com.rihs.exception.PlanNotFoundException;
import com.rihs.service.IPlanService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api/plan")
@Slf4j
public class PlanRestController {

	@Autowired
	private IPlanService service;
	
	@PostMapping("/create")
	public ResponseEntity<String> createPlan(
			@RequestBody PlanRequest request){
		log.info("Entering into createPlan method");
		ResponseEntity<String> response = null;
		try {
			String msg = service.savePlan(request);
			if(msg == null) {
				log.warn("savePlan returned null response");
			}
			response = ResponseEntity.ok(msg);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred while creating Plan");
		}
		log.info("Exiting from createPlan method");
		return response;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Plan>> getAllPlans(){
		return ResponseEntity.ok(service.getAllPlans());
	}
	
	@GetMapping("/fetch/{id}")
	public ResponseEntity<Plan> getOnePlan(@PathVariable("id") Integer id){
		log.info("Entering into getOnePlan method");
		ResponseEntity<Plan> response = null;
		try {
			Plan cw = service.getOnePlan(id);
			if(cw == null) {
				log.warn("getOnePlan return null response");
			}
			response = ResponseEntity.ok(cw);
		} catch(PlanNotFoundException cnfe) {
			cnfe.printStackTrace();
			log.error(cnfe.getMessage());
			throw cnfe;
		}
		log.info("Exiting from getOnePlan method");
		return response;
	}
	
	@PutMapping("/modify")
	public ResponseEntity<String> updatePlan(@RequestBody PlanRequest request){
		log.info("Entering into updatePlan method");
		ResponseEntity<String> response = null;
		try {
			String msg = service.savePlan(request);
			if(msg == null) {
				log.warn("savePlan returned null response");
			}
			response = ResponseEntity.ok(msg);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred while updating Plan");
		}
		log.info("Exiting from updatePlan method");
		return response;
	}
	
	public ResponseEntity<String> deletePlan(@PathVariable("id") Integer id){
		log.info("Entering into deletePlan method");
		ResponseEntity<String> response = null;
		try {
			String msg = service.deletePlan(id);
			if(msg == null) {
				log.warn("deletePlan return null response");
			}
			response = ResponseEntity.ok(msg);
		} catch(PlanNotFoundException cnfe) {
			cnfe.printStackTrace();
			log.error(cnfe.getMessage());
			throw cnfe;
		}
		log.info("Exiting from deletePlan method");
		return response;
	}
	
	@PatchMapping("/toggle/{id}")
	public ResponseEntity<String> togglePlan(@PathVariable("id") Integer id){
		log.info("Entering into togglePlan method");
		ResponseEntity<String> response = null;
		try {
			String msg = service.togglePlan(id);
			if(msg==null) {
				log.warn("togglePlan returned null response");
			}
			response = ResponseEntity.ok(msg);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred while updating Plan");
		}
		log.info("Exiting from togglePlan method");
		return response;
	}
}
