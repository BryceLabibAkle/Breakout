package PhysicsEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PhysicsEngine {
	
	// Swing Widgets
	static JFrame window;
	MainPanel mainPanel = new MainPanel();
//	JPanel scorePanel = new JPanel();
//	JLabel scoreLabel = new JLabel();
	
	// Swing Widget Colors
	Color scoreLabelColor = new Color(255, 255, 255, 150);
	
	PhysicsEngine() {
		PhysicsEngine.window = new JFrame();
		new ScreenManager(50);
	}
	
	public static void main(String[] args) {
		new PhysicsEngine().startup();
		new ScreenManager((float) 50.0);
		System.out.println();
	}
	
	// Needs startup function to work
	void startup() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addKeyListener(mainPanel);  // Tells MainPanel to process a Key action
		window.addMouseListener(mainPanel);  // Tells MainPanel to process a Mouse action
		window.setVisible(true);
		
//		int scoreLabelX = 0;
//		int scoreLabelY = scoreLabelX;
//		int scoreLabelWidth = 50;
//		int scoreLabelHeight = 50;
//		scoreLabel.setBounds(scoreLabelX, scoreLabelY, scoreLabelWidth, scoreLabelHeight);
//		scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
//		scoreLabel.setVerticalAlignment(SwingConstants.TOP);
//		scoreLabel.setBackground(scoreLabelColor);
//		scoreLabel.setText("" + ScoreManagerObject.score);
//		scorePanel.add(scoreLabel);
//		mainPanel.add(scorePanel);
		
		mainPanel.setPreferredSize(ScreenManager.windowSize);
		window.add(mainPanel);
		
		window.pack();  // Enable/Disable if you have issues 
	}
}
