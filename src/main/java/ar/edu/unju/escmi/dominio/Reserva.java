package ar.edu.unju.escmi.dominio;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "reservas")

public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // Relación con la clase Cliente

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon; // Relación con la clase Salon

    @Column(name = "fecha")
    private LocalDate fecha; // Fecha de la reserva

    @Column(name = "hora_inicio")
    private String horaInicio; // Hora de inicio de la reserva

    @Column(name = "hora_fin")
    private String horaFin; // Hora de fin de la reserva

    @Column(name = "monto_pagado")
    private double montoPagado; 

    @ManyToMany
    @JoinTable(
        name = "reserva_servicios",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<ServicioAdicional> serviciosAdicionales; // Relación con la clase ServicioAdicional

    @Column(name = "pago_adelantado")
    private double pagoAdelantado; // Monto pagado por adelantado

    @Column(name = "cancelado")
    private boolean cancelado; // Estado de cancelación

    @Column(name = "estado")
    private boolean estado;

    // Constructor
    public Reserva(Cliente cliente, Salon salon, LocalDate fecha, String horaInicio, String horaFin,
                   double montoPagado, List<ServicioAdicional> serviciosAdicionales, double pagoAdelantado,
                   boolean cancelado, boolean estado) {
        this.cliente = cliente;
        this.salon = salon;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.montoPagado = montoPagado;
        this.serviciosAdicionales = serviciosAdicionales;
        this.pagoAdelantado = pagoAdelantado;
        this.cancelado = cancelado;
        this.estado = estado;
    }

    public Reserva() {
		// TODO Auto-generated constructor stub
	}

	public double calcularCostoHorarioExtendido() {
        int horasExtras = calcularHorasExtras(); 
        return horasExtras * 10000; 
    }

    public double calcularMontoTotal() {
        double totalServicios = calcularServicios();
        double costoExtendido = calcularCostoHorarioExtendido();
        return salon.getPrecio() + costoExtendido + totalServicios;
    }
    
    public double calcularServicios() {
    	double totalServicios = serviciosAdicionales.stream()
                .filter(ServicioAdicional::isEstado) // Solo contar servicios activos
                .mapToDouble(ServicioAdicional::getPrecio)
                .sum();
    	return totalServicios;
    }

    // Método para mostrar todos los datos de la reserva
    public void mostrarDatos() {
    	
        System.out.println("\nReserva ID: " + id);
        cliente.mostrarCliente();
        salon.mostrarDatos();
        System.out.println("Fecha: " + fecha);
        System.out.println("Hora Inicio: " + horaInicio);
        System.out.println("Hora Fin: " + horaFin);
        System.out.println("Monto Pagado: $" + montoPagado);
        System.out.println("Pago Adelantado: $" + pagoAdelantado);
        System.out.println("Estado del Pago: " + (cancelado ? "CANCELADO" : "PAGO PENDIENTE"));
        System.out.println("Estado: " + (estado ? "Activo" : "Inactivo"));
        System.out.println(" ");
    }

    public double calcularPagoPendiente() {
        double montoTotal = calcularMontoTotal();
        return montoTotal - montoPagado;
    }

    private int calcularHorasExtras() {
    	LocalTime inicio = LocalTime.parse(horaInicio);
        LocalTime fin = LocalTime.parse(horaFin);
        
        Duration duration = Duration.between (inicio, fin);
        long horasReservadas = duration.toHours();

        int horasEstándar = 4; // Cambiar según la lógica real
        int horasExtras = (int) Math.max(0, horasReservadas - horasEstándar);
        
        return horasExtras;
        
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Salon getSalon() {
		return salon;
	}

	public void setSalon(Salon salon) {
		this.salon = salon;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public double getMontoPagado() {
		return montoPagado;
	}

	public void setMontoPagado(double montoPagado) {
		this.montoPagado = montoPagado;
	}

	public List<ServicioAdicional> getServiciosAdicionales() {
		return serviciosAdicionales;
	}

	public void setServiciosAdicionales(List<ServicioAdicional> serviciosAdicionales) {
		this.serviciosAdicionales = serviciosAdicionales;
	}

	public double getPagoAdelantado() {
		return pagoAdelantado;
	}

	public void setPagoAdelantado(double pagoAdelantado) {
		this.pagoAdelantado = pagoAdelantado;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}