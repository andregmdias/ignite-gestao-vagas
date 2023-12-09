package br.com.giannatech.gestaovagas.modules.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "job")
@Table(name = "jobs")
public class JobEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotBlank(message = "O campo 'title' é obrigatório")
	private String description;

	@NotBlank(message = "O campo 'description' é obrigatório")
	private String level;

	@NotBlank(message = "O campo 'location' é obrigatório")
	private String benefits;

	@ManyToOne
	@JoinColumn(name = "company_id", insertable = false, updatable = false)
	private CompanyEntity company;

	@Column(name = "company_id", nullable = false)
	private UUID companyId;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
