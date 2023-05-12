package com.sahu.um.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED)
public class DuplicateUserFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DuplicateUserFoundException(String errorMessage) {
		super(errorMessage);
	}
	
}
