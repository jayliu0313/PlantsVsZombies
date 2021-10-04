package plants;
import java.awt.*;

import audioplayer.MP3;
import collider.Collider;
import gameframe.GamePanel;
import zombies.Zombie;


public class Pea {

    private int posX;
    protected GamePanel gp;
    private int myLane;
    private Thread gameAudio2;
    private MP3 mp3;
    private String filename;

    
    public Pea(GamePanel parent, int lane, int startX) {
        this.gp = parent;
        this.myLane = lane;
        posX = startX;
    }

    public void advance() {
        Rectangle pRect = new Rectangle(posX, 50 + myLane * 158, 28, 28);
        for (int i = 0; i < gp.getLaneZombies().get(myLane).size(); i++) {
            Zombie z = gp.getLaneZombies().get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.getPosX(), 0 + myLane * 158, 280, 120);
            if (pRect.intersects(zRect)) {
                z.setHealth(z.getHealth() - 300);
                boolean exit = false;
                if (z.getHealth() < 0) {
                    System.out.println("ZOMBIE DIED");
                    gp.getLaneZombies().get(myLane).remove(i);
                    GamePanel.setProgress(10);
                    exit = true;
                }
                gp.getLanePeas().get(myLane).remove(this);
                if (exit) break;
            }
        }
        /*if(posX > 2000){
            gp.lanePeas.get(myLane).remove(this);
        }*/
        posX += 15;
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

	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
