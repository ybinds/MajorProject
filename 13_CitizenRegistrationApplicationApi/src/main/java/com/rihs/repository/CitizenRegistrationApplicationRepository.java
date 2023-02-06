package com.rihs.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.CitizenRegistrationApplication;

public interface CitizenRegistrationApplicationRepository extends JpaRepository<CitizenRegistrationApplication, Serializable> {

	Optional<CitizenRegistrationApplication> findByCitizenAppId(Long appId);

}
