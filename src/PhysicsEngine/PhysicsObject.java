package PhysicsEngine;

import java.awt.Rectangle;

public class PhysicsObject extends FatherOfObjects{
	double gravity;
	double mass;
	double velocityX;
	double velocityY;
	boolean hasPhysics;
	
	PhysicsObject(double x, double y, int width, int height, double gravity, double mass, int velocityX, int velocityY) {
		super(x, y, width, height);
		this.gravity = gravity;
		this.mass = mass;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.isActive = true;
		this.hasPhysics = true;
		this.needImage = true;
		
		this.collisionBox = new Rectangle((int) x, (int) y, width, height);
	}
	
	void update() {
		if (isActive == true) {
			updatePosition();
			this.collisionBox.setBounds((int) x, (int) y, width, height);
		}
	 }
	
	void updatePosition() {
		if (hasPhysics) {
			// Checks if the Object has hit the corners of the wall
			// Vertical (Walls)
			if ((getLeftSideLocation() < 0) || (getRightSideLocation() > ScreenManager.windowSize.width)) {
				velocityX = -1*(velocityX / mass);
			}
			// Horizontal (Ceiling and Floor)
			if ((getTopSideLocation() < 0) || (getBottomSideLocation() > ScreenManager.windowSize.height)) {
				velocityY = -1*(velocityY / mass);
			}
			
			// Check if the ballObject left the edges of the screen
			// If ballObject left the top side of the screen
			if (getTopSideLocation() < 0) {
				hitTopOfScreen();
			}
			// If ballObject left the right side of the screen
			else if (getRightSideLocation() > ScreenManager.windowSize.width) {
				hitRightOfScreen();
			}
			// If ballObject left the bottom side of the screen
			else if (getBottomSideLocation() > ScreenManager.windowSize.height) {
				hitBottomOfScreen();
			}
			// If ballObject left the left side of the screen
			else if (getLeftSideLocation() < 0) {
				hitLeftOfScreen();
			}
			
			// Increases velocitY (gravity pushing the object down)
			velocityY += gravity;
			
			// Moves the object (updates the position)
			x += velocityX;
			y += velocityY;
			
			// Slows the object's X velocity so that it doesn't roll forever
			//velocityX = velocityX*(0.999-(gravity/1000));
			
			// Sets the maximum velocity
			if (Math.abs(velocityX) > 15) {
				velocityX /= 1.05;
			}
			if (Math.abs(velocityY) > 15) {
				velocityY /= 1.05;
			}
			
//			System.out.println("X: " + x + "\n"
//							 + "Y: " + y + "\n"
//							 + "X VELO: " + velocityX + "\n"
//							 + "Y VELO: " + velocityY + "\n");
		}
	}
	
	void hitTopOfScreen() {
		y = 0;
	}
	
	void hitRightOfScreen() {
		x = (ScreenManager.windowSize.width - getWidth());
	}
	
	void hitBottomOfScreen() {
		y = (ScreenManager.windowSize.height - getHeight());
	}
	
	void hitLeftOfScreen() {
		x = 0;
	}
	
	void setGravity(double gravity) {
		this.gravity = gravity;
	}
	
	void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}
	
	void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}
}
