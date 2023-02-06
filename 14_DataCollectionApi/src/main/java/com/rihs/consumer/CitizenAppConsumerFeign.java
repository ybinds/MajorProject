package com.rihs.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rihs.binding.CitizenRegistrationApplicationRequest;
import com.rihs.entity.CitizenRegistrationApplication;

@FeignClient("CITIZENREGISTRATIONAPPLICATIONAPI")
public interface CitizenAppConsumerFeign {

	@PostMapping("/register")
	public ResponseEntity<String> registerCitizen(@RequestBody CitizenRegistrationApplicationRequest request);
	
	@GetMapping("/fetch/{appId}")
	public ResponseEntity<CitizenRegistrationApplication> getApplication(@PathVariable("appId") Long appId);
}
