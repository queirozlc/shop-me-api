package com.lucas.shopme.controller;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService service;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}
}
