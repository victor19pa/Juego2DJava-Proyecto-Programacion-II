package Clases;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import Principal.JuegoMain;

public class Jugador extends ElementoPrincipal {
	private String nombreJugador;
	private int vidas=0;
	private int itemsRecolectados=0;
	private int estadoJugador=0;

	public Jugador(){}
	private int iteradorImagen=0;

	public Jugador(int x, int y, int velocidad, String llaveImagen, String nombreJugador, int vidas,
			int itemsRecolectados) {
		super(x, y, velocidad, llaveImagen);
		this.nombreJugador = nombreJugador;
		this.vidas = vidas;
		this.itemsRecolectados = itemsRecolectados;
	}

	public String getNombreJugador() {
		return nombreJugador;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public int getItemsRecolectados() {
		return itemsRecolectados;
	}

	public void setItemsRecolectados(int itemsRecolectados) {
		this.itemsRecolectados = itemsRecolectados;
	}
	
	public int getEstadoJugador() {
		return estadoJugador;
	}

	public void setEstadoJugador(int estadoJugador) {
		this.estadoJugador = estadoJugador;
	}
/*	
	public void pintar(Graphics2D g2D, ImageObserver observer){
		if(JuegoMain.presionoAbajo || JuegoMain.presionoDerecha || JuegoMain.presionoIzquierda){
		g2D.setColor(Color.BLUE);
		g2D.drawRect(x+8,y,anchoImagen-7, altoImagen);
		}
		if(JuegoMain.presionoArriba){
			g2D.setColor(Color.RED);
			g2D.drawRect(x+3,y,anchoImagen-7,altoImagen);
		}
		if(JuegoMain.presionoD){
			g2D.setColor(Color.RED);
			g2D.drawRect(x+5,y+14,anchoImagen+28,altoImagen-23);
		}
		if(JuegoMain.presionoW){
			g2D.setColor(Color.RED);
			g2D.drawRect(x+7,y,anchoImagen-17,altoImagen);
		}
		if(JuegoMain.presionoA){
			g2D.setColor(Color.RED);
			g2D.drawRect(x,y+15,anchoImagen,altoImagen-25);
		}
		if(JuegoMain.presionoS){
			g2D.setColor(Color.RED);
			g2D.drawRect(x+13,y+25,anchoImagen-15, altoImagen);
		}
		g2D.drawImage(JuegoMain.imagenes.get(this.llaveImagen), x, y, observer);
	}
	*/
	public Rectangle obtenerRectangulo(){
		//	if(this.getLlaveImagen()==)
			if(JuegoMain.presionoArriba)
				return new Rectangle(x+3,y,anchoImagen-7, altoImagen);
			if(JuegoMain.presionoD)
				return new Rectangle(x+5,y,anchoImagen+25, altoImagen);
			if(JuegoMain.presionoW)
				return new Rectangle(x+5,y,anchoImagen-10, altoImagen);
			if(JuegoMain.presionoA)
				return new Rectangle(x,y+15,anchoImagen,altoImagen-25);
			if(JuegoMain.presionoS)
				return new Rectangle(x+13,y+25,anchoImagen-15,altoImagen);
			
			return new Rectangle(x+8,y,anchoImagen-7, altoImagen);
		}
	
	public void mover(){
		if(JuegoMain.presionoDerecha){
			x+=velocidad;
			if(iteradorImagen>=5){
				iteradorImagen=0;
				this.llaveImagen="derecha1";
			}else{
				this.llaveImagen="derecha2";
			}
		}
		if(JuegoMain.presionoIzquierda){
			x-=velocidad;
			if(iteradorImagen>=5){
				iteradorImagen=0;
				this.llaveImagen="izquierda1";
			}else{
				this.llaveImagen="izquierda2";
			}
		}
		if(JuegoMain.presionoArriba){
			y-=velocidad;
			if(iteradorImagen>=5){
				iteradorImagen=0;
				this.llaveImagen="espalda1";
			}else{
				this.llaveImagen="espalda2";
			}
		}
		if(JuegoMain.presionoAbajo){
			y+=velocidad;
			if(iteradorImagen>=5){
				iteradorImagen=0;
				this.llaveImagen="frente1";
			}else{
				this.llaveImagen="frente2";
			}
		}
		iteradorImagen++;
	}
	public void choqueFondo(){
		x-=velocidad;
		y-=velocidad;
	}
	public void agregarItem(){
		itemsRecolectados++;
	}
	public void agregarVidas(){
		vidas++;
	}
	public void restarVidas(){
		vidas--;
	}

	public void disminuirY(){
		y-=velocidad+3;
	}
	public void aumentarY(){
		y+=velocidad+3;
	}
	public void disminuirX(){
		x-=velocidad+3;
	}
	public void aumentarX(){
		x+=velocidad+3;
	}
	
}
