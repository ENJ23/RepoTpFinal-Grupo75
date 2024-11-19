package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.ISalonDao;
import ar.edu.unju.escmi.dominio.Salon;

public class SalonDaoImp implements ISalonDao{

	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

	
	@Override
	public void consultarSalones() {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
        try {
        	transaction.begin();
        	TypedQuery<Salon> query = manager.createQuery("SELECT s FROM Salon s", Salon.class);
        	List<Salon> salones = query.getResultList();
        	if(!salones.isEmpty()) {
        		System.out.println("\n");
        	for (Salon salon : salones) {
                salon.mostrarDatos();
            	}
        	}else {
        		System.out.println("No hay salones registrados.");
        		transaction.rollback();
        	}
        	transaction.commit();
        }catch(Exception e) {
        	if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo acceder a los salones. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
        }
	}


	@Override
	public Salon buscarSalon(long id) {
		EntityTransaction transaction = manager.getTransaction();
        Salon salon = null;

        try {
            transaction.begin();
            
            salon = manager.find(Salon.class, id);
            
            transaction.commit(); 
            
           
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); 
            }
            System.out.println("No se pudo buscar el salon. Error: " + e.getMessage());
        } 
	 if (salon != null) {
		  return salon;
	 	}else {
		 return null;	 
		}
	}


	@Override
	public void guardarSalon(Salon salon) {
		// TODO Auto-generated method stub
		try {
			manager.getTransaction().begin();
			manager.persist(salon);
			manager.getTransaction().commit();
			System.out.println("Salon guardado con éxito.");
			
		}catch(Exception e) {
			
			if (manager.getTransaction() != null && manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			
			System.out.println("No se pudo guardar el salon. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
		}
	}


	@Override
	public void eliminarSalon(Salon salon) {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			manager.remove(salon);
			transaction.commit();
		}catch (Exception e){
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo guardar el salon. Intentelo nuevamente más tarde.");
			System.out.println("ERROR: " + e.getMessage());
		}
	}


	@Override
	public void modificarSalon(Salon salon, Salon datosActualizados) {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			Salon salonAModificar = manager.find(Salon.class, salon.getId());

			if (salonAModificar != null) {	
				salonAModificar.setCapacidad(datosActualizados.getCapacidad());
				salonAModificar.setNombre(datosActualizados.getNombre());
				salonAModificar.setConPileta(datosActualizados.isConPileta());
				salonAModificar.setPrecio(datosActualizados.getPrecio());
				
			transaction.commit();
			
			}else {
				System.out.println("Salon no encontrado");
				transaction.rollback();
			}
		}catch (Exception e){
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo guardar el salon. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
		}
	}

	
}