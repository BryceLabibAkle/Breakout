package PhysicsEngine;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenManager {
	
	// Size of screen in pixels
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	// Size of window
	static float windowPercentageOfScreen;
	static Dimension windowSize;
	
	// Window size
	static int windowWidth;
	static int windowHeight;
	
	// The varibles the program will use to calculate the screen size
	static int screenWidth;
	static int screenHeight;
	
	ScreenManager(float windowPercentageOfScreen_) {
		setwindowPercentageOfScreen(windowPercentageOfScreen_);
	}
	
	static void setScreenWidth(int screenWidth) {
		ScreenManager.screenWidth = screenWidth;
	}
	
	static void setScreenHeight(int screenHeight) {
		ScreenManager.screenHeight = screenHeight;
	}
	
	static void setwindowPercentageOfScreen(float windowPercentageOfScreen_) {
		ScreenManager.windowPercentageOfScreen = windowPercentageOfScreen_;
		System.out.println("ScreenManager.windowPercentageOfScreen: " + ScreenManager.windowPercentageOfScreen);
		ScreenManager.screenWidth = screenSize.width;
		ScreenManager.screenHeight = screenSize.height;
		System.out.println("screenSize.width" + screenSize.width);
		System.out.println("screenSize.height" + screenSize.height);
		
		ScreenManager.windowPercentageOfScreen = windowPercentageOfScreen_;
		ScreenManager.windowWidth = getSizeFromPercentageOfScreenX(windowPercentageOfScreen);
		ScreenManager.windowHeight = getSizeFromPercentageOfScreenY(windowPercentageOfScreen);
		ScreenManager.windowSize = new Dimension(getSizeFromPercentageOfScreenX(windowPercentageOfScreen), 
												 getSizeFromPercentageOfScreenY(windowPercentageOfScreen));
	}
	
	static int getWindowWidth() {
		System.out.println("windowWidth: " + getSizeFromPercentageOfScreenX(windowPercentageOfScreen));
		return getSizeFromPercentageOfScreenX(50);
	}
	
	static int getWindowHeight() {
		System.out.println("windowHeight: " + getSizeFromPercentageOfScreenY(windowPercentageOfScreen));
		return getSizeFromPercentageOfScreenY(50);
	}
	
	static int getSizeFromPercentageOfWindowX(float percentageSizeOfWindow) {
		return (int) (ScreenManager.getWindowWidth()*(percentageSizeOfWindow/100.0));
	}
	
	static int getSizeFromPercentageOfWindowY(float percentageSizeOfWindow) {
		return (int) (ScreenManager.getWindowHeight()*(percentageSizeOfWindow/100.0));
	}
	
	static int getSizeFromPercentageOfScreenX(float percentageSizeOfScreen) {
		System.out.println("screenWidth: " + screenWidth);
		System.out.println("percentageSizeOfScreen: " + percentageSizeOfScreen);
		return (int) (screenSize.width*(percentageSizeOfScreen/100.0));
	}
	
	static int getSizeFromPercentageOfScreenY(float percentageSizeOfScreen) {
		System.out.println("screenHeight: " + screenHeight);
		double l = (screenSize.height*(percentageSizeOfScreen/100.0));
		System.out.println("l" + l);
		return (int) (screenSize.height*(percentageSizeOfScreen/100.0));
	}
	
	static Dimension getDimensionFromPercentageOfScreen(float percentageSizeOfScreenX, float percentageSizeOfScreenY) {
		int x = getSizeFromPercentageOfScreenX(percentageSizeOfScreenX);
		int y = getSizeFromPercentageOfScreenY(percentageSizeOfScreenY);
		return (new Dimension(x, y));
	}
}
