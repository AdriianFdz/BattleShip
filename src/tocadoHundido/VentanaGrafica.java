package tocadoHundido;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class VentanaGrafica extends JFrame{
	
	protected Casilla casilla;
	protected Casilla casillaCPU;
	
	protected JTextField cajaNombre;
	protected JComboBox<tipoDificultad> dificultades;
	protected JPanel panelNombre;
	protected JPanel panelConjunto;
	
	protected ArrayList<ArrayList<Casilla>> botones;
	protected ArrayList<ArrayList<Casilla>> botonesCPU;
	
	
	protected ArrayList<Barco> listaBarcos;
	protected ArrayList<Barco> listaBarcosCPU;
	
	protected Direccion selecDireccion;
	
	protected JButton horizontal = new JButton("Horizontal");
	protected JButton vertical = new JButton("Vertical");
	
	protected JButton jugar = new JButton("Jugar");
	
	protected JPanel arriba = new JPanel();
	protected JPanel abajo = new JPanel();
	protected JPanel grid = new JPanel();
	protected JPanel panelBotones = new JPanel();
	protected JPanel panelDificultad = new JPanel();
	
	protected JPanel numeroBarcos = new JPanel();
	
	protected JLabel numeroSubmarino = new JLabel();
	protected JLabel numeroDestructor = new JLabel();
	protected JLabel numeroFragata = new JLabel();
	protected JLabel numeroPortavion = new JLabel();

	protected JLabel submarino = new JLabel();
	protected JLabel destructor = new JLabel();
	protected JLabel fragata = new JLabel();
	protected JLabel portavion = new JLabel();
	
	
	protected int contador;
	protected int contadorCPU;
	protected int dirRandom;
	
	protected Boolean colocFinalizada;
	
	protected ArrayList<Point> coordsCheck;
	protected int cont;
	protected int contDestCPU;
	protected Boolean hayGanador;
	
	
	protected Usuario usuario;
	protected Cpu cpu;
	protected GuardadoDatos guardadoDatos;
	
	protected JButton nuevaPartida = new JButton("Nueva partida");
	protected JButton guardarPartida = new JButton("Guardar partida");
	protected JButton cargarPartida = new JButton("Cargar partida");
	protected JButton empezarPartida = new JButton("Empezar partida");
	protected JLabel resultado = new JLabel();
	protected JLabel ganador = new JLabel();
	protected JLabel jugadaCPU = new JLabel(" ", SwingConstants.CENTER);
	
	protected ArrayList<Point> coordsCandidatas = new ArrayList<Point>();
	protected Boolean rodeando = false;
	protected int cordXTemp;
	protected int cordYTemp;
	
	protected int numero;
	
	public VentanaGrafica(Usuario usuarioP, Cpu cpuP, ArrayList<Barco> barcos, ArrayList<Barco> barcoscpu) {
		this.usuario = usuarioP;
		this.cpu = cpuP;
		
		
		dificultades = new JComboBox<tipoDificultad>(tipoDificultad.values());
		panelConjunto = new JPanel();
		panelConjunto.setLayout(new GridLayout(2,1));
		panelNombre = new JPanel();
		
		
		JLabel nombre = new JLabel("Nombre: ");
		cajaNombre = new JTextField(10);
		panelNombre.add(nombre);
		panelNombre.add(cajaNombre);
		
		panelConjunto.setLayout(new BorderLayout());
		
		panelBotones.add(empezarPartida);
		panelBotones.add(cargarPartida);
		
		panelDificultad.setLayout(new GridLayout(2,1));
		JLabel textoDificultad = new JLabel("Seleccione una dificultad");
		panelDificultad.add(textoDificultad);
		panelDificultad.add(dificultades);
		
		panelConjunto.add(panelNombre, BorderLayout.NORTH);
		panelConjunto.add(panelDificultad, BorderLayout.CENTER);
		
		panelConjunto.add(panelBotones, BorderLayout.SOUTH);
		
		this.add(panelConjunto, BorderLayout.CENTER);
		
		
		if (cargarPartida.getActionListeners().length == 0) {													
			cargarPartida.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					guardadoDatos = new GuardadoDatos(usuario, cpu);												
					guardadoDatos.cargarPartida();												
					usuario = guardadoDatos.usuario;
					cpu = guardadoDatos.cpu;
					remove(panelConjunto);
					createGrid();
					colocarBarcos(barcos);
					atacar(barcoscpu);	
				}
			});								
		}
		
		
		if (empezarPartida.getActionListeners().length == 0) {
			empezarPartida.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (cajaNombre.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio", "Error al introducir el nombre", JOptionPane.ERROR_MESSAGE);
					} else {		
						repaint();
						usuario.setNombre(cajaNombre.getText());
						remove(panelConjunto);
						createGrid();
						colocarBarcos(barcos);
						atacar(barcoscpu);	
					}
					
				}
			});
		}
		
		
		setTitle("BATTLESHIP");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		this.pack();
		
	}
	
	public void createGrid() {	

		
		botones = new ArrayList<ArrayList<Casilla>>();
		botonesCPU = new ArrayList<ArrayList<Casilla>>();
		
	    grid.setLayout(new GridLayout(10,10));   
	    
	    abajo.add(horizontal);
	    abajo.add(vertical);

	    for (int i = 0; i < 10; i++) {
	    	ArrayList<Casilla> botonesTemp = new ArrayList<Casilla>();
	    	ArrayList<Casilla> botonesTempCPU = new ArrayList<Casilla>();
	    	
	        for (int j = 0; j < 10; j++) {
	        	//BOTONES CPU
	        	JButton cajaCPU = new JButton();
	        	casillaCPU = new Casilla(cajaCPU, new Point(i,j), false, false);
	        	botonesTempCPU.add(casillaCPU);
	        	//BOTONES JUGADOR
	            JButton caja = new JButton();
	            caja.setText("" + i + j);
	            caja.setBackground(Color.GRAY);
	            grid.add(caja);
	            casilla = new Casilla(caja, new Point(i, j), false, false);
	            botonesTemp.add(casilla);
	        }   
	        botones.add(botonesTemp);
	        botonesCPU.add(botonesTempCPU);
	    }
	    
	    arriba.add(grid);

	    add(arriba, BorderLayout.NORTH);
	    add(abajo, BorderLayout.SOUTH);
	    setSize(600,400);
	}

	
	public void colocarBarcos(ArrayList<Barco> barcos) {
		
		HashMap<TipoBarco, Integer> contarBarcos = new HashMap<TipoBarco, Integer>();
		
		contador = -1;
		colocFinalizada = false;
		this.listaBarcos = new ArrayList<Barco>(); //COPIA RECURSIVA
		for (Barco barco : barcos) {
			this.listaBarcos.add(barco);
		}
		
		//CONTAR BARCOS CON MAPAS
		for (Barco barco : listaBarcos) {
			if (!contarBarcos.containsKey(barco.getTipo())) {
				contarBarcos.put(barco.getTipo(), 1);
			} else {
				contarBarcos.put(barco.getTipo(), contarBarcos.get(barco.getTipo())+1);
			}
		}
		numeroPortavion.setText(""+contarBarcos.get(TipoBarco.PORTAVION));
		numeroFragata.setText(""+contarBarcos.get(TipoBarco.FRAGATA));
		numeroSubmarino.setText(""+contarBarcos.get(TipoBarco.SUBMARINO));
		numeroDestructor.setText(""+contarBarcos.get(TipoBarco.DESTRUCTOR));
	
		portavion.setText("Portavion: ");
		fragata.setText("Fragata: ");
		submarino.setText("Submarino: ");
		destructor.setText("Destructor: ");
		
		
		
		numeroBarcos.setLayout(new GridLayout(4,2));
		numeroBarcos.add(portavion);
		numeroBarcos.add(numeroPortavion);
		numeroBarcos.add(fragata);
		numeroBarcos.add(numeroFragata);
		numeroBarcos.add(submarino);
		numeroBarcos.add(numeroSubmarino);
		numeroBarcos.add(destructor);
		numeroBarcos.add(numeroDestructor);
		add(numeroBarcos, BorderLayout.WEST);
		
		if (contador!=listaBarcos.size()-1) {
			for (ArrayList<Casilla> botonTemp : botones) {
				for (Casilla boton: botonTemp) {
					if (boton.boton.getActionListeners().length == 0) {			
						boton.boton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if (!colocFinalizada) {
									if (boton.ocupada) {
										JOptionPane.showMessageDialog(null, "CASILLA OCUPADA, elija otro sitio", "Error", JOptionPane.ERROR_MESSAGE);
									} else {
										if (selecDireccion == Direccion.HORIZONTAL) {
											contador++;
											boolean ocupado = false;
											for (int selector = (int)boton.coordenada.getY(); selector < boton.coordenada.getY()+listaBarcos.get(contador).tamanyo; selector++) {
												try {												
													if (botones.get((int) boton.coordenada.getX()).get(selector).ocupada) {
														ocupado = true;
													}
												} catch (Exception e2) {
													ocupado = true;
												}
											}
											
											if (boton.coordenada.getY() + listaBarcos.get(contador).tamanyo-1 > 9 || ocupado){
												JOptionPane.showMessageDialog(null, "CASILLA OCUPADA, no se pueden superponer los barcos", "Error", JOptionPane.ERROR_MESSAGE);
												contador--;
											} else {												
												boton.boton.setText(""+listaBarcos.get(contador).tamanyo);
												boton.boton.setBackground(Color.RED);
												
												int fila = (int) boton.coordenada.getX(); 
												int columna = (int) boton.coordenada.getY();
												
												for (int i = 0; i < listaBarcos.get(contador).tamanyo; i++) {
													botones.get(fila).get(columna+i).boton.setText("" + listaBarcos.get(contador).tamanyo);
													botones.get(fila).get(columna+i).boton.setBackground(Color.RED);
													botones.get(fila).get(columna+i).ocupada = true;
													botones.get(fila).get(columna+i).setBarcoAsignado(listaBarcos.get(contador));
													
												}
												//MOSTRAR MAPA EN PANTALLA
												switch (boton.barcoAsignado.getTipo()) {
													case PORTAVION:
														numero = Integer.parseInt(numeroPortavion.getText())-1;
														numeroPortavion.setText(""+numero);
														break;
													case FRAGATA:
														numero = Integer.parseInt(numeroFragata.getText())-1;
														numeroFragata.setText(""+numero);
														break;
													case SUBMARINO:
														numero = Integer.parseInt(numeroSubmarino.getText())-1;
														numeroSubmarino.setText(""+numero);
														break;											
													default:
														numero = Integer.parseInt(numeroDestructor.getText())-1;
														numeroDestructor.setText(""+numero);
														break;
													}
											}
											
											
											
										} else if (selecDireccion == Direccion.VERTICAL) {
											contador++;
											boolean ocupado = false;
											for (int selector = (int)boton.coordenada.getX(); selector < boton.coordenada.getX()+listaBarcos.get(contador).tamanyo; selector++) {
												try {												
													if (botones.get(selector).get((int) boton.coordenada.getY()).ocupada) {
														ocupado = true;
													}
												} catch (Exception e2) {
													ocupado = true;
												}
												
											}
											
											
											if (boton.coordenada.getX()+listaBarcos.get(contador).tamanyo-1 > 9 || ocupado) {
												JOptionPane.showMessageDialog(null, "CASILLA OCUPADA, no se pueden superponer los barcos", "Error", JOptionPane.ERROR_MESSAGE);
												contador--;
											} else {			
												boton.boton.setText(""+listaBarcos.get(contador).tamanyo);
												boton.boton.setBackground(Color.RED);
												
												int fila = (int) boton.coordenada.getX();
												int columna = (int) boton.coordenada.getY();
												
												for (int i = 0; i < listaBarcos.get(contador).tamanyo; i++) {
													botones.get(fila+i).get(columna).boton.setText("" + listaBarcos.get(contador).tamanyo);
													botones.get(fila+i).get(columna).boton.setBackground(Color.RED);
													botones.get(fila+i).get(columna).ocupada = true;
													botones.get(fila+i).get(columna).setBarcoAsignado(listaBarcos.get(contador));
													
												}	
												switch (boton.barcoAsignado.getTipo()) {
													case PORTAVION:
														numero = Integer.parseInt(numeroPortavion.getText())-1;
														numeroPortavion.setText(""+numero);
														break;
													case FRAGATA:
														numero = Integer.parseInt(numeroFragata.getText())-1;
														numeroFragata.setText(""+numero);
														break;
													case SUBMARINO:
														numero = Integer.parseInt(numeroSubmarino.getText())-1;
														numeroSubmarino.setText(""+numero);
														break;											
													default:
														numero = Integer.parseInt(numeroDestructor.getText())-1;
														numeroDestructor.setText(""+numero);
														break;
												}
											}
											
										}
										
										
										
									}
									
									
								}
								
								if (contador==listaBarcos.size()-1) {			
									add(jugar);
									colocFinalizada = true;
									
								}
							}
						});
					}

					
				}
			}
			
			if (horizontal.getActionListeners().length == 0) {				
				horizontal.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						selecDireccion = Direccion.HORIZONTAL;
					}
				});
			}

			if (vertical.getActionListeners().length == 0) {			
				vertical.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						selecDireccion = Direccion.VERTICAL;
					}
				});
			}
			
			
			
		}

		
	}
	public void atacar(ArrayList<Barco> barcos) {
		hayGanador = false;
		contadorCPU = -1;
		cont = 0;
		contDestCPU = 0;
		coordsCheck = new ArrayList<Point>();
		
		this.listaBarcosCPU = new ArrayList<Barco>(); //COPIA RECURSIVA
		for (Barco barco : barcos) {
			listaBarcosCPU.add(barco);
		}
		if (jugar.getActionListeners().length == 0) {			
			jugar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					add(jugadaCPU, BorderLayout.CENTER);
					remove(jugar);
					remove(abajo);
					remove(numeroBarcos);
					pack();
					colocFinalizada = false;
					cpu.setDificultad((tipoDificultad) dificultades.getSelectedItem());
					
					
					while (contadorCPU != listaBarcos.size()-1) {
						
						contadorCPU++;
						
						dirRandom = (int)(Math.random()*2);
						
						switch (dirRandom) {
						//HORIZONTAL
						case 0:
							int cordY = (int)(Math.random()*5);
							int cordX = (int)(Math.random()*10);
							boolean ocupadaCheckH = false;
							
							for (int i = 0; i < listaBarcosCPU.get(contadorCPU).tamanyo; i++) {
								if (botonesCPU.get(cordX).get(cordY+i).ocupada) {
									ocupadaCheckH = true;
									break;
								} else {
									ocupadaCheckH = false;
								}
							}
							
							if (!ocupadaCheckH) {							
								for (int i = 0; i < listaBarcosCPU.get(contadorCPU).tamanyo; i++) {
									botonesCPU.get(cordX).get(cordY+i).setOcupada(true);
									botonesCPU.get(cordX).get(cordY+i).setBarcoAsignado(listaBarcosCPU.get(contadorCPU));
									listaBarcosCPU.get(contadorCPU).getBotones().add(botonesCPU.get(cordX).get(cordY+i));
									ocupadaCheckH = false;
								}
							} else {
								contadorCPU--;
							}
							break;
							
						case 1: //VERTICAL
							cordY = (int)(Math.random()*10);
							cordX = (int)(Math.random()*5);
							boolean ocupadaCheckV = false;
							
							for (int i = 0; i < listaBarcosCPU.get(contadorCPU).tamanyo; i++) {
								
								if (botonesCPU.get(cordX+i).get(cordY).ocupada) {
									ocupadaCheckV = true;
									break;
								} else {
									ocupadaCheckV = false;
								}
							}	
							
							if (!ocupadaCheckV) {							
								for (int i = 0; i < listaBarcosCPU.get(contadorCPU).tamanyo; i++) {
									botonesCPU.get(cordX+i).get(cordY).setOcupada(true);
									botonesCPU.get(cordX+i).get(cordY).setBarcoAsignado(listaBarcosCPU.get(contadorCPU));
									listaBarcosCPU.get(contadorCPU).getBotones().add(botonesCPU.get(cordX+i).get(cordY));
								}
							} else {
								contadorCPU--;
							}
							break;
							
						}	
					}
					
					grid.removeAll();
					for (ArrayList<Casilla> botonTemp : botonesCPU) {
						for (Casilla boton : botonTemp) {
							boton.boton.setText(" ");
							grid.add(boton.boton);
							
						}
					}				
					for (ArrayList<Casilla> botonTemp : botonesCPU) {
						for (Casilla boton : botonTemp) {
							if (boton.boton.getActionListeners().length == 0) {	
								boton.boton.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										if (boton.ocupada) {
											if (!boton.fuePresionado) {		
												boton.setFuePresionado(true);
												boton.boton.setBackground(Color.RED);
												boton.barcoAsignado.setTocados(boton.barcoAsignado.getTocados()+1);
												boton.barcoAsignado.getCoordenadas().add(boton.getCoordenada());
												if (boton.barcoAsignado.getTocados()==boton.barcoAsignado.getTamanyo()) {
													boton.barcoAsignado.setDestruido(true);
													Thread t = new Thread(new Runnable() {
														
														@Override
														public void run() {
															try {
																AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/tocadoHundido/explosion.wav").getAbsoluteFile());
																Clip clip = AudioSystem.getClip();
																clip.open(ais);
																clip.start();					        
													        } catch (UnsupportedAudioFileException e1) {
																JOptionPane.showMessageDialog(null, "Error en el formato de audio", "Error", JOptionPane.ERROR_MESSAGE);
															} catch (IOException e1) {
																JOptionPane.showMessageDialog(null, "Error en la ruta", "Error", JOptionPane.ERROR_MESSAGE);
															} catch (LineUnavailableException e1) {
																JOptionPane.showMessageDialog(null, "Error al obtener el clip", "Error", JOptionPane.ERROR_MESSAGE);
															}
														}
													});
													t.start();
													t.interrupt();
											        

													
													
													
													for (Point coord : boton.barcoAsignado.getCoordenadas()) {
														botonesCPU.get((int)coord.getX()).get((int)coord.getY()).getBoton().setBackground(Color.GREEN);
														
													}	
													
													
												}
												
												try {
													if (boton.barcoAsignado.getDestruido()) {
														cont++;
													}																					
												} catch (Exception e2) {
												}
												
												if (listaBarcos.size() == cont) {
													usuario.setGanadas(usuario.getGanadas()+1);
													ganador.setText("Ganador: " + usuario.getNombre());
													JOptionPane.showMessageDialog(null, "Felicidades, ¡has ganado! Pulsa el boton nueva partida para volver a jugar", "Has GANADO", JOptionPane.INFORMATION_MESSAGE);
													hayGanador = true;
												}
												
												
											} else {
												JOptionPane.showMessageDialog(null, "BOTON PRESIONADO PREVIAMENTE. Turno perdido :(", "Error", JOptionPane.ERROR_MESSAGE);
											}
										} else {
											if (boton.getFuePresionado()) {
												JOptionPane.showMessageDialog(null, "BOTON PRESIONADO PREVIAMENTE. Turno perdido :(", "Error", JOptionPane.ERROR_MESSAGE);											}
											boton.setFuePresionado(true);
											boton.boton.setBackground(Color.BLUE);
										}
										
										
										
										
										//SELECCION DE LA CPU FACIL
										int cordX;
										int cordY;

										Point coordenadaCheck;
										Point coordCandidata;
																			
										if (cpu.getDificultad() == tipoDificultad.FACIL) {											
											do {
												cordY = (int)(Math.random()*10);
												cordX = (int)(Math.random()*10);
												coordenadaCheck = new Point(cordX,cordY);
												
											} while (coordsCheck.contains(coordenadaCheck));
											coordsCheck.add(coordenadaCheck);
										} else {
											if (!rodeando) {
												do {
													cordY = (int)(Math.random()*10);
													cordX = (int)(Math.random()*10);
													coordenadaCheck = new Point(cordX,cordY);
													
												} while (coordsCheck.contains(coordenadaCheck));
												coordsCheck.add(coordenadaCheck);												
											} else {
												cordX = (int) coordsCandidatas.get(coordsCandidatas.size()-1).getX();
												cordY = (int) coordsCandidatas.get(coordsCandidatas.size()-1).getY();
												coordsCandidatas.remove(coordsCandidatas.size()-1);
												coordenadaCheck = new Point(cordX,cordY);
												coordsCheck.add(coordenadaCheck);
											}
											
										}
										

										if (botones.get(cordX).get(cordY).getOcupada()) {
											botones.get(cordX).get(cordY).getBarcoAsignado().setTocados(botones.get(cordX).get(cordY).getBarcoAsignado().getTocados()+1);
											jugadaCPU.setText("Te han tocado el/la "+botones.get(cordX).get(cordY).barcoAsignado.getTipo());
											rodeando = true;
											botones.get(cordX).get(cordY).getBarcoAsignado().getCoordenadas().add(boton.getCoordenada());
											
											
											coordCandidata = new Point(cordX+1, cordY);
											if (cordX>=0 && cordX<=8 && !coordsCheck.contains(coordCandidata)) {												
												coordsCandidatas.add(coordCandidata);
											}
											coordCandidata = new Point(cordX, cordY+1);
											if (cordY>=0 && cordY<=8 && !coordsCheck.contains(coordCandidata)) {												
												coordsCandidatas.add(coordCandidata);
											}
											coordCandidata = new Point(cordX-1, cordY);
											if (cordX>=1 && cordX<=9 && !coordsCheck.contains(coordCandidata)) {												
												coordsCandidatas.add(coordCandidata);
											}
											coordCandidata = new Point(cordX, cordY-1);
											if (cordY>=1 && cordY<=8 && !coordsCheck.contains(coordCandidata)) {												
												coordsCandidatas.add(coordCandidata);
											}
																						
											if (botones.get(cordX).get(cordY).getBarcoAsignado().getTamanyo() == botones.get(cordX).get(cordY).getBarcoAsignado().getTocados()) {
												rodeando = false;
												botones.get(cordX).get(cordY).getBarcoAsignado().setDestruido(true);
												jugadaCPU.setText("Jugada CPU: Te han hundido el/la "+botones.get(cordX).get(cordY).barcoAsignado.getTipo());
												coordsCandidatas.clear();
												contDestCPU++;
												
												Thread t2 = new Thread(new Runnable() {
													
													@Override
													public void run() {
														try {
															AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/tocadoHundido/explosion.wav").getAbsoluteFile());
															Clip clip = AudioSystem.getClip();
															clip.open(ais);
															clip.start();					        
												        } catch (UnsupportedAudioFileException e1) {
															JOptionPane.showMessageDialog(null, "Error en el formato de audio", "Error", JOptionPane.ERROR_MESSAGE);
														} catch (IOException e1) {
															JOptionPane.showMessageDialog(null, "Error en la ruta", "Error", JOptionPane.ERROR_MESSAGE);
														} catch (LineUnavailableException e1) {
															JOptionPane.showMessageDialog(null, "Error al obtener el clip", "Error", JOptionPane.ERROR_MESSAGE);
														}
													}
												});
												t2.start();
												t2.interrupt();
												JOptionPane.showMessageDialog(null, "Te han hundido el/la "+botones.get(cordX).get(cordY).barcoAsignado.getTipo(), "BARCO HUNDIDO", JOptionPane.INFORMATION_MESSAGE);
												
												
												if (contDestCPU == listaBarcos.size()) {
													
													cpu.setGanadas(cpu.getGanadas()+1);
													JOptionPane.showMessageDialog(null, "Vaya, has perdido. Pulsa el boton nueva partida para volver a jugar", "Has PERDIDO", JOptionPane.ERROR_MESSAGE);
													ganador.setText("Ganador: CPU");
													hayGanador = true;
												}
												
												
											} else {												
												jugadaCPU.setText("Jugada CPU: Te han tocado el/la "+botones.get(cordX).get(cordY).barcoAsignado.getTipo());
											}
										} else {
											jugadaCPU.setText("Jugada CPU: Agua, ¡tuviste suerte!");
										}								
										
										if (hayGanador) {
											hayGanador = false;
											grid.removeAll();
											resultado.setText(usuario.getNombre() + " " + usuario.getGanadas() + " - " + cpu.getGanadas() + " CPU" );
											grid.add(ganador);
											grid.add(resultado);
											grid.add(nuevaPartida);
											grid.add(guardarPartida);
											grid.add(cargarPartida);
											setSize(200,200);
											
											
											if (guardarPartida.getActionListeners().length == 0) {	
												guardarPartida.addActionListener(new ActionListener() {
													
													@Override
													public void actionPerformed(ActionEvent e) {
														guardadoDatos = new GuardadoDatos(usuario, cpu);
														guardadoDatos.guardarPartida();
														
													}
												});
											}
											
											if (cargarPartida.getActionListeners().length == 0) {													
												cargarPartida.addActionListener(new ActionListener() {
													
													@Override
													public void actionPerformed(ActionEvent e) {
														guardadoDatos = new GuardadoDatos(usuario, cpu);												
														guardadoDatos.cargarPartida();												
														usuario = guardadoDatos.usuario;
														cpu = guardadoDatos.cpu;
													}
												});								
											}

										}							
									}
								});;	
							}
							
						}	
						
					} 
					
					
					
					
					
					
					
				}
			});
		}

		
		
		if (nuevaPartida.getActionListeners().length == 0) {			
			nuevaPartida.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Barco barco : listaBarcos) {
						barco.destruido = false;
						barco.tocados = 0;
						barco.coordenadas = new ArrayList<Point>();
						barco.botones = new ArrayList<Casilla>();
					}
					for (Barco barco : listaBarcosCPU) {
						barco.destruido = false;
						barco.tocados = 0;
						barco.coordenadas = new ArrayList<Point>();
						barco.botones = new ArrayList<Casilla>();
					}
					
					for (ArrayList<Casilla> botonesTemp : botonesCPU) {
						for (Casilla boton : botonesTemp) {
							boton.fuePresionado = false;
							boton.ocupada = false;
							boton.boton.setBackground(Color.WHITE);
							boton.barcoAsignado = null;
						}
					}
					for (ArrayList<Casilla> botonesTemp : botones) {
						for (Casilla boton : botonesTemp) {
							boton.fuePresionado = false;
							boton.ocupada = false;
							boton.boton.setBackground(Color.WHITE);
							boton.barcoAsignado = null;
						}
					}
					
					
					grid.removeAll();
					createGrid();
					colocarBarcos(listaBarcos);
					atacar(listaBarcosCPU);
				}
			});
		}
		
		
		
		
		
	}
	
}
