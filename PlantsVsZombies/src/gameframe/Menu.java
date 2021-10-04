package gameframe;

import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import audioplayer.MP3;
import database.AddPlayerDemo;
import database.SelectDemo;
import database.ShowPlayerDemo;
import database.TruncatePlayerDemo;
import main.Main;

public class Menu extends JFrame implements ActionListener {

	private GridBagLayout layout;
	private GridBagConstraints gbc;
	private ImageIcon imageRank;
	private JPanel rankPanel;
	private JLabel rankTitle[];
	private JLabel rank[][];

	private SelectDemo getData;
	public static AddPlayerDemo addData;
	private ShowPlayerDemo showData;
	private TruncatePlayerDemo truncateData;

	private int GameID = 0;
	private JButton start;
	
	private JButton truncate;
	private ImageIcon imageTruncate;
			
	private ImageIcon imageStart;
	private JTextField name;
	private JLabel enterName;
	private Thread gameAudio;
	private MP3 mp3;
	private String filename;
	String username = System.getProperty("user.dir");

	// private GridBagConstraints gbc;

	public Menu() {
		super();
		setLayout(new BorderLayout());

		getData = new SelectDemo();
		showData = new ShowPlayerDemo();

		setBack(); // 調用背景方法
		Container c = getContentPane(); // 獲取JFrame面板
		c.setLayout(null);
		layout = new GridBagLayout();
		layout.columnWidths = new int[] {70, 70, 140, 85, 85};
	   	layout.rowHeights = new int[] {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};
	   	
		imageRank = new ImageIcon(username + "/images/frame/rankBackground.jpg");

		rankPanel = new JPanel(layout) {
			protected void paintComponent(Graphics g) {
				g.drawImage(imageRank.getImage(), 0, 0, rankPanel.getWidth(), rankPanel.getHeight(), this);
			}
		};
		
		rankPanel.setOpaque(false);
		rankPanel.setSize(500, 550);
		rankPanel.setLocation(600, 250);

		gbc = new GridBagConstraints();
		
		rankTitle = new JLabel[6];
		rankTitle[0] = new JLabel("Rank");
		rankTitle[1] = new JLabel("ID");
		rankTitle[2] = new JLabel("Name");
		rankTitle[3] = new JLabel("Score");
		rankTitle[4] = new JLabel("Sun");
		for(int i = 0; i < 5; i++) {
			rankTitle[i].setFont(new Font("Dialog", 1, 20));
			rankTitle[i].setForeground(Color.WHITE);
			gbc.insets = new Insets(5, 5, 5, 5); 
			gbc.gridx = i;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.fill = GridBagConstraints.CENTER;
			rankPanel.add(rankTitle[i], gbc);
		}
		
		rank = new JLabel[10][5];
		int GameID = 0;
		String Name = null;
		String Score = null;
		String SunScore = null;
		String st = null;
		for (int i = 0; i < 10; i++) {
			try {
				if (showData.rs.next()) {
					GameID = showData.rs.getInt(2);
					Name = showData.rs.getString(3);
					Score = showData.rs.getString(4);
					SunScore = showData.rs.getString(5);
					for (int j = 0; j < 5; j++) {
						if(j == 0) {
							st = String.format("   %02d", i + 1);
						} else if (j == 1) {
							st = String.format("  %04d", GameID);
						} else if (j == 2) {
							st = String.format("    %8s", Name);
						} else if (j == 3) {
							st = String.format("   %3s", Score);
						} else {
							st = String.format("   %3s", SunScore);
						}
						rank[i][j] = new JLabel(st);
						rank[i][j].setFont(new Font("", 0, 20));
						if(i == 0 || i == 1 || i == 2) {
							rank[i][j].setForeground(Color.YELLOW);
						}else {
							rank[i][j].setForeground(Color.WHITE);
						}
						gbc.insets = new Insets(5, 5, 5, 5); 
						gbc.gridx = j;
						gbc.gridy = i + 1;
						gbc.gridwidth = 2;
						gbc.gridheight = 1;
						gbc.fill = GridBagConstraints.BOTH;
						rankPanel.add(rank[i][j], gbc);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		c.add(rankPanel);

		enterName = new JLabel("Enter Name");
		enterName.setSize(150, 50);
		enterName.setLocation(300, 850);
		enterName.setFont(new Font("Serif", 0, 30));
		enterName.setBackground(new Color(255, 236, 139));
		enterName.setOpaque(true);
		c.add(enterName);

		name = new JTextField();
		name.setSize(100, 50);
		name.setLocation(450, 850);
		name.setFont(new Font("Serif", 0, 20));
		c.add(name);
		// name.addActionListener(this);

		imageTruncate = new ImageIcon(username + "/images/frame/Delete.png");
		truncate = new JButton(imageTruncate);
		truncate.setOpaque(false);
		truncate.setLocation(800, 225);
		truncate.setSize(imageTruncate.getIconWidth(), imageTruncate.getIconHeight());
		truncate.addActionListener(this);
		c.add(truncate);
		
		imageStart = new ImageIcon(username + "/images/frame/start.gif");
		start = new JButton(imageStart);
		start.setLocation(600, 800);
		start.setContentAreaFilled(false);
		start.setOpaque(false);
		start.setSize(imageStart.getIconWidth(), imageStart.getIconHeight()); // 按鈕大小
		start.addActionListener(this);
		c.add(start);

		gameAudio = new Thread() {
			public void run() {

				filename = "sounds/menu.mp3";

				// int returnValue = jfc.showSaveDialog(null);

				mp3 = new MP3(filename);
				mp3.setLoop(true);
				mp3.play();
			}
		};
		gameAudio.start();

	}

	public void setBack() {
		((JPanel) this.getContentPane()).setOpaque(false);
		ImageIcon img = new ImageIcon(username + "/images/frame/p1.jpg");
		JLabel background = new JLabel(img);
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start || e.getSource() == name) {
			try {
				if (getData.rs.next()) {
					GameID = getData.rs.getInt(1);
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			GameID++;
			System.out.println("Your GameID : " + GameID);
			String gameName = name.getText();
			System.out.println("Your Name : " + gameName);
			addData = new AddPlayerDemo();
			try {
				addData.sql.setInt(1, GameID);
				addData.sql.setString(2, gameName);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mp3.stop();
			Main.game();
		}else if(e.getSource() == truncate) {
			truncateData = new TruncatePlayerDemo();
			String sql = "truncate player";
			try {
			    int reply = JOptionPane.showConfirmDialog(null, "\n" + 
			    		"All data will be deleted, are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	truncateData.stmt.executeUpdate(sql);
		        }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
