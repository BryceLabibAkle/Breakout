package PhysicsEngine;

import java.util.ArrayList;
import java.util.Random;

public class BoxObjectOrganizer {
	// Grid
	int rows;
	int columns;
	
	// Other
	Random rand = new Random();
	boolean hasPowerup;
	
	// Steps for Blocks
	int stepX;
	int stepY;
	
	/* Each BoxObject Array represent a row 
	 * Each BoxObject in the Array represents one column 
	 */
	ArrayList<ArrayList<BoxObject>> blockObjects = new ArrayList<ArrayList<BoxObject>>();
	
	BoxObjectOrganizer(int rows, int columns, double chanceForPowerup) {
		this.rows = rows;
		this.columns = columns;
		
		// Setting step amount
		// Sould be (50/num) but for some reason 40 works better (now 80 bruv)
		int stepX = ScreenManager.getSizeFromPercentageOfWindowX((float) (100.0/columns));
		int stepY = ScreenManager.getSizeFromPercentageOfWindowY((float) (80.0/rows));
		
		System.out.println("stepX: " + stepX);
		System.out.println("stepY: " + stepY);
		
		for (int currentRow = 0; currentRow < rows; currentRow++) {
			System.out.println("\nRow: " + currentRow);
			blockObjects.add(new ArrayList<BoxObject>());
			for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
				
				// Settings for the boxObject
				int boxObjectX = stepX*currentRow;
				int boxObjectY = stepY*currentColumn;
				
				hasPowerup = ((chanceForPowerup) > rand.nextInt(100));

				blockObjects.get(currentRow).add(new BoxObject(boxObjectX, boxObjectY, stepX, stepY, rand.nextInt(9)+1, hasPowerup));
			}
		}
		
		System.out.println(blockObjects);
	}
	
	BoxObject getBoxObject(int boxObjectColumn, int boxObjectRow) {
		return blockObjects.get(boxObjectColumn).get(boxObjectRow);
	}
}
