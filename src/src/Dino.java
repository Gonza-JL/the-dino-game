package src;

import java.awt.Image;
import java.awt.Rectangle;

public class Dino extends Rectangle {
	
	private Image imagen;
	private boolean saltando;
	private int tiempoEnElAire;
	private int velocidad = 10;
	

	public Dino(int x, int y, int ancho, int alto) {
		super(x, y, ancho, alto);
	}
	
	public void actualizar(Rectangle r) {
		if(!estaEnElSuelo(r)) {
			tiempoEnElAire++;
		} else {
			tiempoEnElAire = 0;
		}
	}
	
	public void saltar(Rectangle r) {
		if(saltando && tiempoEnElAire < 20) {
			this.y -= velocidad;
		} else {
			saltando = false;
		}
	}
	
	public void caer(Rectangle r) {
		if(!estaEnElSuelo(r) && !saltando || tiempoEnElAire >= 20) {
			this.y += velocidad;
		}
	}
	
	public boolean estaEnElSuelo(Rectangle r) {
		if(this.y + 30 == r.y - r.height/2) {
			return true;
		}
		return false;
	}

	public void setSaltando(boolean saltando) {
		this.saltando = saltando;
	}

	public boolean getSaltando() {
		return saltando;
	}

}
