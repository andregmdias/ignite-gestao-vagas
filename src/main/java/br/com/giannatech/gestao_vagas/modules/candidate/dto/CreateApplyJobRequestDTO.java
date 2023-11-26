package br.com.giannatech.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

public record CreateApplyJobRequestDTO(
		UUID candidateId,
		UUID jobId
) {
}
