package br.com.giannatech.gestaovagas.exceptions;

public class JobNotFoundException extends RuntimeException {

	public JobNotFoundException() {
		super("Job not found");
	}
}
