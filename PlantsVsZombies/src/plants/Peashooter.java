package plants;

import javax.swing.*;

import audioplayer.MP3;
import gameframe.GamePanel;

import java.awt.event.ActionEvent;


public class Peashooter extends Plant {

	
	public Timer shootTimer;
	private Thread gameAudio;
	private MP3 mp3;
	private String filename;

	public Peashooter(GamePanel parent, int x, int y) {
		super(parent, x, y);
		shootTimer = new Timer(2000, (ActionEvent e) -> {
			// System.out.println("SHOOT");
			if (getGp().getLaneZombies().get(y).size() > 0) {
				getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 430 + this.getX() * 115));
				gameAudio = new Thread() {
					public void run() {
						filename = "sounds/laser.mp3";
						// int returnValue = jfc.showSaveDialog(null);
						mp3 = new MP3(filename);
						mp3.setLoop(false);
						mp3.play();
					}
				};
				gameAudio.start();
			}
		});
		shootTimer.start();
	}

	@Override
	public void stop() {
		shootTimer.stop();
	}

}
