package plants;
import javax.swing.*;

import gameframe.GamePanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Sun extends JPanel implements MouseListener {

	String username = System.getProperty("user.dir");
	
    private GamePanel gp;
    private ImageIcon sunImage;

    private int myX;
    private int myY;
    private int endY;

    private int destruct = 200;

    public Sun(GamePanel parent, int startX, int startY, int endY) {
        this.gp = parent;
        this.endY = endY;
        setSize(100, 100);
        setOpaque(false);
        myX = startX;
        myY = startY;
        setLocation(myX, myY);
        sunImage = new ImageIcon(username + "/images/sun.gif");
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sunImage.getImage(), 0, 0, null);
    }

    public void advance() {
        if (myY < endY) {
            myY += 4;
        } else {
            destruct--;
            if (destruct < 0) {
                gp.remove(this);
                gp.getActiveSuns().remove(this);
            }
        }
        setLocation(myX, myY);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gp.setSunScore(gp.getSunScore() + 25);
        gp.remove(this);
        gp.getActiveSuns().remove(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
