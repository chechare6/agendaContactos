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
				if (existeAgenda(ruta)) {
					System.out.print("Escribe la UUID del contacto: ");
					String id = sc.nextLine();
					System.out.println(buscarCod(ruta, id));
				} else {
					System.out.println("No existen datos en la agenda.");
				}
				break;
			case 2:
				if (existeAgenda(ruta)) {
					System.out.print("Escribe el nombre del contacto (o sus primeras letras): ");
					String nombre = sc.nextLine();
					System.out.println(buscarNombre(ruta, nombre));
				} else {
					System.out.println("No existen datos en la agenda.");
				}
				break;
			case 3:
				if (existeAgenda(ruta)) {
					mostrarAgenda(ruta);
				} else {
					System.out.println("No existen datos en la agenda");
				}
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
				if (existeAgenda(ruta)) {
					System.out.print("Escribe la UUID del contacto a eliminar: ");
					String id = sc.nextLine();
					System.out.println(borrarContacto(ruta, id));
				} else {
					System.out.println("No existen datos en la agenda");
				}
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
			br.readLine(); // Lee la cabecera para no volver a leerla después
			while ((datos = br.readLine()) != null) {
				String[] contactos = datos.split(";");
				for (int i = 0; i < contactos.length; i++) {
					if (contactos[i].contains("00000000-0000-0000-0000-000000000000"))
						continue;
					if (contactos[i].equals(id)) {
						return contactos[i] + " | " + contactos[i + 1] + " | " + contactos[i + 2] + " | "
								+ contactos[i + 3];
					}
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "No se ha encontrado al usuario con UUID: " + id;
	}

	public static String buscarNombre(String ruta, String nombre) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(ruta));
			String datos, persona = null;
			br.readLine(); // Lee la cabecera para no volver a leerla después
			while ((datos = br.readLine()) != null) {
				String[] contactos = datos.split(";");
				for (int i = 1; i < contactos.length; i++) {
					if (contactos[i - 1].contains("00000000-0000-0000-0000-000000000000"))
						continue;
					if (contactos[i].toLowerCase().contains(nombre.toLowerCase())) {
						persona = contactos[i - 1] + " | " + contactos[i] + " | " + contactos[i + 1] + " | "
								+ contactos[i + 2];
						System.out.println(persona);
					}
				}
			}
			if (persona != null) {
				return "No hay más contactos con nombre: " + nombre;
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "No se ha encontrado al contacto con nombre (o que contenga): " + nombre;
	}

	public static void addContacto(String nombre, String telefono, int edad) {
		try {
			RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
			try {
				if (raf.length() > 0) {
					raf.seek(raf.length());
					raf.writeBytes("\n");
				} else {
					raf.writeBytes("UUID;Nombre;Telefono;Edad\n");
				}
				Contacto nCont = new Contacto(nombre, telefono, edad);
				raf.writeBytes(String.format("%s;%s;%s;%s", nCont.getIdContacto(), nCont.getNombre(),
						nCont.getTelefono(), nCont.getEdad()));
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

	// METODO LO PONE COMO 0000000000000000000000000 PERO NO PONE BORRADO
	public static String borrarContacto(String ruta, String id) {
		String idBorrada = "00000000-0000-0000-0000-000000000000";
		try {
			RandomAccessFile raf = new RandomAccessFile(ruta, "rw");
			String datos = raf.readLine();
			long pos = 0;
			while ((datos = raf.readLine()) != null) {
				String[] contactos = datos.split(";");
				String idABorrar = contactos[0].trim();
				if (idABorrar.equals(id)) {
					raf.seek(pos);
					String nuevaLinea = idBorrada + ";" + contactos[1] + ";" + contactos[2] + ";"
							+ contactos[3];
					raf.writeBytes(nuevaLinea);
					return "Contacto con UUID: " + id + " borrado.";
				}
				pos = raf.getFilePointer();
			}
			return "UUID no encontrada";
		} catch (Exception e) {
			e.getMessage();
		}
		return "No se ha encontrado un contacto con UUID: " + id;
	}

	//METODO CREA ARCHIVO TEMPORAL PERO NO LO SUSTITUYE EN LA AGENDA (NO ES FUNCIONAL)
	public static String borrarContacto2(String ruta, String id) {
		String idBorrada = "00000000-0000-0000-0000-000000000000";
		boolean idExiste = false;
		try {
			RandomAccessFile rafOriginal = new RandomAccessFile(ruta, "rw");
			RandomAccessFile rafTemp = new RandomAccessFile("./FicheroAgenda/Temporal.csv", "rw");
			String datos = rafOriginal.readLine();
			rafTemp.writeBytes(datos + "\n"); // Escribe Cabecera
			while ((datos = rafOriginal.readLine()) != null) {
				String[] contactos = datos.split(";");
				String idABorrar = contactos[0].trim();
				if (idABorrar.equals(id)) {
					String nuevaLinea = "borrado -> " + idBorrada + ";" + contactos[1] + ";" + contactos[2] + ";"
							+ contactos[3] + "\n";
					rafTemp.writeBytes(nuevaLinea);
					idExiste = true;
				} else {
					rafTemp.writeBytes(
							contactos[0] + ";" + contactos[1] + ";" + contactos[2] + ";" + contactos[3] + "\n");
				}
			}
			if (idExiste) {
				try {
					File archivoBorrar = new File(ruta);
					if(archivoBorrar.exists() && archivoBorrar.delete()) {
						File archivoRenombrar = new File("./FicheroAgenda/Temporal.csv");
						if(archivoRenombrar.exists()) {
							File nuevoFichero = new File(ruta);
							archivoRenombrar.renameTo(nuevoFichero);
							return "Archivo Temporal creado";
						}
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return "No se ha podido borrar/crear temporal";
	}

	public static void mostrarAgenda(String ruta) {
		try {
			BufferedReader bfr = new BufferedReader(new FileReader(ruta));
			String linea;
			while ((linea = bfr.readLine()) != null) {
				String[] columnas = linea.split(";");
				for (String columna : columnas) {
					System.out.print(columna + " | ");
				}
				System.out.println();

			}
		} catch (IOException e) {
			System.err.println("ERROR");
		}

	}

};