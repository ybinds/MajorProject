package com.rihs.binding;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EligibilityDetailsResponse {
	private Long traceId;
	private Long caseNum;
	private String holderName;
	private Long holderSsn;
	private String planName;
	private String planStatus;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate planStartDate;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate planEndDate;
	private Double benefitAmount;
	private String denialReason;
}
