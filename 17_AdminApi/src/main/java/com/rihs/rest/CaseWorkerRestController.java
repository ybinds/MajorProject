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

import com.rihs.binding.CaseWorkerReq;
import com.rihs.entity.CaseWorker;
import com.rihs.exception.CaseWorkerNotFoundException;
import com.rihs.service.ICaseWorkerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api/caseworker")
@Slf4j
public class CaseWorkerRestController {

	@Autowired
	private ICaseWorkerService service;
	
	@PostMapping("/create")
	public ResponseEntity<String> createCaseWorker(
			@RequestBody CaseWorkerReq request){
		log.info("Entering into createCaseWorker method");
		ResponseEntity<String> response = null;
		try {
			String msg = service.saveCaseWorker(request);
			if(msg == null) {
				log.warn("saveCaseWorker returned null response");
			}
			response = ResponseEntity.ok(msg);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred while creating Case Worker");
		}
		log.info("Exiting from createCaseWorker method");
		return response;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CaseWorker>> getAllCaseWorkers(){
		return ResponseEntity.ok(service.getAllCaseWorkers());
	}
	
	@GetMapping("/fetch/{id}")
	public ResponseEntity<CaseWorker> getOneCaseWorker(@PathVariable("id") Integer id){
		log.info("Entering into getOneCaseWorker method");
		ResponseEntity<CaseWorker> response = null;
		try {
			CaseWorker cw = service.getOneCaseWorker(id);
			if(cw == null) {
				log.warn("getOneCaseWorker return null response");
			}
			response = ResponseEntity.ok(cw);
		} catch(CaseWorkerNotFoundException cnfe) {
			cnfe.printStackTrace();
			log.error(cnfe.getMessage());
			throw cnfe;
		}
		log.info("Exiting from getOneCaseWorker method");
		return response;
	}
	
	@PutMapping("/modify")
	public ResponseEntity<String> updateCaseWorker(@RequestBody CaseWorkerReq request){
		log.info("Entering into updateCaseWorker method");
		ResponseEntity<String> response = null;
		try {
			String msg = service.saveCaseWorker(request);
			if(msg == null) {
				log.warn("saveCaseWorker returned null response");
			}
			response = ResponseEntity.ok(msg);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred while updating Case Worker");
		}
		log.info("Exiting from updateCaseWorker method");
		return response;
	}
	
	public ResponseEntity<String> deleteCaseWorker(@PathVariable("id") Integer id){
		log.info("Entering into deleteCaseWorker method");
		ResponseEntity<String> response = null;
		try {
			String msg = service.deleteCaseWorker(id);
			if(msg == null) {
				log.warn("deleteCaseWorker return null response");
			}
			response = ResponseEntity.ok(msg);
		} catch(CaseWorkerNotFoundException cnfe) {
			cnfe.printStackTrace();
			log.error(cnfe.getMessage());
			throw cnfe;
		}
		log.info("Exiting from deleteCaseWorker method");
		return response;
	}
	
	@PatchMapping("/toggle/{id}")
	public ResponseEntity<String> toggleCaseWorker(@PathVariable("id") Integer id){
		log.info("Entering into toggleCaseWorker method");
		ResponseEntity<String> response = null;
		try {
			String msg = service.toggleCaseWorker(id);
			if(msg==null) {
				log.warn("toggleCaseWorker returned null response");
			}
			response = ResponseEntity.ok(msg);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Exception occurred while updating Case Worker");
		}
		log.info("Exiting from toggleCaseWorker method");
		return response;
	}
}
