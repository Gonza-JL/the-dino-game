package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
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
	private Dino dino;
	private Obstaculo obstaculo;
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
					dino.setSaltando(true);
				}
			}
			
		};
		ventana.addKeyListener(teclas);
	}
	
	private void inicializarVariables() {
		juegoTerminado = false;
		puntaje = 0;
		
		dino = new Dino(ANCHO - 1180, ALTO - 200, ANCHO - 1180, ALTO - 500);
		obstaculo = new Obstaculo(ANCHO, ALTO - 200, ANCHO - 1220, ALTO - 500);
		suelo = new Rectangle(-1, ALTO - 100, ANCHO, ALTO - 500);
		fondo = new Fondo(0, 0, ANCHO, ALTO);
	}
	
	public void actualizar() {
		this.repaint();
		
		puntaje++;
		
		dino.actualizar(suelo);
		dino.saltar();
		dino.caer(suelo);
		
		obstaculo.mover();
		
		if(dino.choco(obstaculo)) {
			juegoTerminado = true;
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(fondo != null) {
			g.drawImage(fondo.getImagen(), fondo.getX(), fondo.getY(), fondo.getAncho(), fondo.getAlto(), null);
		}
		
		if(dino != null) {
			g.drawImage(dino.getImagen(), dino.getX(), dino.getY(), dino.getAncho(), dino.getAlto(), null);
		}
		
		if(suelo != null) {
			g.setColor(Color.BLACK);
			g.drawRect(suelo.x, suelo.y, suelo.width, suelo.height);
		}
		
		if(obstaculo != null) {
			g.setColor(Color.RED);
			g.drawImage(obstaculo.getImagen(), obstaculo.getX(), obstaculo.getY(), obstaculo.getAncho(), obstaculo.getAlto(), null);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("arial", 0, 25));
		g.drawString("" + puntaje/3, ANCHO - 100, ALTO - 575);
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
