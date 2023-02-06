package com.lucas.shopme.exception.handler;

import com.lucas.shopme.exception.bad_request.BadRequestException;
import com.lucas.shopme.exception.bad_request.BadRequestExceptionDetails;
import com.lucas.shopme.exception.not_found.NotFoundException;
import com.lucas.shopme.exception.not_found.NotFoundExceptionDetails;
import com.lucas.shopme.exception.validation.ValidationExceptionDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException ex) {
		BadRequestExceptionDetails badRequestExceptionDetails = BadRequestExceptionDetails
				.builder()
				.title("Bad Request. Check api documentation.")
				.status(HttpStatus.BAD_REQUEST.value())
				.detail(ex.getClass().getName())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<>(badRequestExceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<NotFoundExceptionDetails> handleNotFoundException(NotFoundException ex) {
		NotFoundExceptionDetails notFoundExceptionDetails = NotFoundExceptionDetails
				.builder()
				.title("Not Found. Check api documentation.")
				.status(HttpStatus.NOT_FOUND.value())
				.detail(ex.getClass().getName())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<>(notFoundExceptionDetails, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                              HttpHeaders headers, HttpStatusCode status,
	                                                              WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		String fieldName = fieldErrors
				.stream()
				.map(FieldError::getField)
				.collect(Collectors.joining(", "));
		String errorMessage = fieldErrors
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.joining(", "));


		ValidationExceptionDetails validationExceptionDetails = ValidationExceptionDetails
				.builder()
				.title("Invalid Field(s). Check api documentation.")
				.status(status.value())
				.detail(ex.getClass().getName())
				.timestamp(LocalDateTime.now())
				.message(ex.getMessage())
				.field(fieldName)
				.message(errorMessage)
				.build();

		return ResponseEntity
				.badRequest()
				.body(validationExceptionDetails);
	}
}
