package ar.edu.unju.escmi.dao;

import ar.edu.unju.escmi.dominio.Cliente;
import ar.edu.unju.escmi.exceptions.ClienteNoRegistradoException;

public interface IClienteDao {

	public void guardarCliente(Cliente cliente);
	public void modificarCliente(Cliente clienteAModificar, Cliente datosNuevos);
	public Cliente buscarCliente(long id) throws ClienteNoRegistradoException;
	public void consultarClientes();
	public void eliminarCliente(Cliente cliente);
}