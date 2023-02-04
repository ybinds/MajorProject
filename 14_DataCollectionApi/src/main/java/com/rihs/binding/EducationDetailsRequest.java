package com.rihs.binding;

import lombok.Data;

@Data
public class EducationDetailsRequest {

	private Long caseNumber;
	private String highestDegree;
	private Integer graduationYear;
	private String universityName;
}
