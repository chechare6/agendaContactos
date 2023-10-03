import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.UUID;

public class agendaContactos {

	private static String ruta = "./FicheroAgenda/Agenda.csv";

	public static void main(String[] args) {
		boolean agendaAbierta = true;
		Scanner sc = new Scanner(System.in);
		while (agendaAbierta) {
			System.out.println("Agenda de contactos\n"
					+ "1. Buscar por código de usuario (UUID)\n2. Buscar por nombre\n3. Mostrar la agenda completa\n4. Añadir un contacto\n5. Borrar un contacto\n6. Salir de la agenda");
			System.out.print("Introduzca el número de la acción que quiere realizar: ");
			int option = sc.nextInt();
			sc.nextLine();
			System.out.println("-------------------------");
			switch (option) {
			case 1:
				if(existeAgenda(ruta)) {
					System.out.print("Escribe la UUID del contacto: ");
					String id = sc.nextLine();
					System.out.println(buscarCod(ruta, id));				
				} else {
					System.out.println("No existen datos en la agenda.");
				}
				break;
			case 2:
				buscarNombre(null);
				break;
			case 3:
				mostrarLista();
				break;
			case 4:
				System.out.print("Nombre del nuevo contacto: ");
				String nombre = sc.nextLine();
				System.out.print("Teléfono del nuevo contacto: ");
				String tlf = sc.nextLine();
				System.out.print("Edad del nuevo contacto: ");
				int edad = sc.nextInt();
				sc.nextLine();
				addContacto(nombre, tlf, edad);
				break;
			case 5:
				borrarContacto(null);
				break;
			case 6:
				System.out.println("Agenda cerrada");
				agendaAbierta = false;
				break;
			default:
				System.out.println("Opción no permitida");
				break;
			}
			System.out.println("-------------------------");
		}
		sc.close();
	}

	public static boolean existeAgenda(String ruta) {
		File agenda = new File(ruta);
		return agenda.exists();
	}
	
	public static String buscarCod(String ruta, String id) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(ruta));
			String datos;
			while((datos = br.readLine()) != null) {
				String[] contactos = datos.split(";");
				if(contactos[0] == id) {					
					return new Contacto(UUID.fromString(contactos[0]), contactos[1], contactos[2], Integer.parseInt(contactos[3])).toString();
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	public static Contacto buscarNombre(String nombre) {
		System.out.println("Opción buscar por nombre");
		return null;
	}

	public static void addContacto(String nombre, String telefono, int edad) {
		try {
			RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
			try {
				if (raf.length() > 0) {
					raf.seek(raf.length());
					raf.writeChars("\n");
				} else {
					raf.writeChars("UUID;Nombre;Teléfono;Edad\n");
				}
				Contacto nCont = new Contacto(nombre, telefono, edad);
				raf.writeChars(String.format("%s;%s;%s;%s", nCont.getIdContacto(), nCont.getNombre(), nCont.getTelefono(), nCont.getEdad()));
				raf.close();
				System.out.println("-------------------------");
				System.out.println("Creado con éxito");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void borrarContacto(UUID id) {
		System.out.println("Opción borrar contacto");
	}

	public static void mostrarLista() {
		System.out.println("Opción mostrar lista");
	}

}