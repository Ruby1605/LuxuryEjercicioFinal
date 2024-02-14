package com.example.luxury.models.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Size(max=15)
@Column(name="dni")
private String dni;


@NotBlank
@Size(max=20)
@Column(name="nombre")
private String nombre;


@NotBlank
@Size(max=20)
@Column(name="apellido1")
private String apellido1;


@NotBlank
@Size(max=20)
@Column(name="apellido2")
private String apellido2;


@NotNull
@Column(name="administrador")
private Boolean administrador;


@NotBlank
@Size(min=6)
@Column(name="password")
private String password;


@NotBlank
@Column(name="email")
private String email;


@Column(name="foto")
private String foto;



	
	public Usuario() {}


	public Long getId() {
		return id;
	}


	public String getDni() {
		return dni;
}
public void setDni(String dni) {
	this.dni = dni;
}
public String getNombre() {
		return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getApellido1() {
		return apellido1;
}
public void setApellido1(String apellido1) {
	this.apellido1 = apellido1;
}
public String getApellido2() {
		return apellido2;
}
public void setApellido2(String apellido2) {
	this.apellido2 = apellido2;
}
public Boolean getAdministrador() {
		return administrador;
}
public void setAdministrador(Boolean administrador) {
	this.administrador = administrador;
}
public String getPassword() {
		return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
		return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getFoto() {
		return foto;
}
public void setFoto(String foto) {
	this.foto = foto;
}

	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

