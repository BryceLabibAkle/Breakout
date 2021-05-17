package PhysicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BallObject extends PhysicsObject {
	public BallObject(double x, double y, int width, int height, double gravity, double mass, int velocityX, int velocityY) {
		super(x, y, width, height, gravity, mass, velocityY, velocityY);
		this.gravity = gravity;
		this.mass = mass;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.isActive = true;
		this.hasPhysics = true;
		this.needImage = true;
		this.imageFileName = "Italian Niko.png";
		
		this.collisionBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
	}
}