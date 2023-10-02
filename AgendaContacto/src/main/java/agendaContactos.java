import java.util.Scanner;

public class agendaContactos{

	public static void main(String[] args) {
		
		System.out.println("Agenda de contactos\n"
				+ "1. Buscar por código de usuario (UUID)\n2. Buscar por nombre\n3. Mostrar la agenda completa\n4. Añadir un contacto\n5. Borrar un contacto");
		System.out.print("Introduzca el número de la acción que quiere realizar: ");
		Scanner sc = new Scanner(System.in);
		int opcion = sc.nextInt();
	}

}
