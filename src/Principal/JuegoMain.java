package Principal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.JobAttributes;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.stream.events.EndElement;

import Clases.ElementoPrincipal;
import Clases.Enemigo;
import Clases.Fondo;
import Clases.Item;
import Clases.Jugador;

public class JuegoMain extends Canvas implements KeyListener {
	private JFrame ventana;
	private BufferStrategy dobleBuffer;
	private Graphics2D g2D;

	public static boolean jugando  = false;

	public static final int ANCHO_VENTANA = 550/*DEFINA SUS PROPIAS DIMENSIONES*/;
	public static final int ALTO_VENTANA = 700/*DEFINA SUS PROPIAS DIMENSIONES*/;

	int lastFpsTime; //Variable auxiliar para calculo de la pausa del ciclo principal
	int fps; //Fotogramas por segundo

	private boolean nivel1=true;
	private boolean nivel2=false;
	private boolean nivel3=false;

	private ArrayList<Item> items;
	private ArrayList<ElementoPrincipal> obs;

	private ElementoPrincipal tile;
	private Jugador jugador;
	private Enemigo enemigos;
//	private Item item;
//	private Villanos villano;
	private Fondo fondos;

	public static boolean presionoIzquierda;
	public static boolean presionoDerecha;
	public static boolean presionoArriba;
	public static boolean presionoAbajo;
	public static boolean presionoW;
	public static boolean presionoS;
	public static boolean presionoA;
	public static boolean presionoD;

	private String cadenaX = "";
	private String cadenaY = "";

	public static HashMap<String,BufferedImage> imagenes = new HashMap<String,BufferedImage>();
	
	//CAMINO/MAPA NIVEL 1
	private int tileMap1[][]={
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 0 , 1},
			{2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 2 , 1 , 0 , 1},
			{1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 2 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1}
	};



	private int tileMapFondoB[][]={
			{2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{6 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 2 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 2 , 0 , 3 , 0 , 0 , 0 , 0 , 0 , 0},
			{6 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 2 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 2 , 0 , 5 , 0 , 0 , 0 , 0 , 0 , 0},
			{6 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 2 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 0 , 0 , 0 , 0 , 0 , 0},
			{6 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 2 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 5 , 0 , 0 , 0 , 0 , 0 , 0},
			{6 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 5 , 5 , 5 , 0 , 0 , 0 , 0 , 3 , 0 , 0 , 0 , 0 , 0 , 0},
			{6 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 5 , 3 , 5 , 0 , 0 , 5 , 0 , 0 , 0 , 0 , 0 , 0},
			{6 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 5 , 3 , 5 , 0 , 0 , 3 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 3 , 3 , 0 , 0 , 5 , 3 , 5 , 0 , 0 , 5 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 3 , 3 , 0 , 0 , 4 , 3 , 5 , 0 , 0 , 3 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 3 , 3 , 0 , 0 , 4 , 3 , 5 , 0 , 0 , 5 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 3 , 3 , 0 , 0 , 4 , 3 , 5 , 0 , 0 , 3 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 3 , 3 , 0 , 0 , 4 , 3 , 5 , 0 , 0 , 5 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 3 , 3 , 0 , 0 , 4 , 3 , 5 , 0 , 0 , 3 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 5 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{2 , 0 , 2 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 0 , 0 , 1 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{2 , 0 , 2 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 2 , 0 , 0}

	};
	//CAMINO/MAPA NIVEL 2

	private int tileMap2[][]={
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1}
	};

	private int tileMapFondoB2[][]={
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0}

	};
	 //MAPA / CAMINO NIVEL 3
	private int tileMap3[][]={
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{2 , 2 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 2 , 2 , 1 , 1 , 1 , 2 , 2 , 1 , 1 , 0 , 1},
			{2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 0 , 1},
			{1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1},
			{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 0 , 1}
	};

	private int tileMapFondoB3[][]={
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 3 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 4 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 5 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 6 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
			{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0}

	};

	public JuegoMain(){
		obs = new ArrayList<ElementoPrincipal>();
		cargarImagenes();
		inicializarObjetosJuego();
		inicializarVentana();

		createBufferStrategy(2);
		dobleBuffer = getBufferStrategy();

		jugando = true;

		this.requestFocus();
		this.addKeyListener(this);

		cicloPrincipal();
	}

	public void inicializarVentana(){
		ventana = new JFrame();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(ANCHO_VENTANA, ALTO_VENTANA);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setTitle("Usuario: " + jugador.getNombreJugador());
		ventana.getContentPane().add(this);
		ventana.setVisible(true);

	}

	public void inicializarObjetosJuego(){
		JOptionPane.showMessageDialog(null, "INSTRUCCIONES \n "
				+ "Movimientos:------- Flechas \n"
				+ "Para atacar aprete una vez o mantenga apretado\n"
				+ "Ataque Arriba:-------'W' \n"
				+ "Ataque Abajo:--------'S' \n"
				+ "Ataque Izquierda:---'A' \n"
				+ "Ataque Derecha:----'D' \n");
		jugador = new Jugador(434, 627, 2, "espalda2", JOptionPane.showInputDialog("Usuario: "), 0, 0);
		fondos = new Fondo();
		
		
		//posible error de cambio de nivel
		if(nivel1==true)
			agregarObsNivel1();
		if(nivel2==true && !nivel1)
        	agregarObjetosN2();
	}

	public void agregarObsNivel1(){
		obs.add(new Item(65, 550, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 530, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 510, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 490, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 470, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 450, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 430, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 410, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 390, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 370, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 350, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 330, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 310, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 290, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 270, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 250, 1, "Moneda1", "Moneda"));
		obs.add(new Item(65, 230, 1, "Moneda1", "Moneda"));
		obs.add(new Item(425, 80, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 80, 1, "Moneda1", "Moneda"));
		obs.add(new Item(455, 80, 1, "Moneda1", "Moneda"));
		obs.add(new Item(425, 65, 1, "Moneda1", "Moneda"));
		obs.add(new Item(425, 50, 1, "Moneda1", "Moneda"));
		obs.add(new Item(425, 35, 1, "Moneda1", "Moneda"));
		obs.add(new Item(425, 35, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 35, 1, "Moneda1", "Moneda"));
		obs.add(new Item(455, 35, 1, "Moneda1", "Moneda"));
		obs.add(new Item(455, 65, 1, "Moneda1", "Moneda"));
		obs.add(new Item(455, 50, 1, "Moneda1", "Moneda"));
		obs.add(new Item(455, 35, 1, "Moneda1", "Moneda"));
		obs.add(new Item(285, 310, 1, "Moneda1", "Moneda"));
		obs.add(new Item(385, 310, 1, "Moneda1", "Moneda"));
		obs.add(new Item(365, 310, 1, "Moneda1", "Moneda"));
		obs.add(new Item(345, 310, 1, "Moneda1", "Moneda"));
		obs.add(new Item(325, 310, 1, "Moneda1", "Moneda"));
		obs.add(new Item(305, 310, 1, "Moneda1", "Moneda"));
		obs.add(new Item(285, 410, 1, "Moneda1", "Moneda"));
		obs.add(new Item(385, 410, 1, "Moneda1", "Moneda"));
		obs.add(new Item(365, 410, 1, "Moneda1", "Moneda"));
		obs.add(new Item(345, 410, 1, "Moneda1", "Moneda"));
		obs.add(new Item(325, 410, 1, "Moneda1", "Moneda"));
		obs.add(new Item(305, 410, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 440, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 460, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 480, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 500, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 520, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 540, 1, "Moneda1", "Moneda"));
		obs.add(new Item(440, 55, 1, "Corazon", "Corazon"));
		obs.add(new Item(435, 250, 1, "VidaDisco", "Corazon"));

		obs.add(new Enemigo(100, 490, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(120, 480, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(120, 500, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(200, 540, 1, "Planta", "Planta"));
		obs.add(new Enemigo(220, 540, 1, "Planta", "Planta"));
		obs.add(new Enemigo(240, 540, 1, "Planta", "Planta"));
		obs.add(new Enemigo(295, 540, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(345, 540, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(395, 540, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(120, 210, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(100, 220, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(120, 230, 1, "HongoB1", "Hongo"));
		
		obs.add(new Enemigo(200, 220, 1, "Planta", "Planta"));
		obs.add(new Enemigo(220, 220, 1, "Planta", "Planta"));
		obs.add(new Enemigo(240, 220, 1, "Planta", "Planta"));
		obs.add(new Enemigo(310, 220, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(360, 220, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(410, 220, 1, "HongoB1", "Hongo"));
		
		obs.add(new Enemigo(225, 360, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(245, 370, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(245, 350, 1, "HongoB1", "Hongo"));
	//	obs.add(new Enemigo(385, 35, 2, "Koopa", "Koopa"));
		obs.add(new Enemigo(440, 100, 1, "Planta", "Planta"));
		obs.add(new Enemigo(440, 130, 1, "Planta", "Planta"));
		obs.add(new Enemigo(440, 160, 1, "Planta", "Planta"));
	}
// NO FUNCIONA AGREGAR OBS N2 por eso no se siguio agregando los items de nivel 2 y nivel 3
	public void agregarObjetosN2(){
		obs.add(new Enemigo(90, 432, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(221, 432, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(371, 432, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(221, 276, 1, "HongoB1", "Hongo"));
		obs.add(new Enemigo(75, 276, 1, "HongoB1", "Hongo"));
		
		for(int i=0; i<obs.size();i++){
			obs.get(i).activarElemento();
		}
	}

	public void verificarColisiones(){
		for(int i=0;i<obs.size();i++){
			if(obs.get(i) instanceof Item){
				if(obs.get(i).getLlaveImagen()=="Moneda1")
					if(jugador.obtenerRectangulo().intersects(obs.get(i).obtenerRectangulo())){
						obs.get(i).gestionarColision();
						jugador.agregarItem();
					}
				if(obs.get(i).getLlaveImagen()=="Corazon" || obs.get(i).getLlaveImagen()=="VidaDisco")
					if(jugador.obtenerRectangulo().intersects(obs.get(i).obtenerRectangulo())){
						obs.get(i).gestionarColision();
						jugador.agregarVidas();
					}
			}
			if(obs.get(i) instanceof Enemigo){
				if(jugador.getLlaveImagen()=="frente1"|| jugador.getLlaveImagen()=="espalda1"|| jugador.getLlaveImagen()=="frente2"
						|| jugador.getLlaveImagen()=="espalda2" || jugador.getLlaveImagen()=="izquierda1" || jugador.getLlaveImagen()=="izquierda2"
						|| jugador.getLlaveImagen()=="derecha1"|| jugador.getLlaveImagen()=="derecha2"){
					if(jugador.obtenerRectangulo().intersects(obs.get(i).obtenerRectangulo())){
						obs.get(i).gestionarColision();
						jugador.restarVidas();
					}
				}
				if(jugador.getLlaveImagen()=="AtaqueArriba"||jugador.getLlaveImagen()=="AtaqueAbajo" ||
						jugador.getLlaveImagen()=="AtaqueIzquierda"||jugador.getLlaveImagen()=="AtaqueDerecha"){
					if(jugador.obtenerRectangulo().intersects(obs.get(i).obtenerRectangulo())){
						obs.get(i).gestionarColision();
						jugador.agregarItem();
						limpiarMemoria();
					}
				}
			}
		}
	}

	public void limpiarMemoria(){
		for(int i=0;i<obs.size();i++){
			if(!obs.get(i).isActivo()){
				obs.set(i, null);
				obs.remove(i);
			}
		}
	}
	
	public void verificarVidas(){
			if(jugador.getVidas()<0){
				JOptionPane.showMessageDialog(null, "GAMEOVER");
				int resultado = JOptionPane.showConfirmDialog(null, "¿Desea volver a jugar?");
				if(resultado==1||resultado==2)
					salir();
				else{
					//cicloPrincipal();
					jugador = new Jugador(434, 627, 2, "espalda2", JOptionPane.showInputDialog("Usuario: "), 0, 0);
				//	pintarNivel1();
					presionoDerecha=false;
					presionoIzquierda=false;
					presionoAbajo=false;
					presionoArriba=false;
					agregarObsNivel1();
					nivel2=false;
					nivel3=false;
					nivel1=true;
				}

			}
	}
	public void salir(){
		System.exit(0);
	}

	public void cargarImagenes(){
		try{
			BufferedImage fondo = ImageIO.read(getClass().getResource("/recursos graficos/fondo3.png"));
			BufferedImage jugador = ImageIO.read(getClass().getResource("/recursos graficos/Jugador_sprites.png"));
			BufferedImage fondoB = ImageIO.read(getClass().getResource("/recursos graficos/fondo2.png"));
			BufferedImage villanos = ImageIO.read(getClass().getResource("/recursos graficos/villanos_mario.png"));
			BufferedImage items = ImageIO.read(getClass().getResource("/recursos graficos/goldCoin1.png"));
			BufferedImage corazon = ImageIO.read(getClass().getResource("/recursos graficos/heart.png"));
			BufferedImage corazonDisco = ImageIO.read(getClass().getResource("/recursos graficos/Vida_disco.png"));
			//fondo3 nivel 1
			imagenes.put("fondo1", fondo.getSubimage(77, 88, 27, 26));
			imagenes.put("fondo2", fondo.getSubimage(1284, 171, 27, 26));
			//fondoB nivel 1 arboles y demas
			imagenes.put("fondoB1", fondoB.getSubimage(589, 571, 60, 76)); /*arbol*/
			imagenes.put("fondoB2", fondoB.getSubimage(549, 590, 39, 58)); /*arbol*/
			imagenes.put("fondoB3", fondoB.getSubimage(529, 630, 18, 19)); /*hongo*/
			imagenes.put("fondoB4", fondoB.getSubimage(305, 609, 18, 40)); /*milpa*/
			imagenes.put("fondoB5", fondoB.getSubimage(264, 586, 20, 22)); /*tomate*/
			imagenes.put("fondoB6", fondoB.getSubimage(610, 2  , 39, 95)); /*pino*/
			//jugador
			imagenes.put("espalda1", jugador.getSubimage(143, 69, 28, 37));/*espalda1*/
			imagenes.put("espalda2", jugador.getSubimage(143, 0, 28, 37));/*espalda2*/
			imagenes.put("frente1", jugador.getSubimage(0, 0, 35, 39));/*frente 1*/
			imagenes.put("frente2", jugador.getSubimage(0, 69, 31, 37));/*frente 2*/
			imagenes.put("izquierda1", jugador.getSubimage(69, 0, 35, 39));/*Izquie 1*/
			imagenes.put("izquierda2", jugador.getSubimage(70, 69, 34, 35));/*Izquie 2*/
			imagenes.put("derecha1", jugador.getSubimage(207, 0, 34, 34));/*Derech 1*/
			imagenes.put("derecha2", jugador.getSubimage(208, 69, 33, 37));/*Derech 2*/
			imagenes.put("AtaqueAbajo", jugador.getSubimage(0, 193, 37, 64));
			imagenes.put("AtaqueArriba", jugador.getSubimage(140, 193, 36, 66));
			imagenes.put("AtaqueIzquierda", jugador.getSubimage(54, 207, 64, 36));
			imagenes.put("AtaqueDerecha", jugador.getSubimage(193, 207, 64, 36));
		   //villanos
			imagenes.put("HongoB1", villanos.getSubimage(0, 15, 16, 17));
			imagenes.put("HongoB2", villanos.getSubimage(31, 23, 17, 9));
			imagenes.put("Planta", villanos.getSubimage(191, 8, 17, 24));
			imagenes.put("Koopa", villanos.getSubimage(655, 0, 32, 31));
			//items
			imagenes.put("Moneda1", items.getSubimage(151, 60, 19, 19));
			imagenes.put("Moneda2", items.getSubimage(124, 60, 15, 19));//de lado1
			imagenes.put("Moneda3", items.getSubimage(188, 60, 15, 19));
			imagenes.put("Corazon", corazon.getSubimage(0, 0, 24, 24));
			imagenes.put("VidaDisco", corazonDisco.getSubimage(8, 13, 25, 22));



		}catch ( IOException e){
			e.printStackTrace();
		}
	}

	public void pintarNivel1(){
		for(int i=0;i<tileMap1.length;i++)
    		for(int j=0;j<tileMap1[0].length;j++)
    			if (tileMap1[i][j]!=0)
    				g2D.drawImage(imagenes.get("fondo"+tileMap1[i][j]), i*25, j*25, this);

		for(int i=0;i<tileMapFondoB.length;i++)
    		for(int j=0;j<tileMapFondoB[0].length;j++)
				if (tileMapFondoB[i][j]!=0)
					g2D.drawImage(imagenes.get("fondoB"+tileMapFondoB[i][j]), i*25, j*25, this);

    	for(int i=0;i<obs.size();i++)
    		if(obs.get(i).isActivo())
    			obs.get(i).pintar(g2D, this);
    	
    }
	public void pintarNivel2(){
		
		for(int i=0;i<tileMap2.length;i++)
    		for(int j=0;j<tileMap2[0].length;j++)
    			if (tileMap1[i][j]!=0)
    				g2D.drawImage(imagenes.get("fondo"+tileMap2[i][j]), i*25, j*25, this);

		for(int i=0;i<tileMapFondoB2.length;i++)
    		for(int j=0;j<tileMapFondoB2[0].length;j++)
				if (tileMapFondoB2[i][j]!=0)
					g2D.drawImage(imagenes.get("fondoB2"+tileMapFondoB2[i][j]), i*25, j*25, this);
		
// PINTA LOS ITEMS DEL NIVEL 1
		/*for(int i=0;i<obs.size();i++){
    		if(obs.get(i).isActivo())
    			obs.get(i).pintar(g2D, this);
    	}*/
	}

	public void pintarNivel3(){
		for(int i=0;i<tileMap3.length;i++)
    		for(int j=0;j<tileMap3[0].length;j++)
    			if (tileMap3[i][j]!=0)
    				g2D.drawImage(imagenes.get("fondo"+tileMap3[i][j]), i*25, j*25, this);

		for(int i=0;i<tileMapFondoB3.length;i++)
    		for(int j=0;j<tileMapFondoB3[0].length;j++)
				if (tileMapFondoB3[i][j]!=0)
					g2D.drawImage(imagenes.get("fondoB3"+tileMapFondoB2[i][j]), i*25, j*25, this);
		//PINTA LOS ITEMS DEL NIVEL 1
		/*for(int i=0;i<obs.size();i++){
    		if(obs.get(i).isActivo())
    			obs.get(i).pintar(g2D, this);
    	}*/
	}

	private void pintar(){

        g2D = (Graphics2D)dobleBuffer.getDrawGraphics(); //Obtener la instancia de Graphics para pintar los elementos

        //Puede borrar las siguientes 4 lineas
        g2D.setColor(new Color(250,250,250)); //Definir el color negro en el contexto
        g2D.fillRect(0, 0, ANCHO_VENTANA, ALTO_VENTANA); //Dibujar un rectangulo
        g2D.setColor(Color.WHITE); //Definir el color blanco en el contexto

        //Pintar objetos de juego
        //nivel 1
        if(nivel1){
        	pintarNivel1();
        }
        if(nivel2){
        	pintarNivel2();    		
        }

        if(nivel3){
        	pintarNivel3();
        }

        fondos.pintar(g2D, this);
        jugador.pintar(g2D, this);
       	cadenaX = String.valueOf(jugador.getX());
       	cadenaY = String.valueOf(jugador.getY());
        g2D.setColor(new Color(250, 250, 250));
	    g2D.drawString("\t"+"USUARIO: "+jugador.getNombreJugador(),150, 20);
	    g2D.drawString("Coor X: "+cadenaX,150,40);
	    g2D.drawString("Coor Y: "+cadenaY,150,60);
	    g2D.drawString("\t"+"PUNTUACION: "+jugador.getItemsRecolectados(), 400, 20);
	    g2D.drawString("VIDAS: "+jugador.getVidas(), 300,20 );

        dobleBuffer.show();
    }

	public void actualizar(){
		jugador.mover();
		// Mover enemigos no funciona
		/*
		 * 	enemigos.mover();
		 */
		if(nivel1==true){
			if(jugador.getX()<=70 && jugador.getY()<=1){
				nivel2=true;
				jugador = new Jugador(434, 627, 2, "espalda2",jugador.getNombreJugador(), jugador.getVidas(), jugador.getItemsRecolectados());
				JOptionPane.showMessageDialog(null, "FELICIDADES NIVEL 2");
				presionoDerecha=false;
				presionoIzquierda=false;
				presionoAbajo=false;
				presionoArriba=false;
				nivel1=false;
				for(int i=0;i<obs.size();i++){
					obs.get(i).gestionarColision();
				}
			//	limpiarMemoria();

			}
		}
		if(nivel1==false && nivel2==true){
			if(jugador.getX()<=422 && jugador.getY()<=21){
				nivel3=true;
				jugador = new Jugador(434, 627, 3, "espalda2",jugador.getNombreJugador(), jugador.getVidas(), jugador.getItemsRecolectados());
				JOptionPane.showMessageDialog(null, "FELICIDADES NIVEL 3");
				presionoDerecha=false;
				presionoIzquierda=false;
				presionoAbajo=false;
				presionoArriba=false;

				nivel2=false;
				nivel1=false;
				for(int i=0;i<obs.size();i++){
					obs.get(i).gestionarColision();
				}
			//	limpiarMemoria();

			}
		}
		if(nivel1==false && nivel2==false && nivel3==true){
			if(jugador.getY()<=12 && jugador.getX()<=422){
				nivel3=false;
				JOptionPane.showMessageDialog(null, "FIN DEL JUEGO");
				int resultado = JOptionPane.showConfirmDialog(null, "¿Desea volver a jugar?");
				if(resultado==1||resultado==2)
					salir();
				else{
					//cicloPrincipal();
					jugador = new Jugador(434, 627, 2, "espalda2", JOptionPane.showInputDialog("Usuario: "), 0, 0);
				//	pintarNivel1();
					presionoDerecha=false;
					presionoIzquierda=false;
					presionoAbajo=false;
					presionoArriba=false;
					agregarObsNivel1();
					nivel2=false;
					nivel3=false;
					nivel1=true;
				}

			}
		}
	}

	public void cicloPrincipal(){
       //variables para el calculo del tiempo para la pausa, este codigo es opcional, si lo desea puede definir un valor para la pausa en duro.
	   long lastLoopTime = System.nanoTime();
       final int TARGET_FPS = 60;
       final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

       while (jugando){
    	   //Calculo del tiempo optimo y uniforme para la pausa.
    	   long now = System.nanoTime();
    	   long updateLength = now - lastLoopTime;
    	   lastLoopTime = now;
    	   double delta = updateLength / ((double)OPTIMAL_TIME);
    	   lastFpsTime += updateLength;
    	   fps++;
    	   if (lastFpsTime >= 1000000000){
    		   System.out.println("(FPS: "+fps+")");
    		   lastFpsTime = 0;
    		   fps = 0;
    	   }

    	   pintar();
    	   actualizar();
    	   verificarColisiones();
    	   verificarVidas();
    	   limpiarMemoria();

    	   //Aplicar la pausa.
    	   try{Thread.sleep((lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );} //Puede sustituir el valor de la pausa por un valor fijo
    	   catch(Exception e){};
       }
    }
	@Override
	public void keyPressed(KeyEvent e) {
		 switch(e.getKeyCode()){
     	case KeyEvent.VK_LEFT:
     		presionoIzquierda=true;
     		break;
     	case KeyEvent.VK_RIGHT:
     		presionoDerecha=true;
     		break;
     	case KeyEvent.VK_UP:
     		presionoArriba=true;
     		break;
     	case KeyEvent.VK_DOWN:
     		presionoAbajo=true;
     		break;
     	case KeyEvent.VK_W:
     		presionoW=true;
     		jugador.setLlaveImagen("AtaqueArriba");
     		jugador.disminuirY();
     		break;
     	case KeyEvent.VK_S:
     		presionoS=true;
     		jugador.setLlaveImagen("AtaqueAbajo");
     		jugador.aumentarY();
     		break;
     	case KeyEvent.VK_A:
     		presionoA=true;
     		jugador.setLlaveImagen("AtaqueIzquierda");
     		jugador.disminuirX();
     		break;
     	case KeyEvent.VK_D:
     		presionoD=true;
     		jugador.setLlaveImagen("AtaqueDerecha");
     		jugador.aumentarX();
     		break;
		 }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
        case KeyEvent.VK_LEFT:
    		presionoIzquierda=false;
    		break;
    	case KeyEvent.VK_RIGHT:
    		presionoDerecha=false;
        		break;
    	case KeyEvent.VK_UP:
    		presionoArriba=false;
    		break;
    	case KeyEvent.VK_DOWN:
    		presionoAbajo=false;
    		break;
    	case KeyEvent.VK_W:
     		presionoW=false;
     		jugador.setLlaveImagen("espalda1");
     	break;
    case KeyEvent.VK_S:
     		presionoS=false;
     		jugador.setLlaveImagen("frente1");
     	break;
     case KeyEvent.VK_A:
     		presionoA=false;
     		jugador.setLlaveImagen("izquierda1");
     	break;
     case KeyEvent.VK_D:
     		presionoD=false;
     		jugador.setLlaveImagen("derecha1");
     	break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	public static void main(String[] args) {
		new JuegoMain();
	}

}
