package br.com.giannatech.gestaovagas.modules.candidate.dto;

import java.util.UUID;

public record CreateApplyJobRequestDTO(
		UUID candidateId,
		UUID jobId) {
}
