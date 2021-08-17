package Clases;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import Principal.JuegoMain;

public class Fondo extends ElementoPrincipal {
	private int x2;
	public Fondo(int x, int y, int velocidad, String llaveImagen) {
		super(x, y, velocidad, llaveImagen);
	}

	public Fondo(){}
	
	/*public void pintar(Graphics2D g2D, ImageObserver imgObs){
		g2D.setColor(Color.BLUE);
		g2D.drawRect(51, 650, 374, 20);
		g2D.drawRect(476, 0, 65, 670);
		g2D.drawRect(0, 0, 50, 670);

		g2D.drawImage(JuegoMain.imagenes.get(llaveImagen), x, y, imgObs);
		g2D.drawImage(JuegoMain.imagenes.get(llaveImagen2), x2, y, imgObs);
	}
	public Rectangle obtenerRectangulo(){
		return new Rectangle(x, y, anchoImagen, altoImagen);
	}*/
	public void pintar(Graphics2D g2D , ImageObserver imgObs){
	/*	g2D.setColor(Color.BLUE);
		g2D.drawRect(51, 650, 374, 20);
		g2D.drawRect(476, 0, 65, 670);
		g2D.drawRect(0, 0, 50, 670);*/
		
	}
	public Rectangle obtenerRectangulo(){
		return new Rectangle(x,y,anchoImagen, altoImagen);
	}

}
