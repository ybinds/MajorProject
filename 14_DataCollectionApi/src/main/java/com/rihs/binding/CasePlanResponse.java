package com.rihs.binding;

import java.util.List;

import com.rihs.entity.Plan;

import lombok.Data;

@Data
public class CasePlanResponse {

	private Long caseNumber;
	private List<Plan> plan;
}
