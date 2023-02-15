package com.rihs.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="plans")
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate startDate;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate endDate;
	@ManyToOne
	@JoinColumn(name = "cid_fk")
	private Category category;
	private boolean active;
}
