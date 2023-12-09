package br.com.giannatech.gestaovagas.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("User not found");
	}
}
