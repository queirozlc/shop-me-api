package com.lucas.shopme.service.impl;

import com.lucas.shopme.entity.Category;
import com.lucas.shopme.exception.bad_request.BadRequestException;
import com.lucas.shopme.exception.not_found.NotFoundException;
import com.lucas.shopme.repository.CategoryRepository;
import com.lucas.shopme.request.category.CategoryRequestBody;
import com.lucas.shopme.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	public static final String CATEGORY_NOT_FOUND = "Categoria não encontrada.";
	private final CategoryRepository categoryRepository;

	@Override
	public Category createCategory(CategoryRequestBody categoryRequestBody) {
		return null;
	}

	@Override
	public Category updateCategory(CategoryRequestBody categoryRequestBody, UUID id) {
		return null;
	}

	@Override
	public List<Category> listAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		if (categories.isEmpty()) {
			throw new NotFoundException("Não existe nenhuma categoria cadastrada.");
		}
		return categories;
	}

	@Override
	public void deleteCategory(UUID id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new BadRequestException(CATEGORY_NOT_FOUND));
		categoryRepository.delete(category);
	}
}
