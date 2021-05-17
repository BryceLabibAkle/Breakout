package PhysicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

public class BoxObject extends FatherOfObjects {
	int hitsToDestroy;
	Map<Integer, Color> damageColorMap;
	boolean hasPowerup;
	
	public BoxObject(double x, double y, int width, int height, int hitsToDestroy, boolean hasPowerup) {
		super(x, y, width, height);
		this.hitsToDestroy = hitsToDestroy;
		this.hasPowerup = hasPowerup;
		this.needImage = false;
		
		damageColorMap = new HashMap<>();
		damageColorMap.put(0, new Color(255, 255, 255));  // White
		damageColorMap.put(1, new Color(255, 26, 26));    // Red
		damageColorMap.put(2, new Color(255, 140, 26));   // Orange
		damageColorMap.put(3, new Color(255, 255, 26));	  // Yellow
		damageColorMap.put(4, new Color(140, 255, 26));   // Lime
		damageColorMap.put(5, new Color(0, 102, 26));     // Green
		damageColorMap.put(6, new Color(26, 255, 255));   // Light Blue
		damageColorMap.put(7, new Color(26, 83, 255));    // Dark Blue
		damageColorMap.put(8, new Color(140, 26, 255));   // Purple
		damageColorMap.put(9, new Color(255, 26, 198));   // Pink
	}
	
	void update() {
		if (hitsToDestroy < 1) {
			isActive = false;
		}
		else {
			color = damageColorMap.get(hitsToDestroy);
		}
	}
	
	@Override
	void draw(Graphics g) {
		if (needImage) {
			loadImage(imageFileName);
			g.drawImage(image, (int) x, (int) y, width, height, null);
		}
		else {
	        g.setColor(color);
	        g.fillRect((int) x, (int) y, width, height);
		}
	}
	
	void gotHit() {
		hitsToDestroy--;
	}
	
	int getHitsToDestroy() {
		return this.hitsToDestroy;
	}
	
	void setHitsToDestroy(int hitsToDestroy) {
		this.hitsToDestroy = hitsToDestroy;
	}
	// O
	public String toString() {
		return "" + this.hitsToDestroy;
	}
}
