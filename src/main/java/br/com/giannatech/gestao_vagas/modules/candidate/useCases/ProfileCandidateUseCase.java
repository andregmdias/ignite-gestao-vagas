package br.com.giannatech.gestao_vagas.modules.candidate.useCases;

import br.com.giannatech.gestao_vagas.modules.candidate.dto.ProfileCandidateReponseDTO;
import br.com.giannatech.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository repository;

  public ProfileCandidateReponseDTO execute(UUID id) {
    var candidate =  this.repository
        .findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not fond"));

    var profileCandidate = ProfileCandidateReponseDTO
        .builder()
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .id(candidate.getId())
        .name(candidate.getName())
        .build();

    return profileCandidate;
  }
}
