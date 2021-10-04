package plants;

import audioplayer.MP3;
import gameframe.GamePanel;


public abstract class Plant {

    private int health = 400;
 

    private int x;
    private int y;

    private GamePanel gp;


    public Plant(GamePanel parent, int x, int y) {
        this.x = x;
        this.y = y;
        gp = parent;
    }

    public void stop() {
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }
 
}
