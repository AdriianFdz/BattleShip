package tocadoHundido;

import java.awt.Point;
import java.util.ArrayList;

public class Barco implements Cloneable{
	protected TipoBarco tipo;
	protected int tamanyo;
	protected Boolean destruido;
	protected int tocados;
	protected ArrayList<Casilla> botones;
	protected ArrayList<Point> coordenadas;
	
	public Barco(TipoBarco tipo, int tamanyo, Boolean destruido, int tocados, ArrayList<Casilla> botones, ArrayList<Point> coordenadas) {
		super();
		this.tipo = tipo;
		this.tamanyo = tamanyo;
		this.destruido = destruido;
		this.tocados = tocados;
		this.botones = new ArrayList<Casilla>();
		for (Casilla casilla : botones) {
			botones.add(casilla);
		}
		this.coordenadas = new ArrayList<Point>();
		for (Point point : coordenadas) {
			this.coordenadas.add(point);
		}
	}
	
	public TipoBarco getTipo() {
		return tipo;
	}

	public void setTipo(TipoBarco tipo) {
		this.tipo = tipo;
	}

	public int getTamanyo() {
		return tamanyo;
	}

	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}


	public Boolean getDestruido() {
		return destruido;
	}

	public void setDestruido(Boolean destruido) {
		this.destruido = destruido;
	}

	public int getTocados() {
		return tocados;
	}

	public void setTocados(int tocados) {
		this.tocados = tocados;
	}

	public ArrayList<Casilla> getBotones() {
		return botones;
	}
	
	public void setBotones(ArrayList<Casilla> botones) {
		this.botones = botones;
	}
	
	public ArrayList<Point> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(ArrayList<Point> coordenadas) {
		this.coordenadas = coordenadas;
	}

	@Override
	public String toString() {
		return "Barco [tipo=" + tipo + ", tamanyo=" + tamanyo + ", destruido=" + destruido + ", tocados=" + tocados
				+ "]";
	}

	
		
}
