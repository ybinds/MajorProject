package com.rihs.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Serializable> {

}
