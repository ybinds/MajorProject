package com.rihs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="kids")
@NoArgsConstructor
@AllArgsConstructor
public class Kid {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer kidId;
	private String kidName;
	private Integer kidAge;
	private Integer kidSsn;
}
