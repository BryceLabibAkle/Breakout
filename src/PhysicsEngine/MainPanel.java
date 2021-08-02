package PhysicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel implements ActionListener, KeyListener, MouseListener{
	
	// Window States
	final static int MENU = 0;
	final static int WORLD = 1;
	final static int GAME_OVER = 2;
	static int currentState = WORLD;
	
	// Colors
	Color blackColor = new Color(0, 0, 0);
	
	// FrameRate
	static int fps = 65;
	Timer frameDraw;
	boolean optimisedFrameRatesActive;
	static float secondsPassed = 0;
	static float gravityPowerupDurationSeconds;
	static float gameDuration = 65; 
	
	// Window Text
	
	// Controls Keys
	int moveRacketRightKey = 39;
	int moveRacketLeftKey = 37;
	
	// Other
	ObjectManager objectManager = new ObjectManager();
	
	public MainPanel() {
		setFps(fps);
	}
	
	// Handles Graphics
	@Override
	public void paintComponent(Graphics g) {
		switch (currentState) {
		case MENU:
			drawMenuState(g);
			break;

		case WORLD:
			drawWorldState(g);
			break;
		case GAME_OVER:
			drawGameOverState(g);
			break;
		default:
			System.out.println("Unknown state: " + currentState);
			break;
		}
	}
	
	void drawMenuState(Graphics g) {
		
	}
	
	void drawWorldState(Graphics g) {
		g.setColor(blackColor);
		g.fillRect(0, 0, ScreenManager.windowSize.width, ScreenManager.windowSize.height);
		
		objectManager.drawObjects(g);
		
		// Sets the fps to 30 if the amount of Objects on the screen exceeds 10
		//System.out.println(objectManager.getObjectCount());
		if ((objectManager.getGravityObjectCount() > 10) && (optimisedFrameRatesActive == false)) {
			optimisedFrameRatesActive = true;
			System.out.println("30 fps");
			setFps(30);
		}
		// Sets the fps back to 60 if the objectCount is under 11
		else if ((objectManager.getGravityObjectCount() < 11) && (optimisedFrameRatesActive == true)) {
			optimisedFrameRatesActive = false;
			System.out.println("60 fps");
			setFps(60);
		}
	}
	
	
	void drawGameOverState(Graphics g) {
		g.setColor(blackColor);
		g.fillRect(0, 0, ScreenManager.windowSize.width, ScreenManager.windowSize.height);
		
		ObjectManager.addEndGameText();
		ObjectManager.addRacketObject();
		
		objectManager.drawObjects(g);
	}
	
	void updateMenuState() {
		drawMenuState(null);
		currentState = MENU;
	}
	
	void updateWorldState() {
		currentState = WORLD;
		
		// Updates what's happening to all the objects (makes them move and things happen to them)

		objectManager.updateObjects();
	}
	
	static void updateGameOverState() {
		currentState = GAME_OVER;
	}
	
	void setFps(int fps) {
		MainPanel.fps = fps;
		frameDraw = new Timer(1000/fps, this);
		frameDraw.start();
	}
	
	// ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
//		secondsPassed += 1;
//		System.out.println(secondsPassed/(fps/2));
				
		switch (currentState) {
		case MENU:
			updateMenuState();
			break;
		case WORLD:
			updateWorldState();
			break;
		}
		// Makes sure it paints over the next frame
		repaint();  // ALWAYS HAVE THIS (it doesn't hurt having it)
	}
	
	// KeyListener
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();
		// Debugging
		if (keyPressed == KeyEvent.VK_ENTER) {
			objectManager.addBallObject();
		}
		else if (keyPressed == KeyEvent.VK_SHIFT) {
			objectManager.ballObjects.remove(0);
		}
		else if (keyPressed == KeyEvent.VK_QUOTE) {
			objectManager.addBoxObject();
		}
		
		// Racket Controls
		else if (keyPressed == moveRacketRightKey) {
			objectManager.racketObject.movingRight();
		}
		else if (keyPressed == moveRacketLeftKey) {
			objectManager.racketObject.movingLeft();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Coordinates of the mouse relative to the game window
	    objectManager.mouseX = e.getX();
	    objectManager.mouseY = e.getY();
	    objectManager.addBallObject();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
