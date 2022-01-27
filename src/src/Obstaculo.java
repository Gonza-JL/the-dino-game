package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Obstaculo {
	
	private int x, y;
	private int ancho, alto;
	private Image imagen;
	private int velocidad = 9;
	private boolean xRandom;
	
	public Obstaculo(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.xRandom = false;
		this.imagen = new ImageIcon("obstaculo.png").getImage();
	}
	
	public void dibujar(Graphics g) {
		g.setColor(Color.RED);
		g.drawImage(getImagen(), x, y, ancho, alto, null);	
	}
	
	public void mover() {
		int random = (int)(Math.random() * 2);
		if(x + ancho > 0) {
			x -= velocidad;
		} else {
			if(xRandom && random == 0) {
				x += Juego.ANCHO + 200;
			} else {
				x += Juego.ANCHO;
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public Image getImagen() {
		return imagen;
	}

	public int getVelocidad() {
		return velocidad;
	}
	
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	
	public void setXRandom(boolean xRandom) {
		this.xRandom = xRandom;
	}
	
}
