package PhysicsEngine;

import java.awt.Color;
import java.awt.Graphics;

public class GravityPowerup extends FatherOfPowerups{

	GravityPowerup(double x, double y, int width, int height, double gravity, double mass, int velocityX, int velocityY) {
		super(x, y, width, height, gravity, mass, velocityX, velocityY);
		boolean needImage = true;
		imageFileName = "GravityPowerup.png";
	}
	
	void draw(Graphics g) {
		if (needImage) {
			loadImage("GravityPowerup.png");
			g.drawImage(image, (int) x, (int) y, width, height, null);
		}
		else {
	        g.setColor(Color.RED);
	        g.fillRect((int) x, (int) y, width, height);
		}
	}
}
