package com.example.luxury.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.example.luxury.models.dao.UsuarioDAO;
import com.example.luxury.models.entities.Usuario;


@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioDAO usuarioDAO;
	
	
	
	public UsuarioServiceImpl(
			
			UsuarioDAO usuarioDAO
			) {
		
		this.usuarioDAO = usuarioDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Usuario findOne(Long id) {
		return usuarioDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Usuario usuario) {
		usuarioDAO.save(usuario);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		usuarioDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return usuarioDAO.count();
	}
	
	
	
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

