package tocadoHundido;

import java.io.Serializable;

public class Usuario extends Jugador implements Serializable{
	protected String nombre;

	public Usuario(String nombre, int ganadas) {
		super(ganadas);
		this.nombre = nombre;
	}
	public Usuario() {
		super();
		this.nombre = "";
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", ganadas=" + ganadas + "]";
	}

	


	
}

