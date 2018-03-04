import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		
		// Initial seed
		ArrayList<Cell> cells = new ArrayList<Cell>();
		cells.add(new Cell(0,0));
		cells.add(new Cell(1,0));
		cells.add(new Cell(2,0));
		main.playGame(5, cells, main);
		
		// Glider
		ArrayList<Cell> gliderCells = new ArrayList<Cell>();
		gliderCells.add(new Cell(0,0));
		gliderCells.add(new Cell(1,0));
		gliderCells.add(new Cell(2,0));
		gliderCells.add(new Cell(1,2));
		gliderCells.add(new Cell(2,1));
		main.playGame(5, gliderCells, main);
		
		// Beacon
		ArrayList<Cell> beaconCells = new ArrayList<Cell>();
		beaconCells.add(new Cell(0,0));
		beaconCells.add(new Cell(1,0));
		beaconCells.add(new Cell(0,1));
		beaconCells.add(new Cell(2,3));
		beaconCells.add(new Cell(3,3));
		beaconCells.add(new Cell(3,2));
		//main.playGame(5, beaconCells, main);
		
		ArrayList<Cell> rectangleCells = new ArrayList<Cell>();
		rectangleCells.add(new Cell(0,0));
		rectangleCells.add(new Cell(1,0));
		rectangleCells.add(new Cell(2,0));
		rectangleCells.add(new Cell(0,1));
		rectangleCells.add(new Cell(1,1));
		rectangleCells.add(new Cell(2,1));
		rectangleCells.add(new Cell(0,2));
		rectangleCells.add(new Cell(1,2));
		rectangleCells.add(new Cell(2,2));
		rectangleCells.add(new Cell(0,-1));
		rectangleCells.add(new Cell(1,-1));
		rectangleCells.add(new Cell(2,-1));
		rectangleCells.add(new Cell(0,-2));
		rectangleCells.add(new Cell(1,-2));
		rectangleCells.add(new Cell(2,-2));
		rectangleCells.add(new Cell(0,-3));
		rectangleCells.add(new Cell(1,-3));
		rectangleCells.add(new Cell(2,-3));
		//main.playGame(5, rectangleCells, main);
	}
	
	/**
	 * Pass in cell array and number of iterations and will iterate cell array that many times
	 */
	public void playGame(int numberOfTimes, ArrayList<Cell> cells, Main main) {
		// Run an extra iteration to print inital and final grid
		for(int i = 0; i<numberOfTimes+1; i++) {
			cells = main.iterate(cells, main);
		}
	}
	
	/**
	 * Iterate between one state and the next in the game of life
	 */
	public ArrayList<Cell> iterate(ArrayList<Cell> cells, Main main) {
		
		// Loop through each cell and draw the grid
		// If there are no cells, make min and max for x and y the same, 0
		int firstXPos = 0;
		int firstYPos = 0;
		int lastXPos = 0;
		int lastYPos = 0;
		if(cells.size() != 0) {
			firstXPos = cells.get(0).getxPos();
			firstYPos = cells.get(0).getyPos();
			lastXPos = cells.get(0).getxPos();
			lastYPos = cells.get(0).getyPos();
		}
		
		// Find the lowest x and y in the grid
		for(Cell cell:cells) {
			if(firstXPos > cell.getxPos()) {
				firstXPos = cell.getxPos();
			}
			if(lastXPos < cell.getxPos()) {
				lastXPos = cell.getxPos();
			}
			if(firstYPos > cell.getyPos()) {
				firstYPos = cell.getyPos();
			}
			if(lastYPos < cell.getyPos()) {
				lastYPos = cell.getyPos();
			}
		}
    	
    	// Calc. the neighbours for the cells
    	main.calcNeighbourNumber(firstXPos, lastXPos, firstYPos, lastYPos, cells);
		
		// Make grid based of the cell positions with surrounding boarder for new cells
		// Math abs used if grid is in negative pos
		int[][] grid = new int[Math.abs(lastXPos - firstXPos)+3][Math.abs(lastYPos - firstYPos)+3];
    	for(Cell cell:cells) {
    		grid[cell.getxPos()-firstXPos+1][cell.getyPos()-firstYPos+1] = 1;
    	}
    	
    	// After making grid, iterate over the old live cells to remove any which will now be dead
    	ArrayList<Cell> newCells = (ArrayList<Cell>) cells.clone();
    	for(Cell cell : cells) {
    		if(!cell.iterate()) {
    			newCells.remove(cell);
    		}
    	}
    	cells = newCells;
    	
    	// Loop through each cell in the grid and see if new cell can be created
    	for(int x=0; x<grid.length; x++) {
	        for(int y=0; y<grid[x].length; y++) {
	        	// Only add new cell if there is not one in the current position
	        	if(grid[x][y] == 0) {
		        	int totalNeighbours = 0;
		        	// case: corners of grid
		        	if(x == 0 && y == 0) {
		        		totalNeighbours = grid[x+1][y] + grid[x][y+1] + grid[x+1][y+1];
		        	} else if (x == grid.length-1 && y == grid[x].length-1) {
		        		totalNeighbours = grid[x-1][y] + grid[x][y-1] + grid[x-1][y-1];
		        	} else if(x == grid.length-1 && y == 0) {
		        		totalNeighbours = grid[x-1][y] + grid[x][y+1] + grid[x-1][y+1];
		        	} else if(x == 0 && y == grid[x].length-1) {
		        		totalNeighbours = grid[x+1][y] + grid[x][y-1] + grid[x+1][y-1];
		        	}
		        	// case: top of grid
		        	else if (x == grid.length-1) {
		        		totalNeighbours = grid[x][y+1] + grid[x][y-1] + grid[x-1][y] + grid[x-1][y+1] + grid[x-1][y-1];
		        	}
		        	// Case: Side of grid
		        	else if (y == grid[x].length-1) {
		        		totalNeighbours = grid[x+1][y] + grid[x-1][y] + grid[x][y-1] + grid[x+1][y-1] + grid[x-1][y-1];
		        	}
		        	// case: bottom of grid
		        	else if(x == 0) {
		        		totalNeighbours = grid[x][y+1] + grid[x][y-1] + grid[x+1][y] + grid[x+1][y+1] + grid[x+1][y-1];
		        	}
		        	// case: far side of grid
		        	else if (y == 0) {
		        		totalNeighbours = grid[x+1][y] + grid[x-1][y] + grid[x][y+1] + grid[x+1][y+1] + grid[x-1][y+1];
		        	} 
		        	// Ordinary case
		        	else {
		        		totalNeighbours = grid[x+1][y] + grid[x+1][y-1] + grid[x+1][y+1] + grid[x-1][y]
		        				+ grid[x-1][y-1] + grid[x-1][y+1] + grid[x][y+1] + grid[x][y-1];
		        	}
		        	// If there are exactly 3 neighbours create cell
		        	if(totalNeighbours == 3) {
		        		cells.add(new Cell(x+firstXPos-1, y+firstYPos-1));
		        	}
	        	}
	        }
    	}
    	return cells;
	}
	
	/**
	 * Calculate the number of neighbours each cell in an array of cells at a certain x,y have
	 * Given the first and last x and y positions of all the cells and the list of cells
	 * Will print out the grid of the cell positions
	 */
	public void calcNeighbourNumber(int firstXPos, int lastXPos, int firstYPos, int lastYPos, ArrayList<Cell> cells) {
		
		// Plot all the still alive cells on a grid
    	Object[][] finalGrid = new Object[Math.abs(lastXPos - firstXPos)+3][Math.abs(lastYPos - firstYPos)+3];
    	for(Cell cell:cells) {
    		finalGrid[cell.getxPos()-firstXPos+1][cell.getyPos()-firstYPos+1] = cell;
    	}
    	
    	// Calc. neighbour value and draw final grid
		// Array is looped through in a way that will make x and y match up to standard axis placements i.e.  ^ y > x
    	// Co-ordinates are also standardised: (x,y)
		String line = "";
        for(int y=finalGrid[0].length-1; y>-1; y--) {
        	for(int x=0; x<finalGrid.length; x++) {
	        	if(finalGrid[x][y] instanceof Cell) {
		            line = line + " O";
	        		// Count neighbours
	        		int totalNeighbours = 0;
		        	// case: corners of grid
		        	if(x == 0 && y == 0) {
		        		if(finalGrid[x+1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y+1] instanceof Cell) totalNeighbours++;
		        	} else if (x == finalGrid.length-1 && y == finalGrid[x].length-1) {
		        		if(finalGrid[x-1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y-1] instanceof Cell) totalNeighbours++;
		        	} else if(x == finalGrid.length-1 && y == 0) {
		        		if(finalGrid[x-1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y+1] instanceof Cell) totalNeighbours++;
		        	} else if(x == 0 && y == finalGrid[x].length-1) {
		        		if(finalGrid[x+1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y-1] instanceof Cell) totalNeighbours++;
		        	}
		        	// case: top of grid
		        	else if (x == finalGrid.length-1) {
		        		if(finalGrid[x][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y-1] instanceof Cell) totalNeighbours++;
		        	}
		        	// Case: Side of grid
		        	else if (y == finalGrid[x].length-1) {
		        		if(finalGrid[x+1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y-1] instanceof Cell) totalNeighbours++;
		        	}
		        	// case: bottom of grid
		        	else if(x == 0) {
		        		if(finalGrid[x][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y-1] instanceof Cell) totalNeighbours++;
		        	}
		        	// case: far side of grid
		        	else if (y == 0) {
		        		if(finalGrid[x+1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y+1] instanceof Cell) totalNeighbours++;
		        	} 
		        	// Ordinary case
		        	else {
		        		if(finalGrid[x+1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x+1][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x-1][y+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[x][y+1] instanceof Cell) totalNeighbours++;
		        	}
		        	((Cell) finalGrid[x][y]).setNumberOfNeighbours(totalNeighbours);
	        	} else {
		            line = line + " -";
	        	}
	        }
	        line = line + "\n";
    	}
		System.out.println(line);
	}
}
