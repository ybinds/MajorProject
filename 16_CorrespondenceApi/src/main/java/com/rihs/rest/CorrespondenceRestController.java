package com.rihs.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/correspondence")
public class CorrespondenceRestController {

	@GetMapping("/send")
	public ResponseEntity<String> sendCorrespondence(){
		return null;
	}
}
