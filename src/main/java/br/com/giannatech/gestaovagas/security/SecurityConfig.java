package br.com.giannatech.gestaovagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private SecurityCompanyFilter securityCompanyFilter;

	@Autowired
	private SecurityCandidateFilter candidateFilter;

	private static final String[] PERMITTED_URLS = {
			"/actuator/**",
			"/swagger-ui/**",
			"/v3/api-docs/**",
			"/swagger-resource/**"
	};

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> {
					auth
							.requestMatchers("/candidates").permitAll()
							.requestMatchers("/companies").permitAll()
							.requestMatchers("/candidates/auth").permitAll()
							.requestMatchers("/companies/auth").permitAll()
							.requestMatchers(PERMITTED_URLS).permitAll();
					auth.anyRequest().authenticated();
				})
				.addFilterBefore(candidateFilter, BasicAuthenticationFilter.class)
				.addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
