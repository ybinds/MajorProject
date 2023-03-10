package com.rihs.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Serializable> {

}
