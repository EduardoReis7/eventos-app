package com.eventosapp.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
@Data
@Entity
public class Convidado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idConvidado;
	
	@NotEmpty
	private String nomeConvidado;
	
	@NotEmpty
	private String cpf;
	
	@JoinColumn(name = "eventos", referencedColumnName = "id", updatable = false, nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Eventos eventos;
	
}
