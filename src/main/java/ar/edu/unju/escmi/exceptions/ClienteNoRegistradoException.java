package ar.edu.unju.escmi.exceptions;

public class ClienteNoRegistradoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteNoRegistradoException(String mensaje) {
	        super(mensaje);
	    }
}
