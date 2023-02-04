package com.rihs.binding;

import java.util.List;

import lombok.Data;

@Data
public class KidsDetailsRequest {

	private Long caseNumber;
	private List<KidRequest> kids; 
}
