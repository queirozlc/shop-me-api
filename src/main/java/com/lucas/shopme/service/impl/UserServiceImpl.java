package com.lucas.shopme.service.impl;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.exception.bad_request.BadRequestException;
import com.lucas.shopme.exception.not_found.NotFoundException;
import com.lucas.shopme.mapper.UserMapper;
import com.lucas.shopme.repository.UserRepository;
import com.lucas.shopme.request.UserRequestBody;
import com.lucas.shopme.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	private static final String USER_NOT_FOUND = "Usuário não encontrado.";
	private static final int USERS_PER_PAGE = 5;
	private final UserRepository repository;
	private final UserMapper mapper;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		List<User> users = repository.findAll();
		if (users.isEmpty()) {
			throw new NotFoundException("Não existe nenhum usuário cadastrado.");
		}
		return users;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<User> findAllPageable(int page) {
		Page<User> users = repository.findAll(PageRequest.of(page, USERS_PER_PAGE));
		if (users.isEmpty()) {
			throw new NotFoundException("Não existe nenhum usuário cadastrado.");
		}
		return users;
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(UUID id) {
		return repository.findById(id)
				.orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
	}

	@Override
	public User save(@Valid UserRequestBody userRequestBody) {
		User userToBeSaved = mapper.toUser(userRequestBody);

		if (repository.existsByEmail(userToBeSaved.getEmail())) {
			throw new BadRequestException("Já existe um usuário com esse email.");
		}
		userToBeSaved.setEnabled(true);
		return repository.save(userToBeSaved);
	}

	@Override
	public User update(@Valid UserRequestBody userRequestBody, UUID id) {
		User userToBeUpdated = repository.findById(id)
				.orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
		User userRequest = mapper.toUser(userRequestBody);

		if (!userToBeUpdated.getEmail().equals(userRequest.getEmail())
				&& repository.existsByEmail(userRequest.getEmail())) {
			throw new BadRequestException("Já existe um usuário com esse email.");
		}
		userRequest.setId(userToBeUpdated.getId());
		return repository.save(userRequest);
	}

	@Override
	public void delete(UUID id) {
		User userToDelete = repository.findById(id)
				.orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
		repository.delete(userToDelete);
	}
}
