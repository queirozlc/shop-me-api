package com.lucas.shopme.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public abstract class ExceptionDetails {
	private String title;
	private int status;
	private String detail;
	private String message;
	private LocalDateTime timestamp;
}
