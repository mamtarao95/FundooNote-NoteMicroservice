package com.bridgelabz.microservice.fundoonote.note.exceptions;

public class UnAuthorizedException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnAuthorizedException(String message) {
		super(message);
	}

}
