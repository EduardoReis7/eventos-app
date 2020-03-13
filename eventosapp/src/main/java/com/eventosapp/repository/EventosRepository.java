package com.eventosapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventosapp.models.Eventos;

public interface EventosRepository extends JpaRepository<Eventos, String>{
	Eventos findById(Long id);
}
