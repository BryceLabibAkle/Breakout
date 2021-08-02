package PhysicsEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

public class FatherOfObjects {
	double x;
	double y;
	int width;
	int height;
	boolean isActive = true;
	boolean needImage;
	Rectangle collisionBox;
	Image image;
	String imageFileName;
	Color color;
	static String text = "";
	
	FatherOfObjects(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isActive = true;
		this.needImage = true;
		
		this.collisionBox = new Rectangle((int) (x), (int) (y), width, height);
		
		System.out.println("made new object");
	}
	
	void draw(Graphics g) {
		if (needImage) {
			loadImage(imageFileName);
			g.drawImage(image, (int) x, (int) y, width, height, null);
			g.drawString(text, (int) this.x, (int) this.y);
		}
		else {
	        g.setColor(color);
	        g.fillRect((int) x, (int) y, width, height);
		}
	}
	
	void update() {
		
	}
	
	void loadImage(String imageFile) {
		try {
			image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
		}
		catch (Exception exception) {
			System.out.println("Couldn't load the image: " + imageFile);
		}
	}
	
	int getTopSideLocation() {
		return ((int) this.y - 1);
	}

	int getRightSideLocation() {
		return ((int) this.x + getWidth());
	}

	int getBottomSideLocation() {
		return ((int) this.y + getHeight())+1;
	}

	int getLeftSideLocation() {
		return (int) this.x;
	}
	
	double getX() {
		return this.x;
	}
	 
	double getY() {
		return this.y;
	}
	
	int getWidth() {
		return this.width;
	}
	
	int getHeight() {
		return this.height;
	}
	
	int getCenterOfWidth() {
		int l = (int) (this.getX() + (this.width/2));
		System.out.println(l);
		return l;
	}
	
	String getText() {
		return this.text;
	}
	 
	void setX(int x) {
		this.x = x;
	}
	
	void setY(int y) {
		this.y = y;
	}
	
	void setWidth(int width) {
		this.width = width;
	}
	
	void setHeight(int height) {
		this.height = height;
	}
	
	void setActive(boolean active) {
		this.isActive = active;
	}
	
	void setText(String text) {
		this.text = text;
	}
}
