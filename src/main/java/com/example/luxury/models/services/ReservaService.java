package com.example.luxury.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.example.luxury.models.entities.Reserva;

public interface ReservaService {
	
	public List<Reserva> findAll();
	public Page<Reserva> findAll(Pageable pageable);
	public Reserva findOne(Long id);
	public void save(Reserva reserva);
	public void remove(Long id);
	public Long count();
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

