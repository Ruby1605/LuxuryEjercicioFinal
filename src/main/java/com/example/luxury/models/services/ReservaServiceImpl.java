package com.example.luxury.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.example.luxury.models.dao.ReservaDAO;
import com.example.luxury.models.entities.Reserva;


@Service
public class ReservaServiceImpl implements ReservaService {

	private final ReservaDAO reservaDAO;
	
	
	
	public ReservaServiceImpl(
			
			ReservaDAO reservaDAO
			) {
		
		this.reservaDAO = reservaDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Reserva> findAll() {
		return (List<Reserva>) reservaDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Reserva> findAll(Pageable pageable) {
		return reservaDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Reserva findOne(Long id) {
		return reservaDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Reserva reserva) {
		reservaDAO.save(reserva);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		reservaDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return reservaDAO.count();
	}
	
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

