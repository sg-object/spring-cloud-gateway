package com.sg.scg.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public abstract HttpStatus getStatus();

	public abstract String getMessage();
}
