package ar.edu.unju.escmi.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dao.imp.ReservaDaoImp;
import ar.edu.unju.escmi.dominio.Cliente;
import ar.edu.unju.escmi.dominio.Reserva;
import ar.edu.unju.escmi.dominio.Salon;
import ar.edu.unju.escmi.dominio.ServicioAdicional;
import ar.edu.unju.escmi.exceptions.FechaPasadaException;
import ar.edu.unju.escmi.exceptions.SalonNoDisponibleException;
import ar.edu.unju.escmi.main.Main;

public class FechaTest {

	private class MockReservaDaoImp extends ReservaDaoImp {
        private List<Reserva> reservas;

        public MockReservaDaoImp(List<Reserva> reservas) {
            this.reservas = reservas;
        }

        @Override
        public List<Reserva> listarReservas() {
            return reservas;
        }
    }

    @Test
    public void testFechaDisponible_SalonDisponible() throws SalonNoDisponibleException {
        // Arrange
        LocalDate fechaIngresada = LocalDate.of(2023, 12, 25);
        List<Reserva> reservas = new ArrayList<>();
        MockReservaDaoImp reservaService = new MockReservaDaoImp(reservas);
        Salon salonAReservar = new Salon("Salon Cosmos", 60, false, 70000.0);

        // Act
        boolean resultado = Main.fechaDisponible(fechaIngresada, reservaService, salonAReservar);

        // Assert
        assertTrue(resultado);
    }
    
    @Test
    public void testFechaDisponible_SalonNoDisponible() {
        // Arrange
    	LocalDate fechaIngresada = LocalDate.of(2023, 12, 25);
        List<Reserva> reservas = new ArrayList<>();

        String dni = "12345678";
        String nombre = "Juan";
        String apellido = "Pérez";
        String domicilio = "Calle Falsa 123";
        String telefono = "123456789";
        Cliente cliente = new Cliente(dni, nombre, apellido, domicilio, telefono);

        Salon salonAReservar = new Salon("Salon Cosmos", 60, false, 70000.0);
        String horaInicio = "10:00";
        String horaFin = "12:00";
        double montoPagado = 100.0;
        List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();
        double pagoAdelantado = 50.0;
        boolean cancelado = false;
        boolean estado = true;

        // Crear una reserva existente
        reservas.add(new Reserva(cliente, salonAReservar, fechaIngresada, horaInicio, horaFin, 
                                  montoPagado, serviciosAdicionales, pagoAdelantado, 
                                  cancelado, estado));

        MockReservaDaoImp reservaService = new MockReservaDaoImp(reservas);

        // Act & Assert
        assertThrows(SalonNoDisponibleException.class, () -> {
            Main.fechaDisponible(fechaIngresada, reservaService, salonAReservar);
        });
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
