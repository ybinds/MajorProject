package com.rihs.binding;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CitizenRegistrationApplicationRequest {
	
	private String citizenFullName;
	private String citizenEmail;
	private Long citizenMobileNum;
	private String citizenGender;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate citizenDob;
	private Long citizenSsn;
}
