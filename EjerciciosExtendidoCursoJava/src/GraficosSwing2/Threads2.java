package GraficosSwing;
import java.awt.geom.*;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class Threads2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame marco=new MarcoRebote();
		
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		marco.setVisible(true);
	}

}
class Pelota{
	
	// Mueve la pelota invirtiendo posici�n si choca con l�mites
	
	public void mueve_pelota(Rectangle2D limites){
		
		x+=dx;
		
		y+=dy;
		
		if(x<limites.getMinX()){
			
			x=limites.getMinX();
			
			dx=-dx;
		}
		
		if(x + TAMX>=limites.getMaxX()){
			
			x=limites.getMaxX() - TAMX;
			
			dx=-dx;
		}
		
		if(y<limites.getMinY()){
			
			y=limites.getMinY();
			
			dy=-dy;
		}
		
		if(y + TAMY>=limites.getMaxY()){
			
			y=limites.getMaxY()-TAMY;
			
			dy=-dy;
			
		}
		
	}
	
	//Forma de la pelota en su posici�n inicial
	
	public Ellipse2D getShape(){
		
		return new Ellipse2D.Double(x,y,TAMX,TAMY);
		
	}	
	
	private static final int TAMX=15;
	
	private static final int TAMY=15;
	
	private double x=0;
	
	private double y=0;
	
	private double dx=1;
	
	private double dy=1;
	
	
}

// L�mina que dibuja las pelotas----------------------------------------------------------------------


class LaminaPelota extends JPanel{
	
	//A�adimos pelota a la l�mina
	
	public void add(Pelota b){
		
		pelotas.add(b);
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;
		
		for(Pelota b: pelotas){
			
			g2.fill(b.getShape());
		}
		
	}
	
	private ArrayList<Pelota> pelotas=new ArrayList<Pelota>();
}


//Marco con l�mina y botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame{
	
	public MarcoRebote(){
		
		setBounds(600,300,600,350);
		
		setTitle ("Rebotes");
		
		lamina=new LaminaPelota();
		
		add(lamina, BorderLayout.CENTER);
		
		JPanel laminaBotones=new JPanel();
		
	/*	ponerBoton(laminaBotones, "Detener1", new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				detener(e);
			}
			
		});
		
		ponerBoton(laminaBotones,"Detener2",new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				detener(e);
			}
		});
		
		ponerBoton(laminaBotones, "Detener3", new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				detener(e);
				
			}
			
		});*/
		Hilo1=new JButton("Arrancar1");
		Hilo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				comienza_el_juego(e);
			}
		});
		laminaBotones.add(Hilo1);
		Hilo2=new JButton("Arrancar2");
		Hilo2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				comienza_el_juego(e);
			}
		});
		laminaBotones.add(Hilo2);
		Hilo3=new JButton("Arrancar3");
		Hilo3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				comienza_el_juego(e);
			}
		});
		laminaBotones.add(Hilo3);
		Detener1=new JButton("Detener1");
		Detener1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				detener(e);
			}
		});
		laminaBotones.add(Detener1);
		Detener2=new JButton("Detener2");
		Detener2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				detener(e);
			}
		});
		laminaBotones.add(Detener2);
		Detener3=new JButton("Detener3");
		Detener3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				detener(e);
			}
		});
		laminaBotones.add(Detener3);
		
		add(laminaBotones, BorderLayout.SOUTH);
	}
	//Metodo para interrumpir el programa
	void detener(ActionEvent e) {
		
		if(e.getSource().equals(Detener1)) {
		t1.interrupt();
		}else if(e.getSource().equals(Detener2)) {
			t2.interrupt();
		}else if(e.getSource().equals(Detener3)) {
			t3.interrupt();
		}
	}
	
	//Ponemos botones
	
	public void ponerBoton(Container c, String titulo, ActionListener oyente){
		
		JButton boton=new JButton(titulo);
		
		c.add(boton);
		
		boton.addActionListener(oyente);
		
	}
	
	//A�ade pelota y la bota 1000 veces
	
	public void comienza_el_juego (ActionEvent e){
		
					
			Pelota pelota=new Pelota();
			
			lamina.add(pelota);
			
			Runnable r=new pelotaHilo(pelota,lamina);
			if(e.getSource().equals(Hilo1)) {
			t1=new Thread(r);
			t1.start();
			}else if(e.getSource().equals(Hilo2)) {
			t2=new Thread(r);
			t2.start();
			}else if(e.getSource().equals(Hilo3)) {
				t3=new Thread(r);
				t3.start();
				}
			
		
	}
	Thread t1,t2,t3;
	JButton Hilo1,Hilo2,Hilo3,Detener1,Detener2,Detener3;
	
	private LaminaPelota lamina;
	
	
}
class pelotaHilo implements Runnable{
		pelotaHilo(Pelota pelota, Component lamina){
			mipelota=pelota;
			this.lamina=lamina;
		}
	@Override
	public void run() {
		
		//for (int i=1; i<=3000; i++){
			while(!Thread.currentThread().isInterrupted()) {
			mipelota.mueve_pelota(lamina.getBounds());
			
			lamina.paint(lamina.getGraphics());
			
			try {
				Thread.sleep(4);
			}catch(Exception e) {
				Thread.currentThread().interrupt();
				System.out.println("El sleep fue interrumpido");
				
			}
			
		}
		
	}
	Component lamina;
	Pelota mipelota;
}