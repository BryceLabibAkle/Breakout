package PhysicsEngine;

import java.awt.Image;

public class FatherOfPowerups extends PhysicsObject {
	
	FatherOfPowerups(double x, double y, int width, int height, double gravity, double mass, int velocityX, int velocityY) {
		super(x, y, width, height, gravity, mass, velocityX, velocityY);
		setActive(true);
	}
	
	void collected() {
		setActive(false);
		giveEffect();
	}
	
	void giveEffect() {
		
	}
	
	@Override
	void hitBottomOfScreen() {
		setActive(false);
	}
}
