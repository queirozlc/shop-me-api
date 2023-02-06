package com.lucas.shopme.controller;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.request.UserRequestBody;
import com.lucas.shopme.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService service;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/page/{page}")
	public ResponseEntity<Page<User>> findAllPageable(@PathVariable int page) {
		return ResponseEntity.ok(service.findAllPageable(page));
	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody @Valid UserRequestBody userRequestBody) {
		return new ResponseEntity<>(service.save(userRequestBody), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable UUID id,
	                                   @RequestBody @Valid UserRequestBody userRequestBody) {
		return ResponseEntity.ok(service.update(userRequestBody, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.ok("Usuário deletado com sucesso.");
	}
}
