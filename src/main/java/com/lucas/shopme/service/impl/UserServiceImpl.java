package com.lucas.shopme.service.impl;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.exception.not_found.NotFoundException;
import com.lucas.shopme.mapper.UserMapper;
import com.lucas.shopme.repository.UserRepository;
import com.lucas.shopme.request.UserRequestBody;
import com.lucas.shopme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	private final UserRepository repository;
	private final UserMapper mapper;

	@Override
	public List<User> findAll() {
		List<User> users = repository.findAll();
		if (users.isEmpty()) {
			throw new NotFoundException("Não existe usuários cadastrados.");
		}
		return users;
	}

	@Override
	public Page<User> findAllPageable() {
		return null;
	}

	@Override
	public User findById(UUID id) {
		return null;
	}

	@Override
	public User save(UserRequestBody userRequestBody) {
		return null;
	}

	@Override
	public User update(UUID id, UserRequestBody userRequestBody) {
		return null;
	}

	@Override
	public void delete(UUID id) {

	}
}
