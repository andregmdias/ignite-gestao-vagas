package br.com.giannatech.gestaovagas.modules.candidate.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.giannatech.gestaovagas.exceptions.UserFoundException;
import br.com.giannatech.gestaovagas.modules.candidate.entities.CandidateEntity;
import br.com.giannatech.gestaovagas.modules.candidate.repositories.CandidateRepository;
import br.com.giannatech.gestaovagas.modules.candidate.usecases.CreateCandidateUseCase;

@ExtendWith(MockitoExtension.class)
class CreateCandidateUseCaseTest {

  @InjectMocks
  private CreateCandidateUseCase createCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Should not be able to create candidate with username or email already exists")
  void should_not_be_able_to_create_candidate_with_username_or_email_already_exists() {
    var candidateEntity = CandidateEntity.builder()
        .username("username")
        .email("email")
        .password("password")
        .build();

    when(candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()))
        .thenReturn(Optional.of(candidateEntity));

    try {
      createCandidateUseCase.execute(candidateEntity);
    } catch (Exception e) {
      assertInstanceOf(UserFoundException.class, e);
      assertEquals("Usuário já cadastrado", e.getMessage());
    }
  }

  @Test
  @DisplayName("Should be able to create candidate")
  void should_be_able_to_create_candidate() {
    var candidateEntity = CandidateEntity.builder()
        .username("username")
        .email("email")
        .password("password")
        .build();

    when(candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()))
        .thenReturn(Optional.empty());

    when(passwordEncoder.encode(candidateEntity.getPassword())).thenReturn("encodedPassword");

    when(candidateRepository.save(candidateEntity)).thenReturn(candidateEntity);

    var createdCandidate = createCandidateUseCase.execute(candidateEntity);

    assertEquals(candidateEntity.getUsername(), createdCandidate.getUsername());
    assertEquals(candidateEntity.getEmail(), createdCandidate.getEmail());
    assertEquals("encodedPassword", createdCandidate.getPassword());
  }

}
