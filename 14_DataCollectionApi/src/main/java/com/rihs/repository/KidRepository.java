package com.rihs.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Case;
import com.rihs.entity.Kid;

public interface KidRepository extends JpaRepository<Kid, Serializable> {

	List<Kid> findByCob(Case c);
}
