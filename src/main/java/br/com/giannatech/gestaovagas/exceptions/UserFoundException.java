package br.com.giannatech.gestaovagas.exceptions;

public class UserFoundException extends RuntimeException {

	public UserFoundException() {
		super("Usuário já cadastrado");
	}
}
