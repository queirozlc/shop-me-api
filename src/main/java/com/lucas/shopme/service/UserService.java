package com.lucas.shopme.service;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.request.UserRequestBody;

import java.util.List;
import java.util.UUID;

public interface UserService {

	List<User> findAll();

	User findById(UUID id);

	User save(UserRequestBody userRequestBody);
}
