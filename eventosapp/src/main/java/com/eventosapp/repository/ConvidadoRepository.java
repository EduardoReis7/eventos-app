package com.eventosapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventosapp.models.Convidado;
import com.eventosapp.models.Eventos;

public interface ConvidadoRepository extends JpaRepository<Convidado, Long> {
	Iterable<Convidado> findByEventos(Eventos evento);
	Convidado findByIdConvidado(Long idConvidado);
}
