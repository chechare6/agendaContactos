import java.lang.reflect.Constructor;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
public class Contacto {

	private String nombre, telefono;
	private int edad;
	private UUID idContacto;

	public Contacto(String nombre, String telefono, int edad) {
		this.idContacto = UUID.randomUUID();
		this.nombre = nombre;
		this.telefono = telefono;
		this.edad = edad;
	}

	public Contacto(UUID idContacto, String nombre, String telefono, int edad) {
		this.idContacto = idContacto;
		this.nombre = nombre;
		this.telefono = telefono;
		this.edad = edad;
	}
	
}
