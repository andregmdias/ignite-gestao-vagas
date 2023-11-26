package br.com.giannatech.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobRequestDTO {

	@Schema(description = "Job description", example = "Desenvolvedor Java", requiredMode = RequiredMode.REQUIRED)
	private String description;

	@Schema(description = "Job level", example = "Júnior", requiredMode = RequiredMode.REQUIRED)
	private String level;

	@Schema(description = "Job benefits", example = "VR, VT, Plano de saúde", requiredMode = RequiredMode.REQUIRED)
	private String benefits;
}
