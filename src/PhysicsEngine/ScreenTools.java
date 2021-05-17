package PhysicsEngine;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenTools {
	
	// Size of screen in pixels
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	// Size of window
	static float windowPercentageOfScreen;
	static Dimension windowSize;
			
	// The varibles the program will use to calculate
	static int screenWidth;
	static int screenHeight;
	
	ScreenTools(float windowPercentageOfScreen) {
		this.screenWidth = screenSize.width;
		this.screenHeight = screenSize.height;
		this.windowPercentageOfScreen = windowPercentageOfScreen;
		this.windowSize = new Dimension(getSizeFromPercentageOfScreenX(windowPercentageOfScreen), 
										getSizeFromPercentageOfScreenY(windowPercentageOfScreen));
	}
	
	void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	
	void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	
	static int getSizeFromPercentageOfScreenX(float percentageSizeOfScreen) {
		return (int) (screenWidth*(percentageSizeOfScreen/100));
	}
	
	static int getSizeFromPercentageOfScreenY(float percentageSizeOfScreen) {
		return (int) (screenHeight*(percentageSizeOfScreen/100));
	}
	
	static Dimension getDimensionFromPercentageOfScreen(float percentageSizeOfScreenX, float percentageSizeOfScreenY) {
		int x = getSizeFromPercentageOfScreenX(percentageSizeOfScreenX);
		int y = getSizeFromPercentageOfScreenY(percentageSizeOfScreenY);
		return (new Dimension(x, y));
	}
}
