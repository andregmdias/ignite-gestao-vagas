package br.com.giannatech.gestaovagas.modules.candidate.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.giannatech.gestaovagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.giannatech.gestaovagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.giannatech.gestaovagas.modules.candidate.repositories.CandidateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateUseCase {

	@Value("${security.token.secret.candidate}")
	private String secretKey;

	@Autowired
	private CandidateRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO dto) throws AuthenticationException {

		var candidate = this.repository
				.findByUsername(dto.username())
				.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos"));

		var passwordMatches = this.passwordEncoder.matches(dto.password(), candidate.getPassword());

		if (!passwordMatches) {
			throw new AuthenticationException(null);
		}

		Algorithm algorithm = Algorithm.HMAC256(secretKey);

		var expiresIn = Instant.now().plus(Duration.ofHours(2));
		var token = JWT.create()
				.withIssuer("javagas")
				.withSubject(candidate.getId().toString())
				.withClaim("roles", List.of("candidate"))
				.withExpiresAt(expiresIn)
				.sign(algorithm);

		return AuthCandidateResponseDTO
				.builder()
				.accessToken(token)
				.expiresIn(expiresIn.toEpochMilli())
				.build();
	}
}
