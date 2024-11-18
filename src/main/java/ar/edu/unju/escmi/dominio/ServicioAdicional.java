package ar.edu.unju.escmi.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "servicios_adicionales")
public class ServicioAdicional {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "nombre")
    private String descripcion;
	
	@Column(name = "costo")
    private double precio;
	
	@Column(name = "estado")
    private boolean estado;

	public ServicioAdicional() {
		
	}
	
	public ServicioAdicional(String descripcion, double precio, boolean estado) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
    }

    public void mostrarDatos() {
        System.out.println("Servicio Adicional ID: " + id);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Precio: $" + precio);
        System.out.println("Estado: " + (estado ? "Activo" : "Inactivo"));
        System.out.println(" ");
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}