package PhysicsEngine;

public class WallObject extends FatherOfObjects{

	WallObject(double x, double y, int width, int height) {
		super(x, y, width, height);
		this.needImage = true;
		imageFileName = "Shield.png";
	}
}
