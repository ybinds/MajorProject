package com.rihs.binding;

import lombok.Data;

@Data
public class IncomeDetailsRequest {

	private Long caseNumber;
	private Double salaryIncome;
	private Double rentIncome;
	private Double propertyIncome;
}
