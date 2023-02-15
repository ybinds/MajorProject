package com.rihs.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.CaseWorker;

public interface CaseWorkerRepository extends JpaRepository<CaseWorker, Serializable>{

	Optional<CaseWorker> findByEmailAndPassword(String email, String password);

}
