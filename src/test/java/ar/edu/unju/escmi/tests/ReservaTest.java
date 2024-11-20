package ar.edu.unju.escmi.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dominio.Cliente;
import ar.edu.unju.escmi.dominio.Reserva;
import ar.edu.unju.escmi.dominio.Salon;
import ar.edu.unju.escmi.dominio.ServicioAdicional;

public class ReservaTest {

	private Cliente cliente;
    private Salon salon;
    private ServicioAdicional servicio1;
    private ServicioAdicional servicio2;
    private Reserva reserva;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("12345678", "Juan", "Pérez", "Calle Falsa 123", "123456789");
        salon = new Salon("Salon Cosmos", 60, false, 70000.0);
        servicio1 = new ServicioAdicional("Cámara 360", 2000, true);
        servicio2 = new ServicioAdicional("Cabina de fotos", 1500, true);
        
        reserva = new Reserva(cliente, salon, LocalDate.now(), "10:00", "16:00", 
                              10000, Arrays.asList(servicio1, servicio2), 5000, 
                              false, true);
    }

    @Test
    public void testCalcularCostoHorarioExtendido() {
        double costoExtendido = reserva.calcularCostoHorarioExtendido();
        assertEquals(20000, costoExtendido);
    }

    @Test
    public void testCalcularMontoTotal() {
        double montoTotal = reserva.calcularMontoTotal();
        assertEquals(93500, montoTotal); 
    }

    @Test
    public void testCalcularServicios() {
        double totalServicios = reserva.calcularServicios();
        assertEquals(3500, totalServicios, 0.01); 
    }


    @Test
    public void testCalcularPagoPendiente() {
        double pagoPendiente = reserva.calcularPagoPendiente();
        assertEquals(83500, pagoPendiente); // Monto total - monto pagado
    }
}