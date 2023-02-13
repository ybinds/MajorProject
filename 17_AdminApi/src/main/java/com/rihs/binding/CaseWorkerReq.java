package com.rihs.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CaseWorkerReq {
	
	private Integer id;
	private String fullName;
	private String email;
	private Long mobile;
	private String gender;
	private LocalDate dob;
	private Long ssn;
}
