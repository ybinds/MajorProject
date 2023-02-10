package com.rihs.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Triggers;

public interface TriggersRepository extends JpaRepository<Triggers, Serializable> {

	List<Triggers> findByTriggerStatus(String status);
}
