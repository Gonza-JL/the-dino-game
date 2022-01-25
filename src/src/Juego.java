package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Juego extends JPanel {
	
	private JFrame ventana;
	protected static int ANCHO = 1280;
	protected static int ALTO = 600;
	
	private boolean juegoTerminado;
	private int puntaje;
	private Dino dino;
	private List<Obstaculo> obstaculos;
	private Rectangle suelo;
	private Fondo fondo;
	
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
					if(dino.estaEnElSuelo(suelo)) {
						dino.setSaltando(true);
					}
				}
			}
			
		};
		ventana.addKeyListener(teclas);
	}
	
	private void inicializarVariables() {
		juegoTerminado = false;
		puntaje = 0;
		
		fondo = new Fondo(0, 0, ANCHO, ALTO);
		
		dino = new Dino(ANCHO - 1180, ALTO - 200, ANCHO - 1180, ALTO - 500);
		
		obstaculos = new ArrayList<>();
		Obstaculo o1 = new Obstaculo(ANCHO, ALTO - 200, ANCHO - 1220, ALTO - 500);
		Obstaculo o2 = new Obstaculo(ANCHO + 100, ALTO - 150, ANCHO - 1250, ALTO - 550);
		o2.setXRandom(true);
		obstaculos.add(o1);
		obstaculos.add(o2);
		
		suelo = new Rectangle(-1, ALTO - 100, ANCHO, ALTO - 500);
	}
	
	public void actualizar() {
		this.repaint();
		
		puntaje++;
		
		dino.actualizar(suelo, puntaje/10);
		dino.saltar();
		dino.caer(suelo);
		
		for(int i = 0; i < obstaculos.size(); i++) {
			obstaculos.get(i).mover();
		}
		
		for(int i = 0; i < obstaculos.size(); i++) {
			if(dino.choco(obstaculos.get(i))) {
				dino.perder();
				juegoTerminado = true;
			}
		}
		
		for(int i = 0; i < obstaculos.size(); i++) {
			if(puntaje % 500 == 0 && puntaje/3 > 0) {
				obstaculos.get(i).setVelocidad(obstaculos.get(1).getVelocidad() + 1);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(fondo != null) {
			g.drawImage(fondo.getImagen(), fondo.getX(), fondo.getY(), fondo.getAncho(), fondo.getAlto(), null);
			g.drawImage(fondo.getNube(), 100, 100, 125, 50, null);
			g.drawImage(fondo.getNube(), 500, 80, 80, 25, null);
			g.drawImage(fondo.getNube(), 800, 200, 100, 40, null);
			g.drawImage(fondo.getNube(), 1100, 150, 80, 25, null);
		}
		
		if(dino != null) {
			g.drawImage(dino.getImagen(), dino.getX(), dino.getY(), dino.getAncho(), dino.getAlto(), null);
		}
		
		if(suelo != null) {
			g.setColor(Color.BLACK);
			g.drawRect(suelo.x, suelo.y, suelo.width, suelo.height);
		}
		
		if(obstaculos != null) {
			for(int i = 0; i < obstaculos.size(); i++) {
				g.setColor(Color.RED);
				g.drawImage(obstaculos.get(i).getImagen(), obstaculos.get(i).getX(), obstaculos.get(i).getY(), 
							obstaculos.get(i).getAncho(), obstaculos.get(i).getAlto(), null);	
			}
		}
		
		// Mostrar puntaje
		g.setColor(Color.BLACK);
		g.setFont(new Font("Pixel Emulator", 0, 25));
		g.drawString("" + puntaje/3, ANCHO - 110, ALTO - 575);
	}
	
	public static void main(String[] args) {
		Juego juego = new Juego();
		
		while(!juego.juegoTerminado) {
			juego.actualizar();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}
