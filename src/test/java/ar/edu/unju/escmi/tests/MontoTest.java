package ar.edu.unju.escmi.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.dominio.Salon;
import ar.edu.unju.escmi.dominio.ServicioAdicional;
import ar.edu.unju.escmi.exceptions.MontoNoValidoException;
import ar.edu.unju.escmi.main.Main;

public class MontoTest {

	@Test
    public void testValidarMontoAdelantado_ConServicios_Valido() throws MontoNoValidoException {
        // Arrange
        double pagoAdelantado = 50.0;
        Salon salon = new Salon("Salon Cosmos", 60, false, 70000.0); 
        List<ServicioAdicional> servicios = new ArrayList<>();
        servicios.add(new ServicioAdicional("Servicio 1", 30.0,true)); 
        servicios.add(new ServicioAdicional("Servicio 2", 10.0, true)); 

        // Act
        boolean resultado = Main.validarMontoAdelantado(pagoAdelantado, salon, servicios);

        // Assert
        assertTrue(resultado);
    }

    @Test
    public void testValidarMontoAdelantado_ConServicios_NoValido() {
        double pagoAdelantado = 80000.0;
        Salon salon = new Salon("Salon Cosmos", 60, false, 70000.0); // Precio del salón es 100.0
        List<ServicioAdicional> servicios = new ArrayList<>();
        servicios.add(new ServicioAdicional("Servicio 1", 30.0,true)); // Precio del servicio es 30.0
        servicios.add(new ServicioAdicional("Servicio 2", 10.0, true)); // Precio del servicio es 10.0

        		assertThrows(MontoNoValidoException.class, () -> {
            Main.validarMontoAdelantado(pagoAdelantado, salon, servicios);
        });

    }

    @Test
    public void testValidarMontoAdelantado_SinServicios_Valido() throws MontoNoValidoException {
        double pagoAdelantado = 50.0;
        Salon salon = new Salon("Salon Cosmos", 60, false, 70000.0); // Precio del salón es 100.0
        List<ServicioAdicional> servicios = new ArrayList<>(); // Sin servicios adicionales

        boolean resultado = Main.validarMontoAdelantado(pagoAdelantado, salon, servicios);

        assertTrue(resultado);
    }

    @Test
    public void testValidarMontoAdelantado_SinServicios_NoValido() {
        double pagoAdelantado = 80000.0;
        Salon salon = new Salon("Salon Cosmos", 60, false, 70000.0); // Precio del salón es 70.000,0
        List<ServicioAdicional> servicios = new ArrayList<>(); // Sin servicios

        assertThrows(MontoNoValidoException.class, () -> {
            Main.validarMontoAdelantado(pagoAdelantado, salon, servicios);
        });

    }
}
