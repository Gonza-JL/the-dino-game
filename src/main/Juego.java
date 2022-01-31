package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Juego extends JPanel {

	private JFrame ventana;
	protected static int ANCHO = 1280;
	protected static int ALTO = 600;
	protected static int velocidadCam;

	private boolean juegoTerminado;
	private int puntaje;
	private Integer record;
	private Dino dino;
	private Mapa mapa;
	private Image msjReiniciar, cartel;

	public Juego() {
		iniciarVentana();
		inicializarVariables();
	}

	private void iniciarVentana() {
		ventana = new JFrame("The Dino Game");
		ventana.setSize(ANCHO, ALTO);
		ventana.setLocationRelativeTo(null);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
		ventana.setResizable(false);

		ventana.add(this);
		KeyAdapter teclas = new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (dino.estaEnElSuelo(mapa.getSuelo())) {
						reproducirSonido("data/sonido de salto.wav");
						dino.setSaltando(true);
					}
					if (juegoTerminado) {
						inicializarVariables();
					}
				}
			}

		};
		ventana.addKeyListener(teclas);
	}

	private void inicializarVariables() {
		velocidadCam = 9;
		juegoTerminado = false;
		puntaje = 0;
		msjReiniciar = new ImageIcon("data/icono de reiniciar.png").getImage();
		cartel = new ImageIcon("data/cartel de game over.png").getImage();
		mapa = new Mapa(0, 0, ANCHO, ALTO);
		dino = new Dino(ANCHO - 1180, ALTO - 200, ANCHO - 1180, ALTO - 500);
	}

	public void actualizar() {
		this.repaint();

		if (!juegoTerminado) {
			puntaje++;
			mapa.actualizar(puntaje);
			dino.actualizar(mapa.getSuelo(), puntaje / 10);
			dino.saltar();
			dino.caer(mapa.getSuelo());

			// Simular el aumento de la velocidad del dino
			if (puntaje % 500 == 0 && puntaje / 3 > 0) {
				Juego.velocidadCam++;
			}

			// Parar el juego si el dino chocó
			for (int i = 0; i < mapa.getObstaculos().size(); i++) {
				if (dino.choco(mapa.getObstaculos().get(i))) {
					reproducirSonido("data/sonido de derrota.wav");
					dino.perder();
					juegoTerminado = true;
				}
			}
		} else {
			// Actualizar record
			if (record == null || puntaje / 3 >= record) {
				record = puntaje / 3;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mapa != null)
			mapa.dibujar(g);
		if (dino != null)
			dino.dibujar(g);
		if (juegoTerminado)
			mensajeGameOver(g);
		mostrarPuntaje(g);
	}

	private void mostrarPuntaje(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Pixel Emulator", 0, 20));
		g.drawString("Puntaje: " + puntaje / 3, ANCHO - 220, ALTO - 575);
		if (record != null) {
			g.setColor(Color.BLACK);
			g.drawString("Record: " + record, ANCHO - 420, ALTO - 575);
		}
	}

	private void mensajeGameOver(Graphics g) {
		g.drawImage(cartel, ANCHO / 4 - 75, ALTO / 4, ANCHO - 465, 275, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Pixel Emulator", 0, 25));
		g.drawString("Fin del juego", ANCHO / 2 - 120, ALTO / 3);
		g.setFont(new Font("Pixel Emulator", 0, 20));
		g.drawString("Si desea salir del juego presione la tecla ESC", ANCHO / 4 - 20, ALTO / 3 + 50);
		g.drawString("Y si desea jugar de nuevo presione la tecla ESPACIO", ANCHO / 4 - 55, ALTO / 3 + 75);
		g.drawImage(msjReiniciar, ANCHO / 2 - 50, ALTO / 2, 100, 100, null);
	}

	private void reproducirSonido(String nombreSonido) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("Error al reproducir el sonido");
		}
	}

	public static void main(String[] args) {
		Juego juego = new Juego();

		while (true) {
			juego.actualizar();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
