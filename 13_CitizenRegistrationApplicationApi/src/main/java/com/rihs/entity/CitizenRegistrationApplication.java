package com.rihs.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="citizenregistrationapp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenRegistrationApplication {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long citizenAppId;
	private String citizenFullName;
	private String citizenEmail;
	private Long citizenMobileNum;
	private String citizenGender;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate citizenDob;
	private Long citizenSsn;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	private Integer createdBy;
	private Integer updatedBy;
	
}
