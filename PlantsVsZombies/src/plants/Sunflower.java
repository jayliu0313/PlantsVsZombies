package plants;
import javax.swing.*;

import gameframe.GamePanel;

import java.awt.event.ActionEvent;


public class Sunflower extends Plant {

    private Timer sunProduceTimer;
    

    public Sunflower(GamePanel parent, int x, int y) {
        super(parent, x, y);
        sunProduceTimer = new Timer(15000, (ActionEvent e) -> {
            Sun sta = new Sun(getGp(), 400 + x * 115, 30 + y * 158, 50 + y * 158);
            getGp().getActiveSuns().add(sta);
            getGp().add(sta, new Integer(1));
            
        });
        sunProduceTimer.start();
    }

}
