package com.rihs.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="benefit_issuance")
@AllArgsConstructor
@NoArgsConstructor
public class BenefitIssuance {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String filename;
	private byte[] issuanceDetails;
	@CreationTimestamp
	private LocalDate createdDate;
	
}
