package tocadoHundido;

import java.io.Serializable;

public abstract class Jugador implements Serializable{
	protected int ganadas;
	
	public Jugador(int ganadas) {
		super();
		this.ganadas = ganadas;
	}
	public Jugador() {
		super();
		this.ganadas = 0;
	}
	public int getGanadas() {
		return ganadas;
	}
	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}
	@Override
	public String toString() {
		return "Usuario [ganadas=" + ganadas + "]";
	}
	
	
	
}
