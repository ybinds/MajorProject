package com.rihs.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="cases")
@AllArgsConstructor
@NoArgsConstructor
public class Case {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long caseNumber;
	
	private Long appId;
	
	@ManyToOne
	@JoinColumn(name="pidFk")
	private Plan plan;
	
	@ManyToOne
	@JoinColumn(name="eidFk",unique = true)
	private Education educationDetails;
	
	@ManyToOne
	@JoinColumn(name="iidFk",unique = true)
	private Income incomeDetails;
	
	@OneToMany
	@JoinColumn(name="kidFk")
	private List<Kid> kids;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	private String createdBy;
	private String updatedBy;
	private String status;
}
