package zombies;

import gameframe.GamePanel;


public class ConeHeadZombie extends Zombie {

	public ConeHeadZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setHealth(2000);
    }
}
