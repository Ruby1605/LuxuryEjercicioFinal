package com.example.luxury.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.example.luxury.models.dao.ServicioDAO;
import com.example.luxury.models.entities.Servicio;


@Service
public class ServicioServiceImpl implements ServicioService {

	private final ServicioDAO servicioDAO;
	
	
	
	public ServicioServiceImpl(
			
			ServicioDAO servicioDAO
			) {
		
		this.servicioDAO = servicioDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Servicio> findAll() {
		return (List<Servicio>) servicioDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Servicio> findAll(Pageable pageable) {
		return servicioDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Servicio findOne(Long id) {
		return servicioDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Servicio servicio) {
		servicioDAO.save(servicio);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		servicioDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return servicioDAO.count();
	}
	
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

