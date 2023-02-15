package com.rihs.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.rihs.entity.Category;
import com.rihs.repository.CategoryRepository;

@Component
public class CategoryRunner implements CommandLineRunner {

	@Autowired
	private CategoryRepository repo;
	
	public void run(String... args) throws Exception {

		repo.saveAll(
					Arrays.asList(
							new Category(1, "Food"),
							new Category(2, "Health"),
							new Category(3, "Un-Employment"),
							new Category(4, "Child Care")));
	}

}
