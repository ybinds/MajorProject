package com.rihs.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanRequest {
	
	private Integer id;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer cid;
}
