package main;

import javax.swing.JFrame;

import gameframe.GameWindow;
import gameframe.Menu;

public class Main{
	
	//private static boolean stat=false;
	private static Menu menu;
	public static GameWindow gframe;
	public static void main(String[] args) {
		
		menu = new Menu();
		setFrame(menu);
	}
	
	public static void game() {
		menu.dispose();
		gframe = new GameWindow();
		setFrame(gframe);
	}
	
	public static void setFrame(JFrame frame) {
		frame.setTitle("Plants Vs Zombies");
		frame.setLocation(200, 50);
		frame.setSize(1460, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true); 
	}
	
}
