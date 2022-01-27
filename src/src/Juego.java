package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Juego extends JPanel {
	
	private JFrame ventana;
	protected static int ANCHO = 1280;
	protected static int ALTO = 600;
	
	private boolean juegoTerminado;
	private int puntaje;
	private Integer record;
	private Dino dino;
	private Mapa mapa;
	
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
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					if(dino.estaEnElSuelo(mapa.getSuelo())) {
						dino.setSaltando(true);
					}
					if(juegoTerminado) {
						inicializarVariables();
					}
				}
			}
			
		};
		ventana.addKeyListener(teclas);
	}
	
	private void inicializarVariables() {
		juegoTerminado = false;
		puntaje = 0;
		mapa = new Mapa(0, 0, ANCHO, ALTO);
		dino = new Dino(ANCHO - 1180, ALTO - 200, ANCHO - 1180, ALTO - 500);
	}
	
	public void actualizar() {
		this.repaint();
		
		if(!juegoTerminado) {
			puntaje++;
			mapa.actualizar();
			dino.actualizar(mapa.getSuelo(), puntaje/10);
			dino.saltar();
			dino.caer(mapa.getSuelo());
			
			// Simular movimiento del dino
			for(int i = 0; i < mapa.getObstaculos().size(); i++) {
				mapa.getObstaculos().get(i).mover();
			}
			
			// Verificar si el dino chocó
			for(int i = 0; i < mapa.getObstaculos().size(); i++) {
				if(dino.choco(mapa.getObstaculos().get(i))) {
					dino.perder();
					juegoTerminado = true;
				}
			}
			
			// Aumentar velocidad del juego
			for(int i = 0; i < mapa.getObstaculos().size(); i++) {
				if(puntaje % 500 == 0 && puntaje/3 > 0) {
					mapa.getObstaculos().get(i).setVelocidad(mapa.getObstaculos().get(1).getVelocidad() + 1);
				}
			}
		} else {
			// Actualizar record
			if(record == null || puntaje/3 >= record) {
				record = puntaje/3;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(mapa != null) {
			mapa.dibujar(g);
		}
		if(dino != null) {
			dino.dibujar(g);
		}
		mostrarPuntaje(g);
	}
	
	private void mostrarPuntaje(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Pixel Emulator", 0, 25));
		g.drawString("Puntaje: " + puntaje/3, ANCHO - 200, ALTO - 575);
		if(record != null) {
			g.drawString("Record: " + record, ANCHO - 400, ALTO - 575);
		}
	}
	
	public static void main(String[] args) {
		Juego juego = new Juego();
		
		while(true) {
			juego.actualizar();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}
