package Clases;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import Principal.JuegoMain;

public class Item extends ElementoPrincipal {
	private String nombreItem;
	private boolean activo=true;
	
	public Item(){}

	public Item(int x, int y, int velocidad, String llaveImagen, String nombreItem) {
		super(x, y, velocidad, llaveImagen);
		this.nombreItem = nombreItem;
	}

	public String getNombreItem() {
		return nombreItem;
	}

	public void setNombreItem(String nombreItem) {
		this.nombreItem = nombreItem;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	/*public void pintar(Graphics2D g2D, ImageObserver observer){
	//	g2D.setColor(Color.RED);
	//	g2D.drawRect(x,y,anchoImagen, altoImagen);
		g2D.drawImage(JuegoMain.imagenes.get(this.llaveImagen), x, y, observer);
	}*/
	
	public Rectangle obtenerRectangulo(){
		return new Rectangle(x,y,anchoImagen, altoImagen);
	}
	
	public void gestionarColision(){
		activo=false;
	}
}
