package com.rihs.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Serializable> {

	List<Plan> findByActive(boolean active);
}
