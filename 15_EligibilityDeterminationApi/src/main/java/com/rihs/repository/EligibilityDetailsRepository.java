package com.rihs.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.EligibilityDetails;

public interface EligibilityDetailsRepository extends JpaRepository<EligibilityDetails, Serializable> {

	Optional<EligibilityDetails> findByCaseNum(Long caseNum);
	List<EligibilityDetails> findByPlanStatusAndPlanEndDateAfter(String status, LocalDate endDate);
}
