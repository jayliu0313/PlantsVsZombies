package gameframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import database.AddPlayerDemo;

public class GameWindow extends JFrame implements ActionListener {

    enum PlantType {
        None,
        Sunflower,
        Peashooter,
        Potato,
    }
	
    public static GameWindow gw;
    
	private GamePanel drawpanel;
	
	
	
	private ImageIcon img;
	
	private GridBagLayout layout;
	private JPanel topPanel;
	private GridBagConstraints gbc;
	
	private ImageIcon p_sunflower;
	private JButton sunFlower;
	private ImageIcon p_greenPea;
	private JButton peashooter;
	private ImageIcon p_bluePea;
	private JButton bluePea;
	private ImageIcon p_potato;
	private JButton potato;
	private ImageIcon p_lanmine;
	private JButton landmine;
	
	private JLabel sun;
	private JLabel sunBackground;
	private ImageIcon sunBImg;
	private ImageIcon sunImg;
	
	String username = System.getProperty("user.dir");

	

	public GameWindow() {
		super();
		
		Container c = getContentPane();

		sunBImg = new ImageIcon(username + "/images/frame/g_panel.png");
		
		sunImg = new ImageIcon(username + "/images/frame/Sun.png");
		
		sunBackground = new JLabel(sunBImg);
        sunBackground.setLocation(300, 0);
        sunBackground.setSize(200, 100);
        sunBackground.setOpaque(true);
        
		sun = new JLabel(sunImg);
        sun.setFont(new Font("SansSerif", Font.ITALIC, 50));
        sun.setOpaque(false);
        sun.setForeground(new Color(255, 255, 0));
        sun.setLocation(300, 0);
        sun.setSize(200, 100);
        
        c.add(sunBackground);
        c.add(sun);
        
		drawpanel = new GamePanel(sun);
		add(drawpanel, BorderLayout.CENTER);
		drawpanel.setOpaque(false);
		
		setBack(); 
		// 調用背景方法
		
		
		layout = new GridBagLayout();
		
		topPanel = new JPanel(layout) {
			protected void paintComponent(Graphics g) {  
				for(int i = 0; i < 3; i++) {
					g.drawImage(sunBImg.getImage(), sunFlower.getX() + i * sunFlower.getWidth(), sunFlower.getY(), sunFlower.getWidth(), sunFlower.getHeight(), this);  
				}
		    }
		};
		gbc = new GridBagConstraints();
		
		topPanel.setOpaque(false); // 把JPanel設置為透明 這樣就不會遮住後面的背景 這樣你就能在JPanel隨意加元件了
		
		
		p_sunflower = new ImageIcon(username + "/images/plants/active_sunflower.png");
		sunFlower = new JButton("", p_sunflower);
		sunFlower.setContentAreaFilled(false);
		sunFlower.setOpaque(false);
		setGridBagConstraints(1, 1, 1, 1, true);
		topPanel.add(sunFlower, gbc);
		sunFlower.addActionListener(this);

		p_greenPea = new ImageIcon(username + "/images/plants/active_peashooter.png");
		peashooter = new JButton("", p_greenPea);
		peashooter.setContentAreaFilled(false);
		peashooter.setOpaque(false);
		setGridBagConstraints(2, 1, 1, 1, true);
		topPanel.add(peashooter, gbc);
		peashooter.addActionListener(this);

		p_potato = new ImageIcon(username + "/images/plants/active_walnut.png");
		potato = new JButton("", p_potato);
		potato.setContentAreaFilled(false);
		potato.setOpaque(false);
		setGridBagConstraints(3, 1, 1, 1, true);
		topPanel.add(potato, gbc);
		potato.addActionListener(this);

		getLayeredPane().add(sun, new Integer(2));
		
		add(topPanel, BorderLayout.NORTH);
	}
	
	public void setGridBagConstraints(int x, int y, int h, int w, boolean fill) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridheight = h;
		gbc.gridwidth = w;
		if (fill)
			gbc.fill = GridBagConstraints.BOTH;
	}

	public void setBack() {
		((JPanel) this.getContentPane()).setOpaque(false);
		ImageIcon img = new ImageIcon(username + "/images/frame/Frontyard.png");
		JLabel background = new JLabel(img);
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == sunFlower) {
			drawpanel.setActivePlantingBrush(PlantType.Sunflower);
		} else if (e.getSource() == peashooter) {
			drawpanel.setActivePlantingBrush(PlantType.Peashooter);
		} else if (e.getSource() == potato) {
			drawpanel.setActivePlantingBrush(PlantType.Potato);
		}
	}

}

