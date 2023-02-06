package com.lucas.shopme.service.impl;

import com.lucas.shopme.entity.Category;
import com.lucas.shopme.exception.bad_request.BadRequestException;
import com.lucas.shopme.exception.not_found.NotFoundException;
import com.lucas.shopme.mapper.category.CategoryMapper;
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
	private final CategoryMapper mapper;

	@Override
	public Category createCategory(CategoryRequestBody categoryRequestBody) {
		Category categoryToBeSaved = mapper.toCategory(categoryRequestBody);

		if (categoryToBeSaved.getParent() != null && categoryToBeSaved.getParent().getId() != null) {
			Category parent = categoryRepository.findById(categoryToBeSaved.getParent().getId())
					.orElseThrow(() -> new BadRequestException(CATEGORY_NOT_FOUND));
			categoryToBeSaved.setParent(parent);
		}

		if (categoryRepository.existsByAliasOrNameAllIgnoreCase(categoryToBeSaved.getAlias(), categoryToBeSaved.getName())) {
			throw new BadRequestException("Categoria já cadastrada.");
		}
		categoryToBeSaved.setEnabled(true);
		return categoryRepository.save(categoryToBeSaved);
	}

	@Override
	public Category updateCategory(CategoryRequestBody categoryRequestBody, UUID id) {
		Category categoryRequest = mapper.toCategory(categoryRequestBody);
		Category categoryToBeUpdated = categoryRepository
				.findById(id)
				.orElseThrow(() -> new BadRequestException(CATEGORY_NOT_FOUND));

		if (categoryRequest.getParent() != null && categoryRequest.getParent().getId() != null) {
			Category parent = categoryRepository.findById(categoryRequest.getParent().getId())
					.orElseThrow(() -> new BadRequestException(CATEGORY_NOT_FOUND));
			categoryRequest.setParent(parent);
		}

		if (!categoryRequest.getName().equalsIgnoreCase(categoryToBeUpdated.getName()) ||
				!categoryRequest.getAlias().equalsIgnoreCase(categoryToBeUpdated.getAlias()) &&
						categoryRepository.existsByAliasOrNameAllIgnoreCase(categoryRequest.getAlias(), categoryRequest.getName())) {
			throw new BadRequestException("Categoria já cadastrada com esse alias ou nome.");
		}

		categoryRequest.setId(categoryToBeUpdated.getId());
		return categoryRepository.save(categoryRequest);
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
