package PhysicsEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener{
	// Other
	BallObject ballObject;
	BoxObject boxObject;
	Random random = new Random();
	RacketObject racketObject;
	ScoreManagerObject scoreManagerObject;

	// Mouse pointer location
	int mouseX = 0;
	int mouseY = 0;
	
	// BoxObjectOrganizer
	int boxObjectOrganizerColumn = 8;
	int boxObjectOrganizerRow = 8;
	int startingAmountOfBoxObjects = (boxObjectOrganizerColumn*boxObjectOrganizerRow);
	BoxObjectOrganizer boxObjectOrganizer = new BoxObjectOrganizer(boxObjectOrganizerColumn, boxObjectOrganizerRow, 5);
	
	// Arrays of Objects
	public ArrayList<BallObject> ballObjects = new ArrayList<BallObject>();
	public ArrayList<BoxObject> boxObjects = new ArrayList<BoxObject>();
	//public ArrayList<ArrayList<FatherOfObjects>> blockObjects = boxObjectOrganizer.blockObjects;
	public ArrayList<FatherOfPowerups> powerupObjects = new ArrayList<FatherOfPowerups>();
	public static ArrayList<TextObject> textObjects = new ArrayList<TextObject>();
	
	// BallObject Variables
	double ballObjectGravity = 0.3;
	
	// Powerup Names
	final byte REVERSEGRAVITY = 0;
	final byte EXTRALIFE = 1;
	final byte BOMBBALLS = 2;
		
	// Powerup Variables
	int powerupX;
	int powerupY = 0;
	int powerupWidth = ScreenManager.getSizeFromPercentageOfWindowY(10);
	int powerupHeight = powerupWidth;
	double powerupGravity = 0.5;
	double powerupMass = 1;
	byte powerupDuration = 7;
	int powerupVelocityX;
	int powerupVelocityY = 5;
	
	public ObjectManager() {
		// Creating the Racket Object
		racketObject = addRacketObject();
		scoreManagerObject = addScoreManagerObject();
		System.out.println("racketObject.width: " + racketObject.width);
		System.out.println("racketObject.height: " + racketObject.height);
		System.out.println("racketObject.x: " + racketObject.x);
		System.out.println("racketObject.y: " + racketObject.y);
		
		// Adding box objects
		for (int boxObjectsColumn = 0; boxObjectsColumn < boxObjectOrganizerColumn; boxObjectsColumn++) {
			for (int boxObjectsRow = 0; boxObjectsRow < boxObjectOrganizerRow; boxObjectsRow++) {
				boxObjects.add(boxObjectOrganizer.getBoxObject(boxObjectsColumn, boxObjectsRow));
			}
		}
	}
	
	void addBoxObject() {
		// Setting size of BoxObject
		int boxObjectX = random.nextInt(ScreenManager.getWindowWidth());
		int boxObjectY = random.nextInt(ScreenManager.getWindowHeight());
		int boxObjectWidth = ScreenManager.getSizeFromPercentageOfWindowY((float) 24);
		int boxObjectHeight = ScreenManager.getSizeFromPercentageOfWindowY(18);
		int boxObjectHitsToDestroy = random.nextInt(9)+1;
		
		boxObjects.add(new BoxObject(boxObjectX, boxObjectY, boxObjectWidth, boxObjectHeight, boxObjectHitsToDestroy, true));
	}
	
	static RacketObject addRacketObject() {
		float RacketObjectWidth = ScreenManager.getSizeFromPercentageOfWindowX((float) 14.0);
		float RacketObjectHeight = ScreenManager.getSizeFromPercentageOfWindowY((float) 5.0);
		
		float RacketObjectX = (float) (ScreenManager.getSizeFromPercentageOfWindowX((float) 50.0)-(RacketObjectWidth/4.0));
		float RacketObjectY = (ScreenManager.getSizeFromPercentageOfWindowY((float) 90.0)-RacketObjectHeight);
		
		System.out.println("windowPercentageOfScreen: " + ScreenManager.windowPercentageOfScreen);
		System.out.println("RacketObjectWidth: " + RacketObjectWidth);
		System.out.println("RacketObjectHeight: " + RacketObjectHeight);
		System.out.println("----------");
		System.out.println("RacketObjectX: " + RacketObjectX);
		System.out.println("RacketObjectY: " + RacketObjectY);
		
		return new RacketObject(RacketObjectX, RacketObjectY, (int) RacketObjectWidth, (int) RacketObjectHeight);
	}
	
	void addBallObject() {
		// Setting size of BallObject
		int ballObjectHeight = ScreenManager.getSizeFromPercentageOfWindowY((float) 10.0);
		int ballObjectWidth = ballObjectHeight;
		
		// Spawning BallObject on the racket
		int ballObjectX = (int) (racketObject.getX() + (racketObject.width/4.0));
		int ballObjectY = (int) racketObject.getY() - ballObjectHeight;
		
		// BallObject's mass
		float ballObjectMass = (float) 1.075;
		
		// Ball velocity X
		int ballObjectDistanceFromRacketX = -35*(racketObject.getCenterOfWidth() - mouseX);
		int ballObjectVelocityX = (int) ballObjectDistanceFromRacketX/ScreenManager.getWindowWidth();
		
		// Ball velocity Y
		int ballObjectDistanceFromRacketY = (int) (-35*((racketObject.getY() + racketObject.getHeight()) - mouseY));
		int ballObjectVelocityY = (int) ballObjectDistanceFromRacketY/ScreenManager.getWindowHeight();
		
		ballObjects.add(new BallObject(ballObjectX, ballObjectY, ballObjectWidth, ballObjectHeight, ballObjectGravity, ballObjectMass, ballObjectVelocityX, ballObjectVelocityY));
		
		// Reduces the amount of time left by 5 seconds
		MainPanel.gameDuration -= 5;
	}
	
	void addRandomPowerup(int powerupX, int powerupY) {
		powerupVelocityX = (int) ((random.nextInt(50)-25)/5.0);
		
		switch(random.nextInt(3)) {
		case REVERSEGRAVITY:
			powerupObjects.add(new GravityPowerup(powerupX, powerupY, powerupWidth, powerupHeight, powerupGravity, powerupMass, powerupVelocityX, powerupVelocityY));
			break;
		}
	}
	
	ScoreManagerObject addScoreManagerObject() {
		int ScoreManagerObjectX = ScreenManager.getSizeFromPercentageOfWindowX((float) 1.5);
		int ScoreManagerObjectY = ScreenManager.getSizeFromPercentageOfWindowX((float) 1.5);
		
		int ScoreManagerObjectWidth = ScreenManager.getSizeFromPercentageOfWindowX(10);
		int ScoreManagerObjectHeight = ScreenManager.getSizeFromPercentageOfWindowY(10);
		
		return new ScoreManagerObject(ScoreManagerObjectX, ScoreManagerObjectY, (int) ScoreManagerObjectWidth, (int) ScoreManagerObjectHeight, null);
	}
	
	static void addText(int x, int y, int width, int height, String text, String font, int fontSize, Color color) {
		TextObject textObject = new TextObject(x, y, width, height, color);
//		textObject.setFont(new Font("Courier", Font.ITALIC, fontSize));
		textObject.setText(text);
		textObjects.add(textObject);
		
	}
	
	static void addEndGameText() {
		// Width and Height
		int textWidth = ScreenManager.getSizeFromPercentageOfWindowX(50);
		int textHeight = ScreenManager.getSizeFromPercentageOfWindowY(50);
		
		// X and Y positions
		int textX = (int) (ScreenManager.getSizeFromPercentageOfWindowX(50)-(textWidth/2));
		int textY = (int) (ScreenManager.getSizeFromPercentageOfWindowY(50)-(textHeight/2));
		
		Color textColor = new Color(0, 0, 0);
		
		ObjectManager.addText(textX+1, textY+1, textWidth, textHeight, "GAME ENDED", "No Font", 30, new Color(255, 255, 255));
		ObjectManager.addText(textX, textY, textWidth, textHeight, "GAME ENDED", "No Font", 30, textColor);
	}
	
	void applyGravityPowerup() {
		for (int ballObjectNum1 = 0; ballObjectNum1 < ballObjects.size(); ballObjectNum1++) {
			MainPanel.gravityPowerupDurationSeconds = ((MainPanel.secondsPassed/(MainPanel.fps/2))+powerupDuration);
			ballObjects.get(ballObjectNum1).setGravity(-ballObjectGravity);
		}
	}
	
	void checkToRemoveGravityPowerup() {
		if ((MainPanel.secondsPassed/(MainPanel.fps/2.0)) > MainPanel.gravityPowerupDurationSeconds) {
			for (int ballObjectNum1 = 0; ballObjectNum1 < ballObjects.size(); ballObjectNum1++) {
				ballObjects.get(ballObjectNum1).setGravity(ballObjectGravity);
			}
		}
	}
	
	void checkToEndGame() {
		if ((MainPanel.secondsPassed/(MainPanel.fps/2.0)) > MainPanel.gameDuration) {
			for (int boxObjectNum = 0; boxObjectNum < boxObjects.size(); boxObjectNum++) {
				boxObjects.get(boxObjectNum).setActive(false);
			}
			
			int blackBoxObjectWidth = ScreenManager.getSizeFromPercentageOfWindowX(100);
			int blackBoxObjectHeight = ScreenManager.getSizeFromPercentageOfWindowX(100);
			
			boxObjects.add(new BoxObject(0, 0, blackBoxObjectWidth, blackBoxObjectHeight, 999, false));
			
			scoreManagerObject.updateScore = false;
//			scoreManagerObject.setText("Game Over, your score is " + scoreManagerObject.getScore());
			
//			addEndGameText();
			
			MainPanel.updateGameOverState();
		}
	}
	
	void updateObjects() {
		MainPanel.secondsPassed++;
		//System.out.println(MainPanel.secondsPassed/(MainPanel.fps/2));
		
		checkToRemoveGravityPowerup();
		
		// Checks if the user ran out of time
		checkToEndGame();
		
		// Updates ball objects
		for (BallObject ballObject : ballObjects) {
			ballObject.update();
		}
		
		for (BoxObject boxObject : boxObjects) {
			boxObject.update();
		}
		
		for (FatherOfPowerups powerupObject : powerupObjects) {
			powerupObject.update();
		}
		
		racketObject.update();
		scoreManagerObject.update();
		
		checkCollision();
		purgeObjects();
	}
	
	void checkCollision() {
		for (int ballObjectNum1 = 0; ballObjectNum1 < ballObjects.size(); ballObjectNum1++) {
			for (int ballObjectNum2 = ballObjectNum1; ballObjectNum2 < ballObjects.size(); ballObjectNum2++) {
				
//				double collisionToleranceX = (Math.abs(ballObjects.get(ballObjectNum1).velocityX) + Math.abs(ballObjects.get(ballObjectNum2).velocityX));
//				double collisionToleranceY = (Math.abs(ballObjects.get(ballObjectNum1).velocityY) + Math.abs(ballObjects.get(ballObjectNum2).velocityY));
				double collisionToleranceX = 5;
				double collisionToleranceY = 2;
				
				// ballObject1
				double ballObject1X = ballObjects.get(ballObjectNum1).getX();
				double ballObject1Y = ballObjects.get(ballObjectNum1).getY();
				int ballObject1Width = (int) (ballObjects.get(ballObjectNum1).getWidth());
				int ballObject1Height = (int) (ballObjects.get(ballObjectNum1).getHeight());
				
				// ballObject2
				double ballObject2X = ballObjects.get(ballObjectNum2).getX();
				double ballObject2Y = ballObjects.get(ballObjectNum2).getY();
				int ballObject2Width = (int) (ballObjects.get(ballObjectNum2).getWidth());
				int ballObject2Height = (int) (ballObjects.get(ballObjectNum2).getHeight());
//				int ballObject2Width = (int) (ballObjects.get(ballObjectNum2).getWidth()+Math.abs(ballObjects.get(ballObjectNum2).velocityX));
//				int ballObject2Height = (int) (ballObjects.get(ballObjectNum2).getHeight()+Math.abs(ballObjects.get(ballObjectNum2).velocityY));
//				
				ballObjects.get(ballObjectNum1).collisionBox = new Rectangle((int) ballObject1X, (int) ballObject1Y, 1, 1);
				ballObjects.get(ballObjectNum1).collisionBox = new Rectangle((int) ballObject2X, (int) ballObject2Y, 1, 1);
				//System.out.println(ballObjects.get(ballObjectNum1).collisionBox.width);
				
				if (ballObjects.get(ballObjectNum1).collisionBox.intersects(racketObject.collisionBox)) {
					System.out.println("Hit Racket");
				}
				
//				if (ballObjects.get(ballObjectNum1).collisionBox.intersects(ballObjects.get(ballObjectNum2).collisionBox)) {
//					if (ballObjects.get(ballObjectNum1) != ballObjects.get(ballObjectNum2)) {
//						
//						
//						//System.out.println(collisionToleranceX);
//						if (Math.abs(ballObjects.get(ballObjectNum1).getTopSideLocation() - ballObjects.get(ballObjectNum2).getBottomSideLocation()) < collisionToleranceY) {
//							System.out.println("TOP");
//							//collisionTolerance = (int) (collisionTolerance*(ballObjects.get(ballObjectNum1).velocityY + ballObjects.get(ballObjectNum2).velocityY)/2);
//							ballObjects.get(ballObjectNum1).setVelocityY((int) (-1*ballObjects.get(ballObjectNum1).velocityY));
//							ballObjects.get(ballObjectNum2).setVelocityY((int) (-1*ballObjects.get(ballObjectNum2).velocityY));
//						}
//						else if (Math.abs(ballObjects.get(ballObjectNum1).getBottomSideLocation() - ballObjects.get(ballObjectNum2).getTopSideLocation()) < collisionToleranceY) {
//							System.out.println("BOTTOM");
//							ballObjects.get(ballObjectNum1).setVelocityY((int) (-1*ballObjects.get(ballObjectNum1).velocityY));
//							ballObjects.get(ballObjectNum2).setVelocityY((int) (-1*ballObjects.get(ballObjectNum2).velocityY));
//						}
//						else if (Math.abs(ballObjects.get(ballObjectNum1).getRightSideLocation() - ballObjects.get(ballObjectNum2).getLeftSideLocation()) < collisionToleranceX) {
//							System.out.println("RIGHT");
//							ballObjects.get(ballObjectNum1).setVelocityX((int) (-1*ballObjects.get(ballObjectNum1).velocityX));
//							ballObjects.get(ballObjectNum2).setVelocityX((int) (-1*ballObjects.get(ballObjectNum2).velocityX));
//						}
//						else if (Math.abs(ballObjects.get(ballObjectNum1).getLeftSideLocation() - ballObjects.get(ballObjectNum2).getRightSideLocation()) < collisionToleranceX) {
//							System.out.println("LEFT");
//							ballObjects.get(ballObjectNum1).setVelocityX((int) (-1*ballObjects.get(ballObjectNum1).velocityX));
//							ballObjects.get(ballObjectNum2).setVelocityX((int) (-1*ballObjects.get(ballObjectNum2).velocityX));
//						}
//						//System.out.println("" + ballObjectNum1 + ", " + ballObjectNum2);
//						
//					}
//				}
			}
			
			// Powerup
			for (int boxObjectNum = 0; boxObjectNum < boxObjects.size(); boxObjectNum++) {
				if (boxObjects.get(boxObjectNum).collisionBox.intersects(ballObjects.get(ballObjectNum1).collisionBox)) {
					boxObjects.get(boxObjectNum).gotHit();
					System.out.println("Hit: " + boxObjectNum
									   + "\nX Value: "+ ballObjects.get(ballObjectNum1).getX()
									   + "\nY Value: "+ ballObjects.get(ballObjectNum1).getY() + "\n");
					System.out.println("boxObjectNum Width: " + boxObjects.get(boxObjectNum).collisionBox.width);
					System.out.println("boxObjectNum Height: " + boxObjects.get(boxObjectNum).collisionBox.height);
				}
			}
			
			if (ballObjects.get(ballObjectNum1).collisionBox.intersects(racketObject.collisionBox)) {
				// TODO: Add code to bunch the object
			}
		}
		
		for (int powerupObjectNum = 0; powerupObjectNum < powerupObjects.size(); powerupObjectNum++) {
			if (powerupObjects.get(powerupObjectNum).collisionBox.intersects(racketObject.collisionBox)) {
				double originalGravity = ballObjectGravity;
				powerupObjects.get(powerupObjectNum).setActive(false);
				
				applyGravityPowerup();
			}
		}
	}
	
	void drawObjects(Graphics g) {
		
		for (BoxObject boxObject : boxObjects) {
			boxObject.draw(g);
		}
		
		for (FatherOfPowerups powerupObject : powerupObjects) {
			powerupObject.draw(g);
		}
		
		for (BallObject ballObject : ballObjects) {
			ballObject.draw(g);
		}
		
		for (TextObject textObject : textObjects) {
			textObject.draw(g);
		}
		
		
		scoreManagerObject.draw(g);
		racketObject.draw(g);
	}
	
	
	void purgeObjects() {
		// Removes inactive aliens
		for (int ballObjectNum = 0; ballObjectNum < ballObjects.size(); ballObjectNum++) {  // Can't use for each loop
			if (ballObjects.get(ballObjectNum).isActive == false) {
				ballObjects.remove(ballObjectNum);
			}
		}
		for (int boxObjectNum = 0; boxObjectNum < boxObjects.size(); boxObjectNum++) {  // Can't use for each loop
			if (boxObjects.get(boxObjectNum).isActive == false) {
				if (boxObjects.get(boxObjectNum).hasPowerup) {
					addRandomPowerup((int) boxObjects.get(boxObjectNum).getX(), (int) boxObjects.get(boxObjectNum).getY());
				}
				boxObjects.remove(boxObjectNum);
				scoreManagerObject.addToScore(1000);
			}
		}
		
		for (int powerupObjectNum = 0; powerupObjectNum < powerupObjects.size(); powerupObjectNum++) {  // Can't use for each loop
			if (powerupObjects.get(powerupObjectNum).isActive == false) {
				powerupObjects.remove(powerupObjectNum);
			}
		}
		
		if (racketObject.isActive == false) {
			racketObject = null;
		}
	}
	
	int getGravityObjectCount() {
		return (ballObjects.size() + powerupObjects.size());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
