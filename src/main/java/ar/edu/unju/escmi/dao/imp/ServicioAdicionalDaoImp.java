package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IServicioAdicionalDao;
import ar.edu.unju.escmi.dominio.ServicioAdicional;

public class ServicioAdicionalDaoImp implements IServicioAdicionalDao{

	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

	@Override
	public void consultarServiciosAdicionales() {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
	    try {
	    	transaction.begin();
	    	TypedQuery<ServicioAdicional> query = manager.createQuery("SELECT s FROM ServicioAdicional s", ServicioAdicional.class);
	    	List<ServicioAdicional> servicios = query.getResultList();
	    	if(servicios != null) {
	    		System.out.println("\n");
	    	for (ServicioAdicional servicio : servicios) {
	            servicio.mostrarDatos();
	        	}
	    	}else {
	    		System.out.println("No hay servicios registrados.");
	    		transaction.rollback();
	    	}
	    	transaction.commit();
	    }catch(Exception e) {
	    	if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo acceder a los servicios. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
	    }
	}
	
	@Override
	public ServicioAdicional buscarServicio(long id) {
		EntityTransaction transaction = manager.getTransaction();
        ServicioAdicional servicio = null;

        try {
            transaction.begin();
            
            servicio = manager.find(ServicioAdicional.class, id);
            
            transaction.commit(); 
            
           
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); 
            }
            System.out.println("No se pudo buscar el salon. Error: " + e.getMessage());
        } 
	 if (servicio != null) {
		  return servicio;
	 	}else {
		 return null;	 
		}
	}

	@Override
	public void guardarServicio(ServicioAdicional servicio) {
		// TODO Auto-generated method stub
		try {
			manager.getTransaction().begin();
			manager.persist(servicio);
			manager.getTransaction().commit();
			System.out.println("Servicio guardado con éxito.");
			
		}catch(Exception e) {
			
			if (manager.getTransaction() != null && manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			
			System.out.println("No se pudo guardar el servicio. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
		}
	}

	@Override
	public void eliminarServicio(ServicioAdicional servicio) {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			ServicioAdicional servicioAEliminar = manager.find(ServicioAdicional.class, servicio.getId());
			servicioAEliminar.setEstado(false);
			transaction.commit();
		}catch (Exception e){
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo guardar el servicio. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
		}
	}

	@Override
	public void modificarServicio(ServicioAdicional servicio, ServicioAdicional datosActualizados) {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			ServicioAdicional servicioAModificar = manager.find(ServicioAdicional.class, servicio.getId());

			if (servicioAModificar != null) {	
				
				servicioAModificar.setDescripcion(datosActualizados.getDescripcion());
				servicioAModificar.setPrecio(datosActualizados.getPrecio());
				
			transaction.commit();
			
			}else {
				System.out.println("Servicio Adicional no encontrado");
				transaction.rollback();
			}
		}catch (Exception e){
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo guardar el servicio. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
		}
	}
}