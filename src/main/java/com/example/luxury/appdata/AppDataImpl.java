package com.example.luxury.appdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.springframework.stereotype.Component;


@Component
public class AppDataImpl  implements AppData {

	private String name; 
	private String author; 
	private int year; 
	private String web; 
	private String webURL; 
	private TreeMap<String,GeneralOption> generalOptions; 
	private ArrayList<GeneralOption> sortedGeneralOptions;
			
	public AppDataImpl() {
		name = "Luxury";
		author = "Rubén Casado";
		year = 2024;
		web = "";
		webURL = "";
		
		generalOptions = new TreeMap<>();
		sortedGeneralOptions = new ArrayList<>();
		int order=0;
		
		GeneralOption opUsuarios = new GeneralOption(order,"USUARIOS","Usuarios","/usuarios/list","LIST");

opUsuarios.addScreen("LIST","Listado de Usuarios");
opUsuarios.addScreen("CREATE","Alta de Usuarios");
opUsuarios.addScreen("UPDATE","Modificación de Usuarios");
opUsuarios.addScreen("VIEW","Ficha del Usuario");
opUsuarios.addScreen("VIEWIMG","Usuario - View Image");

opUsuarios.setEmptyMessage("No hay usuarios que mostrar");

generalOptions.put("USUARIOS",opUsuarios);
sortedGeneralOptions.add(opUsuarios);

order++;

GeneralOption opServicios = new GeneralOption(order,"SERVICIOS","Servicios","/servicios/list","LIST");

opServicios.addScreen("LIST","Listado de Servicios");
opServicios.addScreen("CREATE","Alta de Servicios");
opServicios.addScreen("UPDATE","Modificación de Servicios");
opServicios.addScreen("VIEW","Ficha del Servicio");
opServicios.addScreen("VIEWIMG","Servicio - View Image");

opServicios.setEmptyMessage("No hay servicios que mostrar");

generalOptions.put("SERVICIOS",opServicios);
sortedGeneralOptions.add(opServicios);

order++;

GeneralOption opReservas = new GeneralOption(order,"RESERVAS","Gestión de Reservas","/reservas/list","LIST");

opReservas.addScreen("LIST","Listado de Reservas");
opReservas.addScreen("CREATE","Alta de Reservas");
opReservas.addScreen("UPDATE","Modificación de Reservas");
opReservas.addScreen("VIEW","Ficha de la Reserva");
opReservas.addScreen("VIEWIMG","Reserva - View Image");

opReservas.setEmptyMessage("No hay reservas que mostrar");

generalOptions.put("RESERVAS",opReservas);
sortedGeneralOptions.add(opReservas);

order++;


		
		Collections.sort(sortedGeneralOptions);
		
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	
	@Override
	public String getAuthor() {
		return author;
	}

	
	@Override
	public int getYear() {
		return year;
	}

	
	@Override
	public String getWeb() {
		return web;
	}
	
	
	@Override
	public String getWebUrl() {
		return webURL;
	}

	
	@Override
	public String getAuthorship() {
		String authorship = "";
		if(!author.isBlank() || !web.isBlank()) {
			authorship += author+" "+year;
			if(!web.isBlank()) authorship += " - "+web;
		}		
		return authorship.trim();
	}
	
	
	@Override
	public TreeMap<String, GeneralOption> getGeneralOptions() {
		return generalOptions;
	}
	
	@Override
	public ArrayList<GeneralOption> getSortedGeneralOptions() {
		return sortedGeneralOptions;
	}
	
	@Override
	public boolean isMainScreen(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getMainScreenCode().equals(screenCode);
	}
	
	@Override
	public String getMainScreenName(String optionCode) {
		return generalOptions.get(optionCode).getMainScreenName();
	}
	
	@Override
	public String getMainScreenLink(String optionCode) {
		return generalOptions.get(optionCode).getLink();
	}
	
	@Override
	public String getScreenName(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getScreen(screenCode);
	}

	@Override
	public String getEmptyMessage(String optionCode) {
		return generalOptions.get(optionCode).getEmptyMessage();
	}
		
}



/*** Duende Code Generator Jose Manuel Rosado ejerciciosmesa.com 2023 ***/

