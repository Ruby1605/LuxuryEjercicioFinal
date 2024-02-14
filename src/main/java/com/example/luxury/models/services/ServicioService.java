package com.example.luxury.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.example.luxury.models.entities.Servicio;

public interface ServicioService {
	
	public List<Servicio> findAll();
	public Page<Servicio> findAll(Pageable pageable);
	public Servicio findOne(Long id);
	public void save(Servicio servicio);
	public void remove(Long id);
	public Long count();
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

