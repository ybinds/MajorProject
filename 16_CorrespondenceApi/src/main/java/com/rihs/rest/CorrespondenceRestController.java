package com.rihs.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rihs.binding.CorrespondenceResponse;
import com.rihs.service.ICorrespondenceService;

@RestController
@RequestMapping("/v1/api/correspondence")
public class CorrespondenceRestController {

	@Autowired
	private ICorrespondenceService service;
	
	@GetMapping("/send")
	public ResponseEntity<CorrespondenceResponse> sendCorrespondence(){
		CorrespondenceResponse response = service.sendCorrespondence();
		return ResponseEntity.ok(response);
	}
}
