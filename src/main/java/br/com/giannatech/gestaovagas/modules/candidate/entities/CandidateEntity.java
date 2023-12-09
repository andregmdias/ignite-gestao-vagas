package br.com.giannatech.gestaovagas.modules.candidate.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "candidate")
@Table(name = "candidates")
public class CandidateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;

	@NotBlank
	@Email(message = "O campo email deve ser um email válido")
	@Column(unique = true)
	private String email;

	@Pattern(regexp = "\\S+", message = "O campo username não pode conter espaço")
	@Column(unique = true)
	private String username;

	@Length(min = 6, message = "O campo senha deve ter no mínimo 6 caracteres")
	@Length(max = 255, message = "O campo senha deve ter no máximo 255 caracteres")
	private String password;
	private String description;
	private String curriculum;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
