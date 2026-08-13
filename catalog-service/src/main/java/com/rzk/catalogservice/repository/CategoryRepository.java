package com.rzk.catalogservice.repository;

import com.rzk.catalogservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}