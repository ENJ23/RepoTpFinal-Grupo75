package ar.edu.unju.escmi.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salones")
public class Salon {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre; // Ej. "Cosmos", "Esmeralda", "Galaxy"

    @Column(name = "capacidad")
    private int capacidad;

    @Column(name = "incluye_pileta")
    private boolean conPileta;
    
    @Column(name = "precio")
    private double precio;
    
    // Constructor
    public Salon(String nombre, int capacidad, boolean conPileta, double precio) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.conPileta = conPileta;
        this.precio = precio;
    }

    public Salon() {
		// TODO Auto-generated constructor stub
	}

	// Método para mostrar los datos del salón
    public void mostrarDatos() {
        System.out.println("Salón ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Capacidad: " + capacidad);
        System.out.println("Con pileta: " + (conPileta ? "Sí" : "No"));
        System.out.println("Precio: $" + precio);
        System.out.println(" ");
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public boolean isConPileta() {
		return conPileta;
	}

	public void setConPileta(boolean conPileta) {
		this.conPileta = conPileta;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}



}