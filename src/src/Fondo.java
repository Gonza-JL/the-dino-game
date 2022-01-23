package src;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Fondo {
	
	private int x, y;
	private int ancho, alto;
	private Image fondo;
	private Image nube;
	
	public Fondo(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.fondo = new ImageIcon("fondo.png").getImage();
		this.nube = new ImageIcon("nube.png").getImage();
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
		return fondo;
	}
	
	public Image getNube() {
		return nube;
	}

}
