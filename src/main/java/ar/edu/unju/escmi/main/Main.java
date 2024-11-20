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

	private static Cliente ingresoDatosCliente(Scanner scanner) {
		String dni = "";
        String nombre = "";
        String apellido = "";
        String domicilio = "";
        String telefono = "";

        boolean datosValidos = false;

        while (!datosValidos) {
            try {
                System.out.print("Ingrese el DNI del cliente: ");
                dni = scanner.nextLine();
                if (!dni.matches("\\d{8,9}")) {
                    throw new IllegalArgumentException("El DNI debe tener 8 o 9 dígitos.");
                }

                System.out.print("Ingrese el nombre del cliente: ");
                nombre = scanner.nextLine();
                if (nombre.trim().isEmpty()) { 
                    throw new IllegalArgumentException("El nombre no puede estar vacío.");
                }

                System.out.print("Ingrese el apellido del cliente: ");
                apellido = scanner.nextLine();
                if (apellido.trim().isEmpty()) { 
                    throw new IllegalArgumentException("El apellido no puede estar vacío.");
                }

                System.out.print("Ingrese el domicilio del cliente: ");
                domicilio = scanner.nextLine();
                if (domicilio.trim().isEmpty()) { 
                    throw new IllegalArgumentException("El domicilio no puede estar vacío.");
                }

                System.out.print("Ingrese el teléfono del cliente: ");
                telefono = scanner.nextLine();
                if (!telefono.matches("\\d+")) { 
                    throw new IllegalArgumentException("El teléfono debe contener solo dígitos.");
                }

                datosValidos = true; 

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        return new Cliente(dni, nombre, apellido, domicilio, telefono);
    }
		
	
	private static Reserva realizarReserva(Scanner scanner, ClienteDaoImp clienteService, SalonDaoImp salonService, ServicioAdicionalDaoImp serviciosService, ReservaDaoImp reservaService) {
		try {
		Cliente cliente = new Cliente();
		Salon salonBuscado = new Salon();
		Reserva nuevaReserva = new Reserva();
		
		long id, idSalon;
		double montoAdelantado = 0;
		boolean comprobacionFecha = false, comprobacionHora = false, validacionMonto = false;
		
		List<ServicioAdicional> servicios = new ArrayList<>();
		
		System.out.println("\n¿Qué cliente va a reservar? Ingrese su ID.");
		clienteService.consultarClientes();
		id = scanner.nextLong();
		scanner.nextLine();
		
			try {
				cliente = clienteService.buscarCliente(id);
			} catch (ClienteNoRegistradoException e) {
				System.out.println("\nEl cliente no está registrado. Ingrese sus datos.");
				cliente = ingresoDatosCliente(scanner);
				clienteService.guardarCliente(cliente);
			}
			
			nuevaReserva.setCliente(cliente);
			
		System.out.println("\nIngrese el ID del salón desea alquilar: ");
		salonService.consultarSalones();
			while (true) {
				try {
				idSalon = Long.parseLong(scanner.nextLine());
				salonBuscado = salonService.buscarSalon(idSalon);
				nuevaReserva.setSalon(salonBuscado);
					if (salonBuscado != null) {
						break;
					}else {
						System.out.println("Salon no Encontrado. Ingrese otro ID."); 
					}
				
				}catch(InputMismatchException e) {
					System.out.println("Ingresa un ID valido: ");
				}
			}
			
		System.out.println("¿Para que fecha desea realizar la reserva? : (yyyy-MM-dd) ");
			while (!comprobacionFecha) {
				try {
					
		        String fechaEntrada = scanner.nextLine();
		        LocalDate fechaIngresada = transformarFecha(fechaEntrada);
		        comprobarFechaFutura(fechaIngresada);
		        
		        	if (fechaDisponible(fechaIngresada, reservaService, salonBuscado)) {
		        		System.out.println("Fecha Disponible.");
		        		nuevaReserva.setFecha(fechaIngresada);
		        		comprobacionFecha = true;
		        	}
		        	
				}catch(FechaPasadaException e) {
					System.out.println(e.getMessage());
					comprobacionFecha = false;
				}catch(SalonNoDisponibleException e) {
					System.out.println(e.getMessage());
					comprobacionFecha = false;
				}catch(DateTimeParseException e) {
					System.out.println("Ingrese la fecha con el formato indicado: (yyyy-MM-dd): ");
					comprobacionFecha = false;
				}
			}
			
		System.out.println("¿En que horario querría realizar la reserva? : (HH:mm) (10:00 - 23:00) ");
			while(!comprobacionHora) {
				try {
					
					String horaEntrada = scanner.nextLine();
					LocalTime horaInicio = transformarHora(horaEntrada);
					comprobacionHora = comprobarHoraEnRango(horaInicio);
					nuevaReserva.setHoraInicio(horaEntrada);
					
				}catch(HoraFueraDeRangoException e) {
					System.out.println(e.getMessage());
					comprobacionHora = false;
				}catch(DateTimeParseException e) {
					System.out.println("Ingrese la hora con el formatio indicado: (HH:mm)");
					comprobacionHora = false;
				}catch(Exception e){
					System.out.println("Entrada no válida. Intente nuevamente.");
			        comprobacionHora = false;
				}
			}
			
		System.out.println("¿En que horario querría terminar la reserva? : (HH:mm) ");
		comprobacionHora = false;
			while(!comprobacionHora) {
				try {
					String horaEntrada = scanner.nextLine();
					LocalTime horaFinal = transformarHora(horaEntrada);
					comprobacionHora = comprobarHoraEnRango(horaFinal);
					nuevaReserva.setHoraFin(horaEntrada);
				}catch(HoraFueraDeRangoException e) {
					System.out.println(e.getMessage());
					comprobacionHora = false;
				}catch(DateTimeParseException e) {
					System.out.println("Ingrese la hora con el formatio indicado: (HH:mm)");
					comprobacionHora = false;
				}catch(Exception e) {
					System.out.println("Entrada no válida. Intente nuevamente.");
			        comprobacionHora = false;
				}
			}
		
		System.out.println("¿Desea añadir servicios adicionales?:  (s/n) ");	
		String continuar = scanner.nextLine();
		
		while(continuar.equalsIgnoreCase("s")) {
			System.out.println("Ingrese el ID del servicio que quiere agregar: ");
			serviciosService.consultarServiciosAdicionales();
			while (true) {
				try {
					long idServicio = Long.parseLong(scanner.nextLine());
					ServicioAdicional servicio = serviciosService.buscarServicio(idServicio);
					servicios.add(servicio);
					System.out.println("Se ha añadido el servicio a su reserva.");
					System.out.println("Se suma $ " +  servicio.getPrecio() + " a la reserva");
					break;
				}catch(NumberFormatException e) {
					System.out.println("Ingrese un ID válido: ");
				}catch(Exception e) {
					System.out.println("Ha ocurrido un error. Intentalo de nuevo.");
				}
			}
			
			System.out.println("¿Deseas añadir otro servicio adicional? (s/n)");
			continuar = scanner.nextLine();
		}
		
		nuevaReserva.setServiciosAdicionales(servicios);	

		System.out.println("Servicios: " + nuevaReserva.calcularServicios());
		System.out.println("Horas extra: " + nuevaReserva.calcularCostoHorarioExtendido());
		System.out.println("Salon: " + salonBuscado.getPrecio());
		System.out.println("El Total es de: " + nuevaReserva.calcularMontoTotal());
		
		System.out.println("¿Desea abonar por adelantado? Si es asi, ¿Cuanto? En caso de que no, escribir '00' (00,00)");	
			while(!validacionMonto) {
				try {
					montoAdelantado = Double.parseDouble(scanner.nextLine());
					validacionMonto = validarMontoAdelantado(montoAdelantado, nuevaReserva);
					nuevaReserva.setPagoAdelantado(montoAdelantado);
					nuevaReserva.setMontoPagado(montoAdelantado);
					break;
				}catch(NumberFormatException e) {
					System.out.println("Ingrese un monto para pagar: (00,00)");
				}catch(MontoNoValidoException e) {
					System.out.println("Ingrese otro monto.");
					System.out.println(e.getMessage());
				}
			}
	
			nuevaReserva.setCancelado(false);
			nuevaReserva.setEstado(true);
			
		return nuevaReserva;
		
		}catch(Exception e) {
			
			System.out.println("Ha ocurrido un error. Intentelo de nuevo mas tarde. ");
			System.out.println("ERROR: " + e.getMessage());
			return null;
		}
	}
	
	
	public static boolean fechaDisponible(LocalDate fechaIngresada, ReservaDaoImp reservaService, Salon salonAReservar) throws SalonNoDisponibleException {
		
		List<Reserva> reservas = reservaService.listarReservas();
		for (Reserva reserva : reservas) {
			
            if (reserva.getFecha().isEqual(fechaIngresada) && reserva.getSalon().equals(salonAReservar) && reserva.isEstado()) {

            	throw new SalonNoDisponibleException("Salon no disponible para la fecha ingresada. Ingrese otra:  ");
            }
        } 
		
		return true;
		
	}
	
	
	
	public static boolean comprobarFechaFutura(LocalDate fechaIngresada) throws FechaPasadaException {
		
	            LocalDate fechaActual = LocalDate.now();

	            if (fechaIngresada.isAfter(fechaActual)) {
	                return true;
	            } else if (fechaIngresada.isEqual(fechaActual)) {
	                return true;
	            } else {
	                throw new FechaPasadaException("La fecha debe ser una fecha futura: ");
	            }

	    }
	
	public static LocalDate transformarFecha(String fechaEntrada) {
		
			
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate fechaIngresada = LocalDate.parse(fechaEntrada, formato);
	
	        return fechaIngresada;
	        

	}
	
	public static boolean comprobarHoraEnRango(LocalTime horaIngresada) throws HoraFueraDeRangoException {
		
		boolean comprobacion = false;
            LocalTime horaInicio = LocalTime.of(10, 0); // 10:00
            LocalTime horaFin = LocalTime.of(23, 0);    // 23:00

            comprobacion = !horaIngresada.isBefore(horaInicio) && !horaIngresada.isAfter(horaFin);
            if (comprobacion) {
            	return true;
            }else {
            	throw new HoraFueraDeRangoException("La hora debe estar dentro del rango 10:00 - 23:00 : ");
            }
            
            //return !horaIngresada.isBefore(horaInicio) && !horaIngresada.isAfter(horaFin);
        
    }
	
	public static LocalTime transformarHora(String horaEntrada) {
	

			DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime horaIngresada = LocalTime.parse(horaEntrada, formato);
            return horaIngresada;

	}
	
	public static boolean validarMontoAdelantado(double pagoAdelantado, Reserva reserva) throws MontoNoValidoException {
		/*
		 * double montoFinal = 0;
		 * 
		 * 
		 * if (!servicios.isEmpty()) { for (ServicioAdicional servicio : servicios) {
		 * montoFinal += servicio.getPrecio(); } }
		 */
		 
		
		if (pagoAdelantado < reserva.calcularMontoTotal()) {
			return true;
		}else {
			throw new MontoNoValidoException("El monto debe ser menor al precio total: ");
		}
	}
	
	public static void cargarSalones(SalonDaoImp salonService) {
		salonService.guardarSalon(new Salon("Salon Cosmos", 60, false, 70000.0));
        salonService.guardarSalon(new Salon("Salon Esmeralda", 20, false, 80000.0));
        salonService.guardarSalon(new Salon("Salon Galaxy", 100, true, 90000.0));
	}
	
	public static void cargarServiciosAdicionales(ServicioAdicionalDaoImp servicioService) {
		
		servicioService.guardarServicio(new ServicioAdicional("Cámara 360", 15000.0, true));
        servicioService.guardarServicio(new ServicioAdicional("Cabina de fotos", 20000.0, true));
        servicioService.guardarServicio(new ServicioAdicional("Filmación", 30000.0, true));
        servicioService.guardarServicio(new ServicioAdicional("Pintacaritas", 10000.0, true));

	}
}


	





    