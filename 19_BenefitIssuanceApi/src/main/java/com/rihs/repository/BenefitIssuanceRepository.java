package com.rihs.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.BenefitIssuance;

public interface BenefitIssuanceRepository extends JpaRepository<BenefitIssuance, Serializable> {

}
