package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.dominio.ServicioAdicional;

public interface IServicioAdicionalDao {

	public void guardarServicio(ServicioAdicional servicio);
	public void consultarServiciosAdicionales();
	public ServicioAdicional buscarServicio(long id);
	public void eliminarServicio(ServicioAdicional servicio);
	public void modificarServicio(ServicioAdicional servicio, ServicioAdicional datosActualizados);
}