import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
public class Contacto {

	private String nombre, telefono;
	private int edad;
	private UUID usuario;

	public Contacto(UUID usuario, String nombre, String telefono, int edad) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.edad = edad;
	}

}
