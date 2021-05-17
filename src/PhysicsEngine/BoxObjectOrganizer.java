package PhysicsEngine;

import java.util.ArrayList;
import java.util.Random;

public class BoxObjectOrganizer {
	// Grid
	int rows;
	int columns;
	
	// Other
	Random rand = new Random();
	ScreenTools screenTools = new ScreenTools(50);
	
	// Steps for Blocks
	int stepX;
	int stepY;
	
	/* Each BoxObject Array represent a row 
	 * Each BoxObject in the Array represents one column 
	 */
	ArrayList<ArrayList<BoxObject>> blockObjects = new ArrayList<ArrayList<BoxObject>>();
	
	BoxObjectOrganizer(int rows, int columns) {
		System.out.println(screenTools.getSizeFromPercentageOfScreenX(50));
		this.rows = rows;
		this.columns = columns;
		
		// Setting step amount
		// Sould be (50/num) but for some reason 40 works better
		int stepX = screenTools.getSizeFromPercentageOfScreenX(40/columns);
		int stepY = screenTools.getSizeFromPercentageOfScreenY(40/rows);
		
		for (int currentRow = 0; currentRow < rows; currentRow++) {
			System.out.println("\nRow: " + currentRow);
			blockObjects.add(new ArrayList<BoxObject>());
			for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
				
				// Settings for the blockObject
				int boxObjectX = stepX*currentRow;
				int boxObjectY = stepY*currentColumn;
				//System.out.println("boxObjectX: " + boxObjectX);
				//System.out.println("boxObjectY: " + boxObjectY);
				
				blockObjects.get(currentRow).add(new BoxObject(boxObjectX, boxObjectY, stepX, stepY, rand.nextInt(9)+1, false));
			}
		}
		
		System.out.println(blockObjects);
	}
	
	BoxObject getBoxObject(int boxObjectColumn, int boxObjectRow) {
		return blockObjects.get(boxObjectColumn).get(boxObjectRow);
	}
}
