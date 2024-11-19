package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.dominio.Salon;

public interface ISalonDao {

	public void guardarSalon(Salon salon);
	public void consultarSalones();
	public Salon buscarSalon(long id);
	public void eliminarSalon(Salon salon);
	public void modificarSalon(Salon salonAModificar, Salon datosNuevos);
}