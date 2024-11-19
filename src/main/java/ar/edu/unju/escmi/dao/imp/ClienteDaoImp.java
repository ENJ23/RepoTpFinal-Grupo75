package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dominio.Cliente;
import ar.edu.unju.escmi.exceptions.ClienteNoRegistradoException;

public class ClienteDaoImp implements IClienteDao{

	private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

	
	@Override
	public void guardarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
				
		try {
					manager.getTransaction().begin();
					manager.persist(cliente);
					manager.getTransaction().commit();
					System.out.println("Cliente guardado con éxito.");
					
				}catch(Exception e) {
					
					if (manager.getTransaction() != null && manager.getTransaction().isActive()) {
						manager.getTransaction().rollback();
					}
					
					System.out.println("No se pudo guardar el cliente. Intentelo nuevamente más tarde.");
					System.out.println("Error: " + e.getMessage());
				}
	}

	@Override
	public void modificarCliente(Cliente cliente, Cliente datosActualizados) {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			Cliente clienteAModificar = manager.find(Cliente.class, cliente.getId());

			if (clienteAModificar != null) {	
				
				clienteAModificar.setNombre(datosActualizados.getNombre());
	            clienteAModificar.setApellido(datosActualizados.getApellido());
	            clienteAModificar.setDomicilio(datosActualizados.getDomicilio());
	            clienteAModificar.setDni(datosActualizados.getDni());
	            
			transaction.commit();
			
			}else {
				System.out.println("Cliente no encontrado");
				transaction.rollback();
			}
		}catch (Exception e){
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo guardar el cliente. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
		}finally {
			//manager.close();
		}
	}

	@Override
	public void consultarClientes() {
		// TODO Auto-generated method stub
        EntityTransaction transaction = manager.getTransaction();
        try {
        	transaction.begin();
        	TypedQuery<Cliente> query = manager.createQuery("SELECT c FROM Cliente c", Cliente.class);
        	List<Cliente> clientes = query.getResultList();
        	if(clientes != null) {
        	for (Cliente cliente : clientes) {
                cliente.mostrarCliente();
            	}
        	}else {
        		System.out.println("No hay clientes registrados.");
        		transaction.rollback();
        	}
        	transaction.commit();
        }catch(Exception e) {
        	if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo guardar el cliente. Intentelo nuevamente más tarde.");
			System.out.println("Error: " + e.getMessage());
        }
	}

	@Override
	public Cliente buscarCliente(long id) throws ClienteNoRegistradoException {
		EntityTransaction transaction = manager.getTransaction();
        Cliente cliente = null;

        try {
            transaction.begin();
            
            cliente = manager.find(Cliente.class, id);
            
            transaction.commit(); 
            
           
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); 
            }
            System.out.println("No se pudo buscar el cliente. Error: " + e.getMessage());
        } finally {
           // manager.close(); 
        }
	 if (cliente != null) {
		  return cliente;
	 }else {
		 throw new ClienteNoRegistradoException("Cliente No Encontrado en la Base de Datos");
	 }
      
    
	}

	@Override
	public void eliminarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		EntityTransaction transaction = manager.getTransaction();
		try {
			transaction.begin();
			Cliente clienteAEliminar = manager.find(Cliente.class, cliente.getId());
			clienteAEliminar.setEstado(false);
			transaction.commit();
		}catch (Exception e){
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			
			System.out.println("No se pudo guardar el cliente. Intentelo nuevamente más tarde.");
			System.out.println("ERROR: " + e.getMessage());
		}
	}

}