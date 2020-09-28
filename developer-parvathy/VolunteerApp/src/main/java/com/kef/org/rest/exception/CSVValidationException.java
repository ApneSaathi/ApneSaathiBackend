package com.kef.org.rest.exception;

public class CSVValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CSVValidationException(String message) {
        super(message);
    }

    public CSVValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
