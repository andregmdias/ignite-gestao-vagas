package br.com.giannatech.gestao_vagas.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super("User not found");
	}
}
