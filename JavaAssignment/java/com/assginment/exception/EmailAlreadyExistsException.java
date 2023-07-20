package com.assginment.exception;

@SuppressWarnings("serial")
public class EmailAlreadyExistsException  extends Exception{
	public EmailAlreadyExistsException(String message) {
		super(message);
	}

}
