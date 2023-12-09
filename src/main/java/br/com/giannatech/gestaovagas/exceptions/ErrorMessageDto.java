package br.com.giannatech.gestaovagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDto {

	private String message;
	private String field;
}
