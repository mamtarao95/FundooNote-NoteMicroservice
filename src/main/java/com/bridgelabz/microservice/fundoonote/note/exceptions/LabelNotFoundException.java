package com.bridgelabz.microservice.fundoonote.note.exceptions;

public class LabelNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public LabelNotFoundException(String message) {
		super(message);
	}
}
