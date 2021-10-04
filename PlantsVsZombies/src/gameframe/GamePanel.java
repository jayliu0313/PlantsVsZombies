package gameframe;

import javax.swing.*;

import audioplayer.MP3;
import collider.Collider;

import plants.Pea;
import plants.Peashooter;
import plants.Plant;
import plants.Potato;
import plants.Sun;
import plants.Sunflower;
import zombies.ConeHeadZombie;
import zombies.NormalZombie;
import zombies.Zombie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JLayeredPane implements MouseMotionListener, MouseListener {

	String username = System.getProperty("user.dir");

	private ImageIcon peashooterImage;
	private ImageIcon sunflowerImage;
	private ImageIcon potatoImage;
	private ImageIcon peaImage;

	private ImageIcon normalZombieImage;
	private ImageIcon coneHeadZombieImage;
	private Collider[] colliders;

	private ArrayList<ArrayList<Zombie>> laneZombies;
	private ArrayList<ArrayList<Pea>> lanePeas;
	private ArrayList<Sun> activeSuns;

	private Timer redrawTimer;
	private Timer advancerTimer;
	private Timer sunProducer;
	private Timer zombieProducer;

	private JLabel sunScoreboard;

	public static String[][] LEVEL_CONTENT = { { "NormalZombie" }, { "NormalZombie", "ConeHeadZombie" } };
	public static int[][][] LEVEL_VALUE = { { { 0, 99 } }, { { 0, 49 }, { 50, 99 } }, { { 0, 19 }, { 20, 100 } } };

	public Thread gameAudio1;
	private Thread gameAudio2;
	private MP3 mp3;
	private String filename;

	private GameWindow.PlantType activePlantingBrush = GameWindow.PlantType.None;

	private int mouseX, mouseY;

	private final int MAX = 100;
	private int[] checkX;
	private int[] checkY;

	private int sunScore;

	public int getSunScore() {
		return sunScore;
	}

	public void setSunScore(int sunScore) {
		this.sunScore = sunScore;
		sunScoreboard.setText(String.valueOf(sunScore));
	}
	
	public int getProgress() {
		return progress;
	}

	public GamePanel(JLabel sunScoreboard) {

		setSize(1460, 1000);
		setLayout(null);
		addMouseMotionListener(this);
		addMouseListener(this);

		this.sunScoreboard = sunScoreboard;
		setSunScore(125); // set sunScore initial value

		checkX = new int[MAX];
		checkY = new int[MAX];

		peashooterImage = new ImageIcon(username + "/images/plants/pea_shooter.gif");
		sunflowerImage = new ImageIcon(username + "/images/plants/sun_flower.gif");
		potatoImage = new ImageIcon(username + "/images/plants/walnut_full_life.gif");
		peaImage = new ImageIcon(username + "/images/plants/Pea.png");

		normalZombieImage = new ImageIcon(username + "/images/zombies/zombie_normal.gif");
		coneHeadZombieImage = new ImageIcon(username + "/images/zombies/zombie_football.gif");

		laneZombies = new ArrayList<>();
		laneZombies.add(new ArrayList<>()); // line 1
		laneZombies.add(new ArrayList<>()); // line 2
		laneZombies.add(new ArrayList<>()); // line 3
		laneZombies.add(new ArrayList<>()); // line 4
		laneZombies.add(new ArrayList<>()); // line 5

		lanePeas = new ArrayList<>();
		lanePeas.add(new ArrayList<>()); // line 1
		lanePeas.add(new ArrayList<>()); // line 2
		lanePeas.add(new ArrayList<>()); // line 3
		lanePeas.add(new ArrayList<>()); // line 4
		lanePeas.add(new ArrayList<>()); // line 5

		colliders = new Collider[45];

		for (int i = 0; i < 45; i++) {
			Collider a = new Collider();
			a.setLocation(360 + (i % 9) * 115, 0 + (i / 9) * 158);
			a.setAction(new PlantActionListener((i % 9), (i / 9)));
			colliders[i] = a;
			add(a, new Integer(0));
		}

		gameAudio1 = new Thread() {
			public void run() {
				filename = "sounds/background.mp3";
				// int returnValue = jfc.showSaveDialog(null);
				mp3 = new MP3(filename);
				mp3.setLoop(true);
				mp3.play();
			}
		};
		gameAudio1.start();

		gameAudio2 = new Thread() {
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				filename = "sounds/zombies_coming.mp3";
				// int returnValue = jfc.showSaveDialog(null);
				mp3 = new MP3(filename);
				mp3.setLoop(false);
				mp3.play();
			}
		};
		gameAudio2.start();

		// colliders[0].setPlant(new FreezePeashooter(this,0,0));
		/*
		 * colliders[9].setPlant(new Peashooter(this,0,1)); laneZombies.get(1).add(new
		 * NormalZombie(this,1));
		 */

		activeSuns = new ArrayList<>();

		redrawTimer = new Timer(25, (ActionEvent e) -> {
			repaint();
		});
		redrawTimer.start();

		advancerTimer = new Timer(60, (ActionEvent e) -> advance());
		advancerTimer.start();

		sunProducer = new Timer(6000, (ActionEvent e) -> {
			Random rnd = new Random();
			Sun sta = new Sun(this, rnd.nextInt(1100) + 300, 0, rnd.nextInt(600) + 200);
			activeSuns.add(sta);
			add(sta, new Integer(1));
			
		});
		sunProducer.start();

		zombieProducer = new Timer(7000, (ActionEvent e) -> {
			Random rnd = new Random();
			int[][] LevelValue;
			String[] Level;

			if (progress < 50) {
				Level = LEVEL_CONTENT[0];
				LevelValue = LEVEL_VALUE[0];
			} else if (progress < 100) {
				Level = LEVEL_CONTENT[1];
				LevelValue = LEVEL_VALUE[1];
			} else {
				Level = LEVEL_CONTENT[1];
				LevelValue = LEVEL_VALUE[2];
			}

			int l = rnd.nextInt(5);
			int t = rnd.nextInt(100);
			Zombie z = null;
			for (int i = 0; i < Level.length; i++) {
				System.out.println(Level.length);
				if (t >= LevelValue[i][0] && t <= LevelValue[i][1]) {
					z = Zombie.getZombie(Level[i], GamePanel.this, l);
				}
			}
			laneZombies.get(l).add(z);
		});

		zombieProducer.start();
		
	}

	private void advance() {
		for (int i = 0; i < 5; i++) {
			for (Zombie z : laneZombies.get(i)) {
				z.advance();
			}

			for (int j = 0; j < lanePeas.get(i).size(); j++) {
				Pea p = lanePeas.get(i).get(j);
				p.advance();
			}

		}

		for (int i = 0; i < activeSuns.size(); i++) {
			activeSuns.get(i).advance();
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw Plants
		for (int i = 0; i < 45; i++) {
			Collider c = colliders[i];
			if (c.assignedPlant != null) {
				Plant p = c.assignedPlant;
				if (p instanceof Peashooter) {
					g.drawImage(peashooterImage.getImage(), 390 + (i % 9) * 115, 50 + (i / 9) * 158, null);
				}
				if (p instanceof Sunflower) {
					g.drawImage(sunflowerImage.getImage(), 390 + (i % 9) * 115, 50 + (i / 9) * 158, null);
				}
				if (p instanceof Potato) {
					g.drawImage(potatoImage.getImage(), 390 + (i % 9) * 115, 50 + (i / 9) * 158, null);
				}
			}
		}

		for (int i = 0; i < 5; i++) {
			for (Zombie z : laneZombies.get(i)) {
				if (z instanceof NormalZombie) {
					g.drawImage(normalZombieImage.getImage(), z.getPosX(), 0 + (i * 158), null);
				} else if (z instanceof ConeHeadZombie) {
					g.drawImage(coneHeadZombieImage.getImage(), z.getPosX(), 0 + (i * 158), null);
				}
			}

			for (int j = 0; j < lanePeas.get(i).size(); j++) {
				Pea pea = lanePeas.get(i).get(j);
				if (pea instanceof Pea) {
					g.drawImage(peaImage.getImage(), pea.getPosX() + 30, 55 + (i * 160), null);
				}
			}

		}

		// if(!"".equals(activePlantingBrush)){
		// System.out.println(activePlantingBrush);
		/*
		 * if(activePlantingBrush == GameWindow.PlantType.Sunflower) {
		 * g.drawImage(sunflowerImage,mouseX,mouseY,null); }
		 */

		// }

	}

	private class PlantActionListener implements ActionListener {

		int x, y;

		public PlantActionListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (colliders[x + y * 9].assignedPlant == null) {
				if (activePlantingBrush == GameWindow.PlantType.Sunflower) {
					if (getSunScore() >= 50) {
						colliders[x + y * 9].setPlant(new Sunflower(GamePanel.this, x, y));
						setSunScore(getSunScore() - 50);
					}
				}
				if (activePlantingBrush == GameWindow.PlantType.Peashooter) {
					if (getSunScore() >= 100) {
						colliders[x + y * 9].setPlant(new Peashooter(GamePanel.this, x, y));
						setSunScore(getSunScore() - 100);
					}
				}
				if (activePlantingBrush == GameWindow.PlantType.Potato) {
					if (getSunScore() >= 25) {
						colliders[x + y * 9].setPlant(new Potato(GamePanel.this, x, y));
						setSunScore(getSunScore() - 25);
					}
				}

			} else {
				System.out.println("This place already has plants.");
			}

			activePlantingBrush = GameWindow.PlantType.None;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x, y;
		x = e.getX();
		y = e.getY();
		System.out.println(x + ", " + y);

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	static int progress = 0;

	public static void setProgress(int num) {
		progress = progress + num;
		System.out.println(progress);
	}

	public GameWindow.PlantType getActivePlantingBrush() {
		return activePlantingBrush;
	}

	public void setActivePlantingBrush(GameWindow.PlantType activePlantingBrush) {
		this.activePlantingBrush = activePlantingBrush;
	}

	public ArrayList<ArrayList<Zombie>> getLaneZombies() {
		return laneZombies;
	}

	public void setLaneZombies(ArrayList<ArrayList<Zombie>> laneZombies) {
		this.laneZombies = laneZombies;
	}

	public ArrayList<ArrayList<Pea>> getLanePeas() {
		return lanePeas;
	}

	public void setLanePeas(ArrayList<ArrayList<Pea>> lanePeas) {
		this.lanePeas = lanePeas;
	}

	public ArrayList<Sun> getActiveSuns() {
		return activeSuns;
	}

	public void setActiveSuns(ArrayList<Sun> activeSuns) {
		this.activeSuns = activeSuns;
	}

	public Collider[] getColliders() {
		return colliders;
	}

	public void setColliders(Collider[] colliders) {
		this.colliders = colliders;
	}

}
