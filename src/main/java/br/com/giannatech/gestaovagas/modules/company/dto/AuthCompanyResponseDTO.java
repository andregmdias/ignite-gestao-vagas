package br.com.giannatech.gestaovagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCompanyResponseDTO {

	private String accessToken;
	private Long expiresIn;
}
