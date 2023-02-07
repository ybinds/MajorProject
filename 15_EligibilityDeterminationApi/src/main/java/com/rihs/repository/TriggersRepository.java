package com.rihs.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Triggers;

public interface TriggersRepository extends JpaRepository<Triggers, Serializable> {

}
