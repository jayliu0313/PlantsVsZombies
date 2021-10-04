package zombies;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;

import audioplayer.MP3;
import collider.Collider;
import database.AddPlayerDemo;
import gameframe.GamePanel;
import gameframe.GameWindow;
import gameframe.Menu;
import plants.Sun;

public class Zombie {

	private int health = 1000;
	private int speed = 1;

	private GamePanel gp;
	private Thread gameAudio1;
	private Thread gameAudio2;
	private MP3 mp3;
	private String filename;

	private int posX = 1460;
	private int myLane;
	private boolean isMoving = true;

	public Zombie(GamePanel parent, int lane) {
		this.gp = parent;
		myLane = lane;
	}

	public void advance() {

		if (isMoving) {
			boolean isCollides = false;
			Collider collided = null;
			for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
				if (gp.getColliders()[i].assignedPlant != null && gp.getColliders()[i].isInsideCollider(posX)) {
					isCollides = true;
					collided = gp.getColliders()[i];
				}
			}
			if (!isCollides) {
				if (slowInt > 0) {
					if (slowInt % 2 == 0) {
						posX -= 4;
					}
					slowInt--;
				} else {
					posX -= 1;
					slow();
				}
			} else {

				gameAudio1 = new Thread() {
					public void run() {
						filename = "sounds/chomp.mp3";
						// int returnValue = jfc.showSaveDialog(null);
						mp3 = new MP3(filename);
						mp3.setLoop(false);
						mp3.play();
					}
				};
				if (collided.assignedPlant.getHealth() == 800 || collided.assignedPlant.getHealth() == 650
						|| collided.assignedPlant.getHealth() == 500 || collided.assignedPlant.getHealth() == 350
						|| collided.assignedPlant.getHealth() == 200 || collided.assignedPlant.getHealth() == 50) {
					gameAudio1.start();
				}

				collided.assignedPlant.setHealth(collided.assignedPlant.getHealth() - 10);
				if (collided.assignedPlant.getHealth() < 0) {
					collided.removePlant();
					mp3.stop();
				}
			}
			if (posX < 300) {
				try {
					Menu.addData.sql.setInt(3, gp.getProgress());
					Menu.addData.sql.setInt(4, gp.getSunScore());
					Menu.addData.sql.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gameAudio2 = new Thread() {
					public void run() {
						filename = "sounds/game_end.mp3";
						mp3 = new MP3(filename);
						mp3.setLoop(false);
						mp3.play();
					}
				};
				gameAudio2.start();
				isMoving = false;
				System.out.println("Your Score : " + gp.getProgress());
				System.out.println("Your SunScor : " + gp.getSunScore());
				JOptionPane.showMessageDialog(gp, "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Check your score");
				JOptionPane.showMessageDialog(gp, "Your Score : " + gp.getProgress() + '\n' + "Starting the level again");
				System.exit(0);
			}
		}
	}

	int slowInt = 30;

	public void slow() {
		slowInt = 30;
	}

	public static Zombie getZombie(String type, GamePanel parent, int lane) {
		Zombie z = new Zombie(parent, lane);
		switch (type) {
		case "NormalZombie":
			z = new NormalZombie(parent, lane);
			break;
		case "ConeHeadZombie":
			z = new ConeHeadZombie(parent, lane);
			break;
		}
		return z;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public GamePanel getGp() {
		return gp;
	}

	public void setGp(GamePanel gp) {
		this.gp = gp;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getMyLane() {
		return myLane;
	}

	public void setMyLane(int myLane) {
		this.myLane = myLane;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean moving) {
		isMoving = moving;
	}

	public int getSlowInt() {
		return slowInt;
	}

	public void setSlowInt(int slowInt) {
		this.slowInt = slowInt;
	}
}
