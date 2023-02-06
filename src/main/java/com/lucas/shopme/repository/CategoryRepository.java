package com.lucas.shopme.repository;

import com.lucas.shopme.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
	boolean existsByAliasOrNameAllIgnoreCase(String alias, String name);
}