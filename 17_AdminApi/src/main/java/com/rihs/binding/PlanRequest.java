package com.rihs.binding;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PlanRequest {
	
	private Integer id;
	private String name;
	@JsonFormat(pattern="MM/dd/yyyy")
	private LocalDate startDate;
	@JsonFormat(pattern="MM/dd/yyyy")
	private LocalDate endDate;
	private Integer cid;
}
