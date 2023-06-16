package tocadoHundido;

import java.io.Serializable;

public class Cpu extends Jugador implements Serializable{
	protected tipoDificultad dificultad;

	public Cpu(int ganadas, tipoDificultad dificultad) {
		super(ganadas);
		this.dificultad = dificultad;
	}
	public Cpu() {
		super();
		this.dificultad = tipoDificultad.FACIL;
	}

	public tipoDificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(tipoDificultad dificultad) {
		this.dificultad = dificultad;
	}

	@Override
	public String toString() {
		return "Cpu [dificultad=" + dificultad + ", ganadas=" + ganadas + "]";
	}
	
	
}
