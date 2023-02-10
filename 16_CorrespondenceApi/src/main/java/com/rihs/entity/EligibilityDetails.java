package com.rihs.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="eligibility_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibilityDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
