package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Mapa {
	
	private int x, y;
	private int ancho, alto;
	private Image fondo;
	private Image nube;
	private List<Integer> xNubes;
	private List<Obstaculo> obstaculos;
	private Rectangle suelo;
	private Image imagenSuelo;
	private int xSuelo;
	
	public Mapa(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		
		xNubes = new ArrayList<>();
		xNubes.add(100);
		xNubes.add(500);
		xNubes.add(800);
		xNubes.add(1100);
		
		fondo = new ImageIcon("fondo.png").getImage();
		nube = new ImageIcon("nube.png").getImage();
		imagenSuelo = new ImageIcon("suelo.png").getImage();
		
		this.suelo = new Rectangle(-1, alto - 100, ancho, alto - 500);
		this.xSuelo = 0;
		
		obstaculos = new ArrayList<>();
		Obstaculo o1 = new Obstaculo(ancho, alto - 200, ancho - 1220, alto - 500);
		Obstaculo o2 = new Obstaculo(ancho + 100, alto - 150, ancho - 1250, alto - 550);
		o2.setXRandom(true);
		obstaculos.add(o1);
		obstaculos.add(o2);
	}
	
	public void actualizar() {
		if(xSuelo == 100) {
			xSuelo = 0;
		} else {
			xSuelo += 5;
		}
		
		for(int i = 0; i < xNubes.size(); i++) {
			if(xNubes.get(i) + 200 > 0) {
				xNubes.set(i, xNubes.get(i) - 5);
			} else {
				xNubes.set(i, ancho);
			}
		}
	}
	
	public void dibujar(Graphics g) {
		g.drawImage(fondo, x, y, ancho, alto, null);
		dibujarNubes(g);
		dibujarSuelo(g);
		for(int i = 0; i < obstaculos.size(); i++) {
			obstaculos.get(i).dibujar(g);
		}
	}
	
	private void dibujarSuelo(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i <= 1300; i += 100) {
			g.drawImage(imagenSuelo, suelo.x + i - xSuelo, suelo.y, 100, suelo.height, null);
		}
		g.drawRect(suelo.x, suelo.y, suelo.width, suelo.height);
	}
	
	private void dibujarNubes(Graphics g) {
		g.drawImage(nube, xNubes.get(0), 150, 125, 50, null);
		g.drawImage(nube, xNubes.get(1), 80, 80, 25, null);
		g.drawImage(nube, xNubes.get(2), 200, 100, 40, null);
		g.drawImage(nube, xNubes.get(3), 150, 80, 25, null);
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

	public List<Obstaculo> getObstaculos() {
		return obstaculos;
	}

	public Rectangle getSuelo() {
		return suelo;
	}

}
