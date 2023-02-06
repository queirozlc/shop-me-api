package com.lucas.shopme.exception.bad_request;

import com.lucas.shopme.exception.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BadRequestExceptionDetails extends ExceptionDetails {
}
