package tocadoHundido;

import java.awt.Point;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;

public class Principal {
	
	public static void main(String[] args) {
		
		
		reiniciarPartida();
		
	}
	
	private static void reiniciarPartida() {
		
		ArrayList<Barco> barcos = new ArrayList<Barco>();
		ArrayList<Barco> barcosCPU = new ArrayList<Barco>();
		Usuario usuario = new Usuario();
		Cpu cpu = new Cpu(0, tipoDificultad.FACIL);
		
		
		//Creo 2 listas de barcos identicas para que jueguen con los mismos barcos
		//No puedo usar la misma debido a que se copiaban las referencias, y la CPU y jugador tienen cada uno su lista
		Barco b = new Barco(TipoBarco.PORTAVION, 5, false, 0, new ArrayList<Casilla>(), new ArrayList<Point>());
		barcos.add(b);
		Barco b2 = new Barco(TipoBarco.FRAGATA, 4, false, 0, new ArrayList<Casilla>(), new ArrayList<Point>());
		barcos.add(b2);
		Barco b3 = new Barco(TipoBarco.SUBMARINO, 3, false, 0,  new ArrayList<Casilla>(), new ArrayList<Point>());
		barcos.add(b3);
		Barco b4 = new Barco(TipoBarco.SUBMARINO, 3, false, 0, new ArrayList<Casilla>(), new ArrayList<Point>());
		barcos.add(b4);
		Barco b5 = new Barco(TipoBarco.DESTRUCTOR, 2, false, 0, new ArrayList<Casilla>(), new ArrayList<Point>());
		barcos.add(b5);
		
		Barco bc = new Barco(TipoBarco.PORTAVION, 5, false, 0, new ArrayList<Casilla>(), new ArrayList<Point>());
		barcosCPU.add(bc);
		Barco bc2 = new Barco(TipoBarco.FRAGATA, 4, false, 0, new ArrayList<Casilla>(), new ArrayList<Point>());
		barcosCPU.add(bc2);
		Barco bc3 = new Barco(TipoBarco.SUBMARINO, 3, false, 0,  new ArrayList<Casilla>(), new ArrayList<Point>());
		barcosCPU.add(bc3);
		Barco bc4 = new Barco(TipoBarco.SUBMARINO, 3, false, 0, new ArrayList<Casilla>(), new ArrayList<Point>());
		barcosCPU.add(bc4);
		Barco bc5 = new Barco(TipoBarco.DESTRUCTOR, 2, false, 0, new ArrayList<Casilla>(), new ArrayList<Point>());
		barcosCPU.add(bc5);
		
		
		
		
		VentanaGrafica v = new VentanaGrafica(usuario, cpu, barcos, barcosCPU);

	}
}
