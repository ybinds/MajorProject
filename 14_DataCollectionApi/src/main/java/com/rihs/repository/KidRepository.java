package com.rihs.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Kid;

public interface KidRepository extends JpaRepository<Kid, Serializable> {

}
