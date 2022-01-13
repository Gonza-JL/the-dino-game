package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Juego extends JPanel {
	
	static int ANCHO = 1280;
	static int ALTO = 600;

	private Dino dino;
	private Obstaculo obstaculo;
	private Rectangle suelo;
	private Fondo fondo;
	
	public Juego() {
		JFrame ventana = new JFrame("The Dino Game");
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
		
		dino = new Dino(ANCHO - 1180, ALTO - 200, ANCHO - 1180, ALTO - 500);
		
		obstaculo = new Obstaculo(ANCHO - 980, ALTO - 200, ANCHO - 1200, ALTO - 500);
		
		suelo = new Rectangle(-1, ALTO - 100, ANCHO, ALTO - 500);
		
		fondo = new Fondo(0, 0, ANCHO, ALTO);
	}
	
	public void actualizar() {
		this.repaint();
		
		dino.actualizar(suelo);
		dino.saltar();
		dino.caer(suelo);
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
			g.setColor(Color.DARK_GRAY);
			g.drawRect(suelo.x, suelo.y, suelo.width, suelo.height);
		}
		
		if(obstaculo != null) {
			g.drawImage(obstaculo.getImagen(), obstaculo.getX(), obstaculo.getY(), obstaculo.getAncho(), obstaculo.getAlto(), null);
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
