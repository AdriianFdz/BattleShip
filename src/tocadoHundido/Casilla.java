package tocadoHundido;

import java.awt.Point;

import javax.swing.JButton;

public class Casilla {
	protected JButton boton;
	protected Point coordenada;
	protected Boolean ocupada;
	protected Barco barcoAsignado;
	protected Boolean fuePresionado;

	
	public Casilla(JButton boton, Point coordenada, Boolean ocupada, Boolean fuePresionado) {
		super();
		this.boton = boton;
		this.coordenada = coordenada;
		this.ocupada = ocupada;
		this.barcoAsignado = null;
		this.fuePresionado = false;
	}
	
	
	
	
	
	public JButton getBoton() {
		return boton;
	}

	public void setBoton(JButton boton) {
		this.boton = boton;
	}

	public Point getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Point coordenada) {
		this.coordenada = coordenada;
	}


	public Boolean getOcupada() {
		return ocupada;
	}


	public void setOcupada(Boolean ocupada) {
		this.ocupada = ocupada;
	}


	public Barco getBarcoAsignado() {
		return barcoAsignado;
	}

	public void setBarcoAsignado(Barco barcoAsignado) {
		this.barcoAsignado = barcoAsignado;
	}

	public Boolean getFuePresionado() {
		return fuePresionado;
	}

	public void setFuePresionado(Boolean fuePresionado) {
		this.fuePresionado = fuePresionado;
	}

	@Override
	public String toString() {
		return "Casilla [coordenada=" + coordenada + ", ocupada=" + ocupada + ", barcoAsignado=" + barcoAsignado + "]";
	}






	
	
}
