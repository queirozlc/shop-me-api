package com.lucas.shopme.service;

import com.lucas.shopme.entity.Category;
import com.lucas.shopme.request.category.CategoryRequestBody;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

	Category createCategory(CategoryRequestBody categoryRequestBody);

	Category updateCategory(CategoryRequestBody categoryRequestBody, UUID id);

	List<Category> listAllCategories();

	void deleteCategory(UUID id);
}
