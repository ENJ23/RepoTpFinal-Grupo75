package ar.edu.unju.escmi.dao;

import java.util.List;

import ar.edu.unju.escmi.dominio.Reserva;

public interface IReservaDao {
	
	public void realizarReserva(Reserva reserva);
	public void pagarReserva(Reserva reserva, double pagoNuevo);
	public void consultarReservas();
	public List<Reserva> listarReservas();
	public Reserva buscarReserva(long id);
}