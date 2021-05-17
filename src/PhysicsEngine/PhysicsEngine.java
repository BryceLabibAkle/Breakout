package PhysicsEngine;

import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PhysicsEngine {
	static ScreenTools screenTools = new ScreenTools(50);
	
	// Swing Widgets
	static JFrame window;
	MainPanel mainPanel = new MainPanel();
	
	PhysicsEngine() {
		PhysicsEngine.window = new JFrame();
	}
	
	public static void main(String[] args) {
		new PhysicsEngine().startup();
		System.out.println();
	}
	
	// Needs startup function to work
	void startup() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.addKeyListener(mainPanel);  // Tells MainPanel to process a Key action
		window.addMouseListener(mainPanel);  // Tells MainPanel to process a Mouse action
		window.setVisible(true);
		
		mainPanel.setPreferredSize(ScreenTools.windowSize);
		window.add(mainPanel);
		
		window.pack();  // Enable/Disable if you have issues 
	}
}
