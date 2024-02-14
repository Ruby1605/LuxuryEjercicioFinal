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
@Table(name="servicios")
public class Servicio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Size(max=20)
@Column(name="nombre")
private String nombre;


@NotNull
@Column(name="aforo")
private Integer aforo;


@Column(name="image")
private String image;



	
	public Servicio() {}


	public Long getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public Integer getAforo() {
		return aforo;
}
public void setAforo(Integer aforo) {
	this.aforo = aforo;
}
public String getImage() {
		return image;
}
public void setImage(String image) {
	this.image = image;
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
		Servicio other = (Servicio) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

