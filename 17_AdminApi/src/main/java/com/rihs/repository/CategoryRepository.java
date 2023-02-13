package com.rihs.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rihs.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Serializable> {

}
