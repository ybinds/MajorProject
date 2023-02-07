package com.rihs.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Case;

public interface CaseRepository extends JpaRepository<Case, Serializable> {

}
