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
@Data
@Table(name="caseworkers")
@NoArgsConstructor
@AllArgsConstructor
public class CaseWorker {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String fullName;
	private String email;
	private String password;
	private Long mobile;
	private String gender;
	@JsonFormat(pattern="MM/dd/yyyy")
	private LocalDate dob;
	private Long ssn;
	private boolean active;
}
