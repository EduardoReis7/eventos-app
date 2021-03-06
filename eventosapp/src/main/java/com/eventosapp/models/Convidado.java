package com.eventosapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "convidado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Convidado{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_convidado", updatable = false, nullable = false)
	private Long idConvidado;
	
	@NotBlank
	private String nomeConvidado;
	
	@NotBlank
	private String cpf;
	
	@JoinColumn(name = "eventos", referencedColumnName = "id", updatable = false, nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Eventos eventos;
	
}
