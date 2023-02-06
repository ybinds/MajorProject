package com.rihs.binding;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CitizenRegistrationApplicationRequest {
	
	private String fullName;
	private String email;
	private Long mobileNum;
	private String gender;
	@JsonFormat(pattern = "mm/dd/yyyy")
	private LocalDate dob;
	private Long ssn;
}
