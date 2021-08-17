package Clases;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import Principal.JuegoMain;

public class ElementoPrincipal {
		protected int x;
		protected int y;
		protected int velocidad;
		protected int anchoImagen;
		protected int altoImagen;
		protected String llaveImagen;
		protected boolean activo = true;
		
		public ElementoPrincipal() {
		}
		public ElementoPrincipal(int x, int y, int velocidad, String llaveImagen) {
			this.x = x;
			this.y = y;
			this.velocidad = velocidad;
			this.llaveImagen = llaveImagen;
			anchoImagen = JuegoMain.imagenes.get(llaveImagen).getWidth();
			altoImagen = JuegoMain.imagenes.get(llaveImagen).getHeight();
		}


		public boolean isActivo() {
			return activo;
		}

		public void setActivo(boolean activo) {
			this.activo = activo;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getVelocidad() {
			return velocidad;
		}
		public void setVelocidad(int velocidad) {
			this.velocidad = velocidad;
		}
		public int getAnchoImagen() {
			return anchoImagen;
		}
		public void setAnchoImagen(int anchoImagen) {
			this.anchoImagen = anchoImagen;
		}
		public int getAltoImagen() {
			return altoImagen;
		}
		public void setAltoImagen(int altoImagen) {
			this.altoImagen = altoImagen;
		}
		public String getLlaveImagen() {
			return llaveImagen;
		}
		public void setLlaveImagen(String llaveImagen) {
			this.llaveImagen = llaveImagen;
		}

		/*public void pintar(Graphics2D g2D, ImageObserver imgObs){
			g2D.drawImage(JuegoMain.imagenes.get(llaveImagen), x, y, imgObs);
		}*/
		public void pintar(Graphics2D g2D, ImageObserver observer){
		//	g2D.setColor(Color.RED);
		//	g2D.drawRect(x,y,anchoImagen, altoImagen);
			g2D.drawImage(JuegoMain.imagenes.get(this.llaveImagen), x, y, observer);
		}
		
		public Rectangle obtenerRectangulo(){
			return new Rectangle(x,y,anchoImagen, altoImagen);
		}

		public void mover(){
		}
		public void gestionarColision(){
			activo=false;
		}
		public void activarElemento(){
			activo=true;
		}
}
