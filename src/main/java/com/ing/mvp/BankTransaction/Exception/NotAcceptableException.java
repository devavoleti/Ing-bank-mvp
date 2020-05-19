package com.ing.mvp.BankTransaction.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotAcceptableException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotAcceptableException(String message) {
		super(message);
	}
}
