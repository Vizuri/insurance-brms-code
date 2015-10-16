package com.vizuri.insurance.rest;

public class ErrorResponse{
	
	String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}   
	
	
}
