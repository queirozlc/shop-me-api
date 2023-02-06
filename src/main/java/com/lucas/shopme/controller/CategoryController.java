package com.lucas.shopme.controller;

import com.lucas.shopme.entity.Category;
import com.lucas.shopme.request.category.CategoryRequestBody;
import com.lucas.shopme.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<Category>> findAllCategories() {
		return ResponseEntity.ok(categoryService.listAllCategories());
	}

	@PostMapping
	public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryRequestBody categoryRequestBody) {
		return new ResponseEntity<>(categoryService.createCategory(categoryRequestBody), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.ok("Categoria removida com sucesso.");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @RequestBody @Valid CategoryRequestBody categoryRequestBody) {
		return new ResponseEntity<>(categoryService.updateCategory(categoryRequestBody, id), HttpStatus.OK);
	}
}
