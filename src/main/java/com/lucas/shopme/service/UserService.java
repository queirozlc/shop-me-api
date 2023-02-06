package com.lucas.shopme.service;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.request.UserRequestBody;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface UserService {

	List<User> findAll();

	Page<User> findAllPageable(int page);

	User findById(UUID id);

	User save(UserRequestBody userRequestBody);

	User update(UserRequestBody userRequestBody, UUID id);

	void delete(UUID id);
}
