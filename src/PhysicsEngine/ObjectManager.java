package PhysicsEngine;

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
	static ScreenTools screenTools = new ScreenTools(50);
	Random random = new Random();
	RacketObject racketObject;

	// Mouse pointer location
	int mouseX = 0;
	int mouseY = 0;
	
	// BoxObjectOrganizer
	int boxObjectOrganizerColumn = 10;
	int boxObjectOrganizerRow = 8;
	int startingAmountOfBoxObjects = (boxObjectOrganizerColumn*boxObjectOrganizerRow);
	BoxObjectOrganizer boxObjectOrganizer = new BoxObjectOrganizer(boxObjectOrganizerColumn, boxObjectOrganizerRow);
	
	// Arrays of Objects
	public ArrayList<BallObject> ballObjects = new ArrayList<BallObject>();
	public ArrayList<BoxObject> boxObjects = new ArrayList<BoxObject>();
	//public ArrayList<ArrayList<FatherOfObjects>> blockObjects = boxObjectOrganizer.blockObjects;
	public ArrayList<FatherOfPowerups> powerupObjects = new ArrayList<FatherOfPowerups>();
	
	// BallObject Variables
	double ballObjectGravity = 0.3;
	
	// Powerup Names
	final byte REVERSEGRAVITY = 0;
	final byte EXTRALIFE = 1;
	final byte BOMBBALLS = 2;
		
	// Powerup Variables
	int powerupX;
	int powerupY = 0;
	int powerupWidth = ScreenTools.getSizeFromPercentageOfScreenY(5);
	int powerupHeight = powerupWidth;
	double powerupGravity = 0.5;
	double powerupMass = 1;
	byte powerupDuration = 7;
	int powerupVelocityX;
	int powerupVelocityY = 5;
	
	public ObjectManager() {
		// Creating the Racket Object
		racketObject = addRacketObject();
		
		// Adding box objects
		for (int boxObjectsColumn = 0; boxObjectsColumn < boxObjectOrganizerColumn; boxObjectsColumn++) {
			for (int boxObjectsRow = 0; boxObjectsRow < boxObjectOrganizerRow; boxObjectsRow++) {
				boxObjects.add(boxObjectOrganizer.getBoxObject(boxObjectsColumn, boxObjectsRow));
			}
		}
	}
	
	void addBoxObject() {
		// Setting size of BoxObject
		int boxObjectX = random.nextInt(screenTools.getSizeFromPercentageOfScreenX(50));
		int boxObjectY = random.nextInt(screenTools.getSizeFromPercentageOfScreenY(50));
		int boxObjectWidth = screenTools.getSizeFromPercentageOfScreenY((float) 12);
		int boxObjectHeight = screenTools.getSizeFromPercentageOfScreenY(9);
		int boxObjectHitsToDestroy = random.nextInt(9)+1;
		
		boxObjects.add(new BoxObject(boxObjectX, boxObjectY, boxObjectWidth, boxObjectHeight, boxObjectHitsToDestroy, true));
	}
	
	RacketObject addRacketObject() {
		int RacketObjectWidth = screenTools.getSizeFromPercentageOfScreenX((float) 7.5);
		int RacketObjectHeight = screenTools.getSizeFromPercentageOfScreenY((float) 2.5);
		
		int RacketObjectX = (screenTools.getSizeFromPercentageOfScreenX(25)-(RacketObjectWidth/4));
		int RacketObjectY = (screenTools.getSizeFromPercentageOfScreenY(45)-RacketObjectHeight);
		
		return new RacketObject(RacketObjectX, RacketObjectY, RacketObjectWidth, RacketObjectHeight);
	}
	
	void addBallObject() {
		// Setting size of BallObject
		int ballObjectHeight = screenTools.getSizeFromPercentageOfScreenY((float) 10);
		int ballObjectWidth = ballObjectHeight;
		
		// Spawning BallObject on the racket
		int ballObjectX = (int) (racketObject.getX() + (racketObject.width/4));
		int ballObjectY = (int) racketObject.getY() - ballObjectHeight;
		
		// Ball velocity
		int ballObjectVelocityX = 8*(((ballObjectX - mouseX)/screenTools.getSizeFromPercentageOfScreenX(50))*100);
		int ballObjectVelocityY;
		
		ballObjects.add(new BallObject(ballObjectX, ballObjectY, ballObjectWidth, ballObjectHeight, ballObjectGravity, 1, ballObjectVelocityX, -7));
	}
	
	void addRandomPowerup(int powerupX, int powerupY) {
		powerupVelocityX = (random.nextInt(50)-25)/10;
		
		switch(random.nextInt(3)) {
		case REVERSEGRAVITY:
			powerupObjects.add(new GravityPowerup(powerupX, powerupY, powerupWidth, powerupHeight, powerupGravity, powerupMass, powerupVelocityX, powerupVelocityY));
			break;
		}
	}
	
	void applyGravityPowerup() {
		for (int ballObjectNum1 = 0; ballObjectNum1 < ballObjects.size(); ballObjectNum1++) {
			MainPanel.gravityPowerupDurationSeconds = ((MainPanel.secondsPassed/(MainPanel.fps/2))+powerupDuration);
			ballObjects.get(ballObjectNum1).setGravity(-ballObjectGravity);
		}
	}
	
	void checkToRemoveGravityPowerup() {
		if ((MainPanel.secondsPassed/(MainPanel.fps/2)) > MainPanel.gravityPowerupDurationSeconds) {
			for (int ballObjectNum1 = 0; ballObjectNum1 < ballObjects.size(); ballObjectNum1++) {
				ballObjects.get(ballObjectNum1).setGravity(ballObjectGravity);
			}
		}
	}
	
	void updateObjects() {
		MainPanel.secondsPassed++;
		//System.out.println(MainPanel.secondsPassed/(MainPanel.fps/2));
		
		checkToRemoveGravityPowerup();
		
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
				
				if (ballObjects.get(ballObjectNum1).collisionBox.intersects(ballObjects.get(ballObjectNum2).collisionBox)) {
					if (ballObjects.get(ballObjectNum1) != ballObjects.get(ballObjectNum2)) {
						
						
						//System.out.println(collisionToleranceX);
						if (Math.abs(ballObjects.get(ballObjectNum1).getTopSideLocation() - ballObjects.get(ballObjectNum2).getBottomSideLocation()) < collisionToleranceY) {
							System.out.println("TOP");
							//collisionTolerance = (int) (collisionTolerance*(ballObjects.get(ballObjectNum1).velocityY + ballObjects.get(ballObjectNum2).velocityY)/2);
							ballObjects.get(ballObjectNum1).setVelocityY((int) (-1*ballObjects.get(ballObjectNum1).velocityY));
							ballObjects.get(ballObjectNum2).setVelocityY((int) (-1*ballObjects.get(ballObjectNum2).velocityY));
						}
						else if (Math.abs(ballObjects.get(ballObjectNum1).getBottomSideLocation() - ballObjects.get(ballObjectNum2).getTopSideLocation()) < collisionToleranceY) {
							System.out.println("BOTTOM");
							ballObjects.get(ballObjectNum1).setVelocityY((int) (-1*ballObjects.get(ballObjectNum1).velocityY));
							ballObjects.get(ballObjectNum2).setVelocityY((int) (-1*ballObjects.get(ballObjectNum2).velocityY));
						}
						else if (Math.abs(ballObjects.get(ballObjectNum1).getRightSideLocation() - ballObjects.get(ballObjectNum2).getLeftSideLocation()) < collisionToleranceX) {
							System.out.println("RIGHT");
							ballObjects.get(ballObjectNum1).setVelocityX((int) (-1*ballObjects.get(ballObjectNum1).velocityX));
							ballObjects.get(ballObjectNum2).setVelocityX((int) (-1*ballObjects.get(ballObjectNum2).velocityX));
						}
						else if (Math.abs(ballObjects.get(ballObjectNum1).getLeftSideLocation() - ballObjects.get(ballObjectNum2).getRightSideLocation()) < collisionToleranceX) {
							System.out.println("LEFT");
							ballObjects.get(ballObjectNum1).setVelocityX((int) (-1*ballObjects.get(ballObjectNum1).velocityX));
							ballObjects.get(ballObjectNum2).setVelocityX((int) (-1*ballObjects.get(ballObjectNum2).velocityX));
						}
						//System.out.println("" + ballObjectNum1 + ", " + ballObjectNum2);
						
					}
				}
			}
			
			// Powerup
			for (int BoxObjectNum = 0; BoxObjectNum < boxObjects.size(); BoxObjectNum++) {
				if (ballObjects.get(ballObjectNum1).collisionBox.intersects(boxObjects.get(BoxObjectNum).collisionBox)) {
					boxObjects.get(BoxObjectNum).gotHit();
					if (boxObjects.get(BoxObjectNum).hasPowerup) {
						addRandomPowerup((int) boxObjects.get(BoxObjectNum).getX(), (int) boxObjects.get(BoxObjectNum).getY());
					}
				}
			}
			
			if (ballObjects.get(ballObjectNum1).collisionBox.intersects(racketObject.collisionBox)) {
				// TODO: Add code to bonch the object
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
		for (BallObject ballObject : ballObjects) {
			ballObject.draw(g);
		}
		
		for (BoxObject boxObject : boxObjects) {
			boxObject.draw(g);
		}
		
		for (FatherOfPowerups powerupObject : powerupObjects) {
			powerupObject.draw(g);
		}
		
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
				boxObjects.remove(boxObjectNum);
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
