package ar.edu.unju.escmi.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dominio.Cliente;
import ar.edu.unju.escmi.dominio.Reserva;
import ar.edu.unju.escmi.dominio.Salon;
import ar.edu.unju.escmi.dominio.ServicioAdicional;
import ar.edu.unju.escmi.exceptions.MontoNoValidoException;
import ar.edu.unju.escmi.main.Main;

public class MontoTest {

	@Test
    public void testValidarMontoAdelantado_ConServicios_Valido() throws MontoNoValidoException {
        double pagoAdelantado = 50.0;
        Salon salon = new Salon("Salon Cosmos", 60, false, 70000.0); 

        Cliente cliente = new Cliente("12345678", "Juan", "Pérez", "Calle Falsa 123", "123456789");
        ServicioAdicional servicio1 = new ServicioAdicional("Cámara 360", 2000, true);
        ServicioAdicional servicio2 = new ServicioAdicional("Cabina de fotos", 1500, true);
        
        Reserva reserva = new Reserva(cliente, salon, LocalDate.now(), "10:00", "16:00", 
                              10000, Arrays.asList(servicio1, servicio2), 5000, 
                              false, true);
        
        boolean resultado = Main.validarMontoAdelantado(pagoAdelantado, reserva);

        assertTrue(resultado);
    }

    @Test
    public void testValidarMontoAdelantado_ConServicios_NoValido() {
        double pagoAdelantado = 95000.0;
        Salon salon = new Salon("Salon Cosmos", 60, false, 70000.0);

        Cliente cliente = new Cliente("12345678", "Juan", "Pérez", "Calle Falsa 123", "123456789");
        ServicioAdicional servicio1 = new ServicioAdicional("Cámara 360", 2000, true);
        ServicioAdicional servicio2 = new ServicioAdicional("Cabina de fotos", 1500, true);
        
        Reserva reserva = new Reserva(cliente, salon, LocalDate.now(), "10:00", "16:00", 
                              10000, Arrays.asList(servicio1, servicio2), 5000, 
                              false, true);
        
        		assertThrows(MontoNoValidoException.class, () -> {
            Main.validarMontoAdelantado(pagoAdelantado, reserva);
        });

    }

    @Test
    public void testValidarMontoAdelantado_SinServicios_Valido() throws MontoNoValidoException {
        double pagoAdelantado = 50.0;
        Salon salon = new Salon("Salon Cosmos", 60, false, 70000.0); 

        Cliente cliente = new Cliente("12345678", "Juan", "Pérez", "Calle Falsa 123", "123456789");
        ServicioAdicional servicio1 = new ServicioAdicional("Cámara 360", 2000, true);
        ServicioAdicional servicio2 = new ServicioAdicional("Cabina de fotos", 1500, true);
        
        Reserva reserva = new Reserva(cliente, salon, LocalDate.now(), "10:00", "16:00", 
                              10000, Arrays.asList(servicio1, servicio2), 5000, 
                              false, true);
        boolean resultado = Main.validarMontoAdelantado(pagoAdelantado, reserva);

        assertTrue(resultado);
    }

    @Test
    public void testValidarMontoAdelantado_SinServicios_NoValido() {
        double pagoAdelantado = 1100000.0;
        Salon salon = new Salon("Salon Cosmos", 60, false, 70000.0); 
        List<ServicioAdicional> servicios = new ArrayList<>(); 

        Cliente cliente = new Cliente("12345678", "Juan", "Pérez", "Calle Falsa 123", "123456789");
        
        Reserva reserva = new Reserva(cliente, salon, LocalDate.now(), "10:00", "16:00", 
                              10000, servicios, 5000, 
                              false, true);
        
        assertThrows(MontoNoValidoException.class, () -> {
            Main.validarMontoAdelantado(pagoAdelantado, reserva);
        });

    }
}