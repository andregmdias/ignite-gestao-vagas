package br.com.giannatech.gestao_vagas.modules.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "company")
@Table(name = "companies")
public class CompanyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String name;

	@Column(unique = true)
	@Length()
	private String cnpj;

	private String username;
	@NotBlank
	@Email(message = "O campo email deve ser um email válido")
	@Column(unique = true)
	private String email;

	@Length(min = 6, message = "O campo senha deve ter no mínimo 6 caracteres")
	@Length(max = 100, message = "O campo senha deve ter no máximo 12 caracteres")
	private String password;
	private String website;
	private String description;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
