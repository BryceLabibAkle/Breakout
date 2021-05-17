package PhysicsEngine;

import java.awt.Color;

public class RacketObject extends FatherOfObjects{
	double velocityX;
	RacketObject(double x, double y, int width, int height) {
		super(x, y, width, height);
		velocityX = 0;
		this.needImage = false;
		imageFileName = "Shield.png";
		color = new Color(0, 26, 198);
	}
	
	void update() {
		if (velocityX > 15) {
			velocityX = 5;
		}
		else if (velocityX < -15) {
			velocityX = -5;
		}
		
		x += velocityX;
		
		velocityX *= 0.85;
	}
	
	void movingRight() {
		velocityX *= 1.1;
		velocityX += 1;
	}
	
	void movingLeft() {
		velocityX *= 1.1;
		velocityX -= 1;
	}
}
