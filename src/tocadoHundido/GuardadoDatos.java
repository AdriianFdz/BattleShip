package tocadoHundido;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class GuardadoDatos implements Serializable{
	protected Usuario usuario;
	protected Cpu cpu;
	public GuardadoDatos(Usuario usuario, Cpu cpu) {
		super();
		this.usuario = usuario;
		this.cpu = cpu;
	}
	
	public GuardadoDatos() {
		super();
		this.usuario = new Usuario();
		this.cpu = new Cpu();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cpu getCpu() {
		return cpu;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}
	
	
	
	
	
	public void guardarPartida() {
		try {
			String filename = JOptionPane.showInputDialog("Introduce el nombre del fichero");
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.usuario);
			System.out.println(this.usuario);
			oos.writeObject(this.cpu);
			System.out.println(this.cpu);

		} catch (FileNotFoundException e) {
			System.out.println("El fichero no existe");
		} catch (IOException e) {
			System.out.println("Error en la salida de datos");
		}

	}
	
	public void cargarPartida() {
		try {
			String filename = JOptionPane.showInputDialog("Introduce el nombre del fichero");
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.usuario = (Usuario) ois.readObject();
			System.out.println(this.usuario);
			this.cpu = (Cpu) ois.readObject();
			System.out.println(this.cpu);

		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			System.out.println("Error en la entrada de datos");
		} catch (ClassNotFoundException e) {
			System.out.println("Error al convertir los datos");
		}
	}

	@Override
	public String toString() {
		return "GuardadoDatos [" + usuario + cpu + "]";
	}
	
	
	
	
}
