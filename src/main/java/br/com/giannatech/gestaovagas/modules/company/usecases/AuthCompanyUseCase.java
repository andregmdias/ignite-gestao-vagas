package br.com.giannatech.gestaovagas.modules.company.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.giannatech.gestaovagas.modules.company.dto.AuthCompanyRequestDTO;
import br.com.giannatech.gestaovagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.giannatech.gestaovagas.modules.company.repositories.CompanyRepository;

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
public class AuthCompanyUseCase {

	@Value("${security.token.secret}")
	private String secretKey;

	@Autowired
	private CompanyRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AuthCompanyResponseDTO execute(AuthCompanyRequestDTO dto) throws AuthenticationException {
		var company = this.repository
				.findByUsername(dto.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos"));

		var passwordMatches = this.passwordEncoder.matches(dto.getPassword(), company.getPassword());

		if (!passwordMatches) {
			throw new AuthenticationException(null);
		}

		Algorithm algorithm = Algorithm.HMAC256(secretKey);

		var expiresIn = Instant.now().plus(Duration.ofHours(6));

		var token = JWT.create()
				.withIssuer("javagas")
				.withSubject(company.getId().toString())
				.withClaim("roles", List.of("company"))
				.withExpiresAt(expiresIn)
				.sign(algorithm);

		return AuthCompanyResponseDTO
				.builder()
				.accessToken(token)
				.expiresIn(expiresIn.toEpochMilli())
				.build();
	}
}
