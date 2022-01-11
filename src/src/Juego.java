package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Juego extends JPanel {
	
	static int ANCHO = 1280;
	static int ALTO = 600;

	Dino dino;
	Rectangle suelo;
	
	public Juego() {
		JFrame ventana = new JFrame("The Dino Game");
		ventana.setSize(ANCHO, ALTO);
		ventana.setLocationRelativeTo(null);  // Establecemos la ventana en el centro de la pantalla
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Apagamos el programa cuando cerramos la ventana
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
		
		this.setBackground(Color.BLACK);
		
		dino = new Dino(100, 400, ANCHO - 1200, ALTO - 520);
		
		suelo = new Rectangle(0, ALTO - 100, ANCHO, 100);
	}
	
	public void actualizar() {
		this.repaint();
		
		dino.actualizar(suelo);
		dino.saltar(suelo);
		dino.caer(suelo);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.GREEN);
		g.fillRect(dino.x, dino.y, dino.width, dino.height);
		
		g.setColor(Color.WHITE);
		g.fillRect(suelo.x, suelo.y, suelo.width, suelo.height);
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
