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
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name="reservas")
public class Reserva implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Size(max=10)
@Column(name="codigo")
private String codigo;


@NotNull
@DateTimeFormat(pattern = "yyyy-MM-dd")
@Column(name="fecha")
private LocalDate fecha;


@NotNull
@Column(name="hora")
private Integer hora;


@NotBlank
@Size(max=15)
@Column(name="dni")
private String dni;


@NotBlank
@Size(max=20)
@Column(name="servicio")
private String servicio;



	
	public Reserva() {}


	public Long getId() {
		return id;
	}


	public String getCodigo() {
		return codigo;
}
public void setCodigo(String codigo) {
	this.codigo = codigo;
}
public LocalDate getFecha() {
		return fecha;
}
public void setFecha(LocalDate fecha) {
	this.fecha = fecha;
}
public Integer getHora() {
		return hora;
}
public void setHora(Integer hora) {
	this.hora = hora;
}
public String getDni() {
		return dni;
}
public void setDni(String dni) {
	this.dni = dni;
}
public String getServicio() {
		return servicio;
}
public void setServicio(String servicio) {
	this.servicio = servicio;
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
		Reserva other = (Reserva) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

