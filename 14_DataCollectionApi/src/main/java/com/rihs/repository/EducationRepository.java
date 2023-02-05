package com.rihs.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Case;
import com.rihs.entity.Education;

public interface EducationRepository extends JpaRepository<Education, Serializable> {

	Education findByCob(Case c);
}
