import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.UUID;

public class agendaContactos{
	
	private static String ruta = "./FicheroAgenda/Agenda.json";
	
	public static void main(String[] args) {
		boolean agendaAbierta = true;
		Scanner sc = new Scanner(System.in);
		while(agendaAbierta) {
			System.out.println("Agenda de contactos\n"
					+ "1. Buscar por código de usuario (UUID)\n2. Buscar por nombre\n3. Mostrar la agenda completa\n4. Añadir un contacto\n5. Borrar un contacto\n6. Salir de la agenda");
			System.out.print("Introduzca el número de la acción que quiere realizar: ");
			int option = sc.nextInt();
			System.out.println("-------------------------");
			switch(option) {
			case 1:
				buscarCod(null);
				break;
			case 2: 
				buscarNombre(null);
				break;
			case 3:
				mostrarLista();
				break;
			case 4:
				addContacto(null, null, option);
				break;
			case 5:
				borrarContacto(null);
				break;
			case 6:
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
	
	public static Contacto buscarCod(UUID id) {
		System.out.println("Opción buscar por código");
		return null;
	}
	public static Contacto buscarNombre(String nombre) {
		System.out.println("Opción buscar por nombre");
		return null;
	}
	public static void addContacto(String nombre, String telefono, int edad) {
//		try {
//			RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
//			try {
//				raf.writeChars("[\n");
//				raf.writeChars("\t{\n");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		System.out.println("Opción añadir contacto");
	}
	public static void borrarContacto(UUID id) {
		System.out.println("Opción borrar contacto");
	}
	public static void mostrarLista() {
		System.out.println("Opción mostrar lista");
	}
	
}