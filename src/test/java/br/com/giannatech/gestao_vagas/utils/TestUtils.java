package br.com.giannatech.gestao_vagas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static String objectToJson(Object object) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String createCompanyJWT(UUID companyId, String secret) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        var expiresIn = Instant.now().plus(Duration.ofHours(6));

        return JWT.create()
                .withIssuer("javagas")
                .withSubject(companyId.toString())
                .withClaim("roles", List.of("company"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);
    }
}
