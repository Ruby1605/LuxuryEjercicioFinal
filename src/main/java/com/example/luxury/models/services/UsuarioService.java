package com.example.luxury.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.example.luxury.models.entities.Usuario;

public interface UsuarioService {
	
	public List<Usuario> findAll();
	public Page<Usuario> findAll(Pageable pageable);
	public Usuario findOne(Long id);
	public void save(Usuario usuario);
	public void remove(Long id);
	public Long count();
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

