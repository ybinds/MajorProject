package com.rihs.entity;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="triggers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Triggers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long triggerId;
	private Long caseNum;
	private Blob triggerPdf;
	private String triggerStatus;
}
