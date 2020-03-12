package com.eventosapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventosapp.models.Eventos;

public interface EventosRepository extends JpaRepository<Eventos, Long>{
	Eventos findById(long id);
}
