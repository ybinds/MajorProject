package com.rihs.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rihs.entity.Case;

@FeignClient("DATA-COLLECTION-API")
public interface CasePlanApplicationConsumer {

	@GetMapping("/v1/api/case/get/{caseNumber}")
	public ResponseEntity<Case> getCaseInfo(@PathVariable("caseNumber") Long caseNumber);
}
