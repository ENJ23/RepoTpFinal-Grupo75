package ar.edu.unju.escmi.exceptions;

public class HoraFueraDeRangoException extends Exception{


	private static final long serialVersionUID = 1L;
	
	public HoraFueraDeRangoException(String mensaje) {
		super(mensaje);
	}

}
