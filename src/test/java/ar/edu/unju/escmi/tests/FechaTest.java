package ar.edu.unju.escmi.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.imp.ReservaDaoImp;
import ar.edu.unju.escmi.dominio.Salon;
import ar.edu.unju.escmi.exceptions.FechaPasadaException;
import ar.edu.unju.escmi.exceptions.SalonNoDisponibleException;
import ar.edu.unju.escmi.main.Main;

public class FechaTest {
	
	private static ReservaDaoImp reservaService;

	
	@BeforeAll
    public static void setUp() {
        // Inicializa la instancia de ReservaDaoImp antes de cada prueba
			reservaService = new ReservaDaoImp();

    }

    @Test
    public void testFechaDisponible_SalonDisponible() throws SalonNoDisponibleException {
        LocalDate fechaIngresada = LocalDate.of(2023, 12, 25);
        //MockReservaDaoImp reservaService = new MockReservaDaoImp(reservas);
        Salon salonAReservar = new Salon("Salon Cosmos", 60, false, 70000.0);

        boolean resultado = Main.fechaDisponible(fechaIngresada, reservaService, salonAReservar);

        assertTrue(resultado);
    }
    
    
    @Test
    public void testComprobarFechaFutura_True() throws FechaPasadaException {
    	LocalDate fechaIngresada = LocalDate.of(2025, 12, 25);
    	
    	assertTrue(Main.comprobarFechaFutura(fechaIngresada));
    }
    
    @Test
    public void testComprobarFechaFutura_FechaPasada() {
    	LocalDate fechaIngresada = LocalDate.of(2023, 12, 25);

    	assertThrows(FechaPasadaException.class, () -> { 
    		Main.comprobarFechaFutura(fechaIngresada);
    		});
    	
    }
}