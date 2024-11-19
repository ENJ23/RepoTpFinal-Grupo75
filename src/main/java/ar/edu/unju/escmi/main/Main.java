package ar.edu.unju.escmi.main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.dao.imp.ReservaDaoImp;
import ar.edu.unju.escmi.dao.imp.SalonDaoImp;
import ar.edu.unju.escmi.dao.imp.ServicioAdicionalDaoImp;
import ar.edu.unju.escmi.dominio.Cliente;
import ar.edu.unju.escmi.dominio.Reserva;
import ar.edu.unju.escmi.dominio.Salon;
import ar.edu.unju.escmi.dominio.ServicioAdicional;
import ar.edu.unju.escmi.exceptions.ClienteNoRegistradoException;
import ar.edu.unju.escmi.exceptions.FechaPasadaException;
import ar.edu.unju.escmi.exceptions.HoraFueraDeRangoException;
import ar.edu.unju.escmi.exceptions.MontoNoValidoException;
import ar.edu.unju.escmi.exceptions.SalonNoDisponibleException;

public class Main {

    public static void main(String[] args) {
        ClienteDaoImp clienteService = new ClienteDaoImp();
        SalonDaoImp salonService = new SalonDaoImp();
        ServicioAdicionalDaoImp servicioService = new ServicioAdicionalDaoImp();
        ReservaDaoImp reservaService = new ReservaDaoImp();
        
        //cargarSalones(salonService);
        //cargarServiciosAdicionales(servicioService);
        
    	Scanner scanner = new Scanner(System.in);
        int opcion;

        
        do {
        	try {
            System.out.println("\nMenú de Opciones:");
            System.out.println("1. Alta de Cliente");
            System.out.println("2. Consultar Clientes");
            System.out.println("3. Modificar Cliente");
            System.out.println("4. Realizar Pago");
            System.out.println("5. Realizar Reserva");
            System.out.println("6. Consultar Todas las Reservas");
            System.out.println("7. Consultar una Reserva");
            System.out.println("8. Consultar Salones");
            System.out.println("9. Consultar Servicios Adicionales");
            System.out.println("0. Salir");
            System.out.print("\nSeleccione una opción: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 
        	}catch(InputMismatchException e) {
        		opcion = 999;
        		scanner.nextLine();
        	}
            switch (opcion) {
                case 1:
                    Cliente cliente = ingresoDatosCliente(scanner);
                    clienteService.guardarCliente(cliente);
                    break;
                case 2:
                    clienteService.consultarClientes();
                    break;
                case 3:

                	try {
                		long idModificar = 0;
	                	System.out.println("Ingrese el ID del cliente a modificar: ");
	                	clienteService.consultarClientes();
	                	idModificar = scanner.nextInt();
	                	scanner.nextLine();
	                	//if (id != 0) {
	                	Cliente clienteBuscado = clienteService.buscarCliente(idModificar);
	                	Cliente datosActualizados = ingresoDatosCliente(scanner);

	                    clienteService.modificarCliente(clienteBuscado, datosActualizados);
						/*
						 * }else { System.out.println("ID no encontrado."); }
						 */
	                
                	}catch(ClienteNoRegistradoException e){
                		System.out.println(e.getMessage());
                		opcion = 999;
                		scanner.nextLine();
                	}catch(Exception e) {
                		System.out.println("Ha ocurrido un error. Intentelo de nuevo.");
                	}
                	
                    break;
                case 4:
                	
                	System.out.println("Ingrese el ID de la reserva que quiere pagar: ");
                	long idReserva = 0;
                	
	                	while (true) {
	                		try {
		                		idReserva = Long.parseLong(scanner.nextLine());
		                		break;
	                		}catch(NumberFormatException e) {
	                			System.out.println("Ingrese el id correctamente: ");
	                		}
	                	}
	                	
	                Reserva reservaAPagar = reservaService.buscarReserva(idReserva);
	                if(reservaAPagar != null) {
	                System.out.println("El monto a pagar es: " + reservaAPagar.calcularPagoPendiente());
	                System.out.println("¿Quiere pagar la reserva? (s/n)");
	                String continuar = scanner.nextLine();
	                
		                if(continuar.equalsIgnoreCase("s") && reservaAPagar != null) {
		                	System.out.println("Cuanto quiere abonar? ");
		                	double pagoNuevo = 0;
		                	while (true) {
		                		try {
		                			pagoNuevo = Double.parseDouble(scanner.nextLine());
		                			break;
		                		}catch(NumberFormatException e) {
		                			System.out.println("Ingrese un numero valido: ");
		                		}
		                	}
		                	
		                    reservaService.pagarReserva(reservaAPagar, pagoNuevo);
		                    System.out.println("Gracias por realizar el pago!");
		                }else {
		                	System.out.println("Procupe pagarla pronto...");
		                }
	               }else {
	            	   System.out.println("Reserva no encontrada. Intente de nuevo con otro ID.");
	               }
                	
                    break;
                case 5:
                    Reserva nuevaReserva = realizarReserva(scanner, clienteService, salonService, servicioService, reservaService);
                    reservaService.realizarReserva(nuevaReserva);
                    break;
                case 6:
                    reservaService.consultarReservas();
                    break;
                case 7:
                	try {
	                	System.out.println("Ingrese el ID de la reserva buscada: ");
	                	long idReservaBuscada = scanner.nextLong();
	                	scanner.nextLine();
	                	
	                	
	                    Reserva reservaBuscada = reservaService.buscarReserva(idReservaBuscada);
	                    	if (reservaBuscada != null) {
	                    		reservaBuscada.mostrarDatos();
	                    	}else {
	                    		System.out.println("Reserva no encontrada.");
	                    	}
	                    
                	}catch(InputMismatchException e) {
                		System.out.println("Debe ingresar un numero");
                		opcion = 999;
                    	scanner.nextLine();
            		}catch(Exception e) {
                    	System.out.println("Error." + " Intentelo de nuevo más tarde");
                    	opcion = 999;
                    	scanner.nextLine();
                    }
                    break;
                case 8:
                    salonService.consultarSalones();
                    break;
                case 9:
                    servicioService.consultarServiciosAdicionales();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            
            
            		}
        	}while (opcion != 0);

        }

	


	





    