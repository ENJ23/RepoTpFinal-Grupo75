package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IReservaDao;
import ar.edu.unju.escmi.dominio.Reserva;

public class ReservaDaoImp implements IReservaDao{

	
	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

	@Override
	public void realizarReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		try {
			manager.getTransaction().begin();
			manager.persist(reserva);
			manager.getTransaction().commit();
			System.out.println("Reserva guardada con éxito.");
			
		}catch(Exception e) {
			
			if (manager.getTransaction() != null && manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			
			System.out.println("No se pudo guardar la reserva. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
		}
	}

	@Override
	public void consultarReservas() {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
        try {
        	transaction.begin();
        	TypedQuery<Reserva> query = manager.createQuery("SELECT r FROM Reserva r", Reserva.class);
        	List<Reserva> reservas = query.getResultList();
        	if(reservas != null) {
        		System.out.println("\n");
        	for (Reserva reserva : reservas) {
        		reserva.mostrarDatos();
            	}
        	}else {
        		System.out.println("No hay reservas registrados.");
        		transaction.rollback();
        	}
        	transaction.commit();
        }catch(Exception e) {
        	if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo guardar el reserva. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
        }
	}

	@Override
	public Reserva buscarReserva(long id) {
		EntityTransaction transaction = manager.getTransaction();
        Reserva reserva = null;

        try {
            transaction.begin();
            
            reserva = manager.find(Reserva.class, id);
            
            transaction.commit(); 
            
           
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); 
            }
            System.out.println("No se pudo buscar el reserva. Error: " + e.getMessage());
        } 
	 if (reserva != null) {
		  return reserva;
	 	}else {
		 return null;	 
		}
	}

	@Override
	public void pagarReserva(Reserva reserva, double pagoNuevo) {

			EntityTransaction transaction = manager.getTransaction();
			try {
				transaction.begin();
				Reserva reservaAModificar = manager.find(Reserva.class, reserva.getId());

				if (reservaAModificar != null) {
					
						if(pagoNuevo >= reservaAModificar.calcularPagoPendiente()) {
							
							double vuelto = pagoNuevo - reservaAModificar.calcularPagoPendiente();
							System.out.println("Aqui tiene su vuelto: " + vuelto);
							
							reservaAModificar.setCancelado(true);
							reservaAModificar.setEstado(false);
							reservaAModificar.setMontoPagado(reservaAModificar.calcularMontoTotal());
							transaction.commit();
							System.out.println("Su pago ha sido completado! Muchas gracias!");
							
						}else {
							
							reservaAModificar.setMontoPagado(reservaAModificar.getPagoAdelantado() + pagoNuevo);
							transaction.commit();
						}

				}else {
					System.out.println("Reserva no encontrada");
					transaction.rollback();
				}
			}catch (Exception e){
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				
				System.out.println("No se pudo guardar la reserva. Intentelo nuevamente más tarde.");
				System.out.println("Error: " + e.getMessage());
			}
	}

	@Override
	public List<Reserva> listarReservas() {
		
        	TypedQuery<Reserva> query = manager.createQuery("SELECT r FROM Reserva r", Reserva.class);
        	List<Reserva> reservas = query.getResultList();
        	return reservas;

	}

	
}