package com.rihs.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rihs.entity.Plan;
import com.rihs.repository.PlanRepository;

@Component
public class AddPlansRunner implements CommandLineRunner{

	@Autowired
	private PlanRepository repo;

	public void run(String... args) throws Exception {

		repo.saveAll(
				Arrays.asList(
						new Plan(1, "SNAP (Supplemental Nutrition Assistance Program)"),
						new Plan(2, "CCAP (Child Care Assistance Program)"),
						new Plan(3, "Medicaid"),
						new Plan(4, "Medicare"),
						new Plan(5, "QHP (Qualified Health Plan)"),
						new Plan(6, "RIW (Rhode Island Works)")));
	}

}
