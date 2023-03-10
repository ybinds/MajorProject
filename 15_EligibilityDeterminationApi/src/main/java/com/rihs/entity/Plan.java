package com.rihs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Integer id;
	private String name;
}
