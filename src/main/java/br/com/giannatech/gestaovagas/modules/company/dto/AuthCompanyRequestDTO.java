package br.com.giannatech.gestaovagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCompanyRequestDTO {

	private String username;
	private String password;
}
