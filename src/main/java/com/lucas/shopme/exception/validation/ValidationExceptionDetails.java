package com.lucas.shopme.exception.validation;

import com.lucas.shopme.exception.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ValidationExceptionDetails extends ExceptionDetails {
	private String field;
}
