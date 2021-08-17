package Clases;

import Principal.JuegoMain;

public class Enemigo extends ElementoPrincipal {
	private String nombreVillano;
	private boolean activo=true;
	private int estadoVillano=0;
	
	public Enemigo(){}
	private int iteradorImagen=0;
	public Enemigo(int x, int y, int velocidad, String llaveImagen, String nombreVillano) {
		super(x, y, velocidad, llaveImagen);
		this.nombreVillano = nombreVillano;
	}
	public String getNombreVillano() {
		return nombreVillano;
	}
	public void setNombreVillano(String nombreVillano) {
		this.nombreVillano = nombreVillano;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public void gestionarColision(){
		activo=false;
	}
	public int getEstadoVillano() {
		return estadoVillano;
	}

	public void setEstadoVillano(int estadoVillano) {
		this.estadoVillano = estadoVillano;
	}
	public void mover(){
		if(JuegoMain.jugando){
			x=x+8;
			if(iteradorImagen>=5){
				iteradorImagen=0;
			
			}else{
				//x=x-8;
			}
		}
		iteradorImagen++;
	}
}
