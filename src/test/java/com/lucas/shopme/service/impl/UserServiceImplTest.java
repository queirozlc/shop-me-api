package com.lucas.shopme.service.impl;

import com.lucas.shopme.entity.User;
import com.lucas.shopme.exception.bad_request.BadRequestException;
import com.lucas.shopme.exception.not_found.NotFoundException;
import com.lucas.shopme.mapper.user.UserMapper;
import com.lucas.shopme.repository.UserRepository;
import com.lucas.shopme.request.user.UserRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {
	public static final String NAME = "user";
	public static final String EMAIL = "user@email.com";
	public static final String PASSWORD = "user";
	public static final boolean ENABLED = true;
	public static final UUID ID = UUID.fromString("e6e3a0e4-c6ce-4a0b-8b33-89c673865937");
	private static final String USER_NOT_FOUND = "Usuário não encontrado.";
	public static final String NO_USERS_FOUND = "Não existe nenhum usuário cadastrado.";
	public static final String USER_ALREADY_EXISTS = "Já existe um usuário cadastrado com esse email.";
	public static User user;
	public static UserRequestBody userRequestBody;
	public static Optional<User> userOptional;
	public static List<User> users;
	public static PageImpl<User> usersPage;

	@InjectMocks
	private UserServiceImpl userService;
	@Mock
	private UserRepository repository;
	@Mock
	private UserMapper mapper;

	@BeforeEach
	void setUp() {
		initClasses();
	}

	@Test
	@DisplayName("Find All users when successful")
	void findAllUsersWhenSuccessful() {
		when(repository.findAll()).thenReturn(users);

		List<User> response = userService.findAll();

		assertEquals(User.class, response.get(0).getClass());
		assertEquals(NAME, response.get(0).getName());
		assertEquals(EMAIL, response.get(0).getEmail());
	}

	@Test
	@DisplayName("Find all throws not found exception when no user found.")
	void findAllThrowsNotFoundExceptionWhenNoUserFound() {
		when(repository.findAll()).thenReturn(new ArrayList<>());
		try {
			userService.findAll();
		}
		catch (Exception e) {
			assertEquals(NO_USERS_FOUND, e.getMessage());
			assertEquals(NotFoundException.class, e.getClass());
		}
	}

	@Test
	@DisplayName("Find All users pageable when successful")
	void findAllUsersPageableWhenSuccessful() {
		when(repository.findAll(any(Pageable.class))).thenReturn(usersPage);
		Page<User> response = userService.findAllPageable(anyInt());
		assertEquals(User.class, response.getContent().get(0).getClass());
		assertEquals(NAME, response.getContent().get(0).getName());
		assertEquals(ID, response.getContent().get(0).getId());
	}

	@Test
	@DisplayName("Find All users pageable throws not found exception when no user found.")
	void FindAllUsersPageableThrowsNotFoundExceptionWhenNoUserFound() {
		when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());

		try {
			userService.findAllPageable(anyInt());
		}
		catch (Exception e) {
			assertEquals(NO_USERS_FOUND, e.getMessage());
			assertEquals(NotFoundException.class, e.getClass());
		}
	}

	@Test
	@DisplayName("Find user by id when successful")
	void findByIdWhenSuccessful() {
		when(repository.findById(ID)).thenReturn(userOptional);

		User response = userService.findById(ID);

		assertEquals(User.class, response.getClass());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(ID, response.getId());
	}

	@Test
	@DisplayName("Find user by id throws bad request exception when no user found.")
	void findByIdThrowsBadRequestExceptionWhenNoUserFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		try {
			userService.findById(ID);
		}
		catch (Exception e) {
			assertEquals(USER_NOT_FOUND, e.getMessage());
			assertEquals(BadRequestException.class, e.getClass());
		}
	}

	@Test
	@DisplayName("Save user when successful")
	void saveUserWhenSuccessful() {
		when(mapper.toUser(any(UserRequestBody.class))).thenReturn(user);
		when(repository.save(any(User.class))).thenReturn(user);
		when(repository.existsByEmail(EMAIL)).thenReturn(false);

		User response = userService.save(userRequestBody);

		assertEquals(User.class, response.getClass());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(ID, response.getId());
		assertEquals(ENABLED, response.isEnabled());
	}

	@Test
	@DisplayName("Save user throws bad request exception when user already exists.")
	void saveUserThrowsBadRequestExceptionWhenUserAlreadyExists() {
		when(mapper.toUser(any(UserRequestBody.class))).thenReturn(user);
		when(repository.save(any(User.class))).thenReturn(user);
		when(repository.existsByEmail(anyString())).thenReturn(true);

		try {
			userService.save(userRequestBody);
		}
		catch (Exception e) {
			assertEquals(USER_ALREADY_EXISTS, e.getMessage());
			assertEquals(BadRequestException.class, e.getClass());
		}
	}

	@Test
	@DisplayName("Update user when successful")
	void updateUserWhenSuccessful() {
		userOptional.get().setName("user to update");
		user.setName("user updated");
		when(mapper.toUser(any(UserRequestBody.class))).thenReturn(user);
		when(repository.findById(ID)).thenReturn(userOptional);
		when(repository.save(any(User.class))).thenReturn(user);
		when(repository.existsByEmail(anyString())).thenReturn(false);

		User response = userService.update(userRequestBody, ID);

		assertEquals(User.class, response.getClass());
		assertNotEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(ID, response.getId());
		assertEquals(ENABLED, response.isEnabled());
	}

	@Test
	@DisplayName("Update user throws bad request exception when user not found.")
	void updateUserThrowsBadRequestExceptionWhenUserNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		try {
			userService.update(userRequestBody, ID);
		}
		catch (Exception e) {
			assertEquals(USER_NOT_FOUND, e.getMessage());
			assertEquals(BadRequestException.class, e.getClass());
		}
	}

	@Test
	@DisplayName("Update user throw bad request exception when email already exists.")
	void updateUserThrowsBadRequestExceptionWhenEmailAlreadyExists() {
		when(repository.findById(ID)).thenReturn(userOptional);
		when(mapper.toUser(any(UserRequestBody.class))).thenReturn(user);
		when(repository.existsByEmail(anyString())).thenReturn(true);

		try {
			userService.update(userRequestBody, ID);
		}
		catch (Exception e) {
			assertEquals(USER_ALREADY_EXISTS, e.getMessage());
			assertEquals(BadRequestException.class, e.getClass());
		}
	}

	@Test
	@DisplayName("Delete user when successful")
	void deleteUserWhenSuccessful() {
		when(repository.findById(ID)).thenReturn(userOptional);
		userService.delete(ID);
		verify(repository, times(1)).delete(userOptional.get());
	}

	@Test
	@DisplayName("Delete user throws bad request exception when no user found.")
	void deleteUserThrowsBadRequestExceptionWhenNoUserFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		try {
			userService.delete(ID);
		}
		catch (Exception e) {
			assertEquals(USER_NOT_FOUND, e.getMessage());
			assertEquals(BadRequestException.class, e.getClass());
		}
	}

	private void initClasses() {
		user = new User(ID, NAME, EMAIL, PASSWORD, ENABLED);
		userRequestBody = new UserRequestBody(NAME, EMAIL, PASSWORD, ENABLED);
		userOptional = Optional.of(user);
		users = List.of(user);
		usersPage = new PageImpl<>(users);
	}
}