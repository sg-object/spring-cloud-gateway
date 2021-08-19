package com.sg.scg.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends AbstractException {

	private static final long serialVersionUID = 1L;

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.UNAUTHORIZED;
	}

	@Override
	public String getMessage() {
		return "this is test";
	}
}
