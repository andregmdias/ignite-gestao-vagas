package br.com.giannatech.gestaovagas.modules.candidate.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.giannatech.gestaovagas.modules.candidate.entities.CandidateEntity;
import br.com.giannatech.gestaovagas.modules.candidate.repositories.CandidateRepository;
import br.com.giannatech.gestaovagas.modules.candidate.usecases.ProfileCandidateUseCase;

@ExtendWith(MockitoExtension.class)
class ProfileCandidateUseCaseTest {

  @InjectMocks
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Mock
  private CandidateRepository repository;

  @Test
  @DisplayName("Should be able to fetch candidate by id")
  void should_be_able_to_fetch_candidate_by_id() {
    var candidateEntity = CandidateEntity.builder()
        .username("username")
        .email("email")
        .password("password")
        .build();

    when(repository.findById(candidateEntity.getId())).thenReturn(Optional.of(candidateEntity));

    var fetchedCandidate = profileCandidateUseCase.execute(candidateEntity.getId());
    assertEquals(candidateEntity.getId(), fetchedCandidate.getId());
    assertEquals(candidateEntity.getUsername(), fetchedCandidate.getUsername());
    assertEquals(candidateEntity.getEmail(), fetchedCandidate.getEmail());
    assertEquals(candidateEntity.getName(), fetchedCandidate.getName());
    assertEquals(candidateEntity.getDescription(), fetchedCandidate.getDescription());
  }

  @Test
  @DisplayName("Should not be able to fetch candidate by id")
  void should_not_be_able_to_fetch_candidate_by_id() {
    var candidateEntity = CandidateEntity.builder()
        .username("username")
        .email("email")
        .password("password")
        .build();

    when(repository.findById(candidateEntity.getId())).thenReturn(Optional.empty());

    try {
      profileCandidateUseCase.execute(candidateEntity.getId());
    } catch (Exception e) {
      assertEquals("User not found", e.getMessage());
    }
  }

}
