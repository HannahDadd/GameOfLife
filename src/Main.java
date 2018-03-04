import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		// Initial seed
		Main main = new Main();
		ArrayList<Cell> cells = new ArrayList<Cell>();
		cells.add(new Cell(0,0));
		cells.add(new Cell(1,0));
		cells.add(new Cell(2,0));
		main.iterate(cells, main);
	}
	
	/**
	 * Iterate between one state and the next in the game of life
	 */
	public void iterate(ArrayList<Cell> cells, Main main) {
		
		// Loop through each cell and draw the grid
		int firstXPos = cells.get(0).getxPos();
		int firstYPos = cells.get(0).getyPos();
		int lastXPos = cells.get(0).getxPos();
		int lastYPos = cells.get(0).getyPos();
		
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
    	
    	// After making grid, iterate over the old live cells
    	ArrayList<Cell> newCells = (ArrayList<Cell>) cells.clone();
    	for(Cell cell : cells) {
    		if(!cell.iterate()) {
    			newCells.remove(cell);
    		}
    	}
    	cells = newCells;
    	
    	// Loop through each cell in the grid and see if new cell can be created
    	for(int i=0; i<grid.length; i++) {
	        for(int j=0; j<grid[i].length; j++) {
	        	// Only add new cell if there is not one in the current position
	        	if(grid[i][j] == 0) {
		        	int totalNeighbours = 0;
		        	// case: corners of grid
		        	if(i == 0 && j == 0) {
		        		totalNeighbours = grid[i+1][j] + grid[i][j+1] + grid[i+1][j+1];
		        	} else if (i == grid.length-1 && j == grid[i].length-1) {
		        		totalNeighbours = grid[i-1][j] + grid[i][j-1] + grid[i-1][j-1];
		        	} else if(i == grid.length-1 && j == 0) {
		        		totalNeighbours = grid[i-1][j] + grid[i][j+1] + grid[i-1][j+1];
		        	} else if(i == 0 && j == grid[i].length-1) {
		        		totalNeighbours = grid[i+1][j] + grid[i][j-1] + grid[i+1][j-1];
		        	}
		        	// case: top of grid
		        	else if (i == grid.length-1) {
		        		totalNeighbours = grid[i][j+1] + grid[i][j-1] + grid[i-1][j] + grid[i-1][j+1] + grid[i-1][j-1];
		        	}
		        	// Case: Side of grid
		        	else if (j == grid[i].length-1) {
		        		totalNeighbours = grid[i+1][j] + grid[i-1][j] + grid[i][j-1] + grid[i+1][j-1] + grid[i-1][j-1];
		        	}
		        	// case: bottom of grid
		        	else if(i == 0) {
		        		totalNeighbours = grid[i][j+1] + grid[i][j-1] + grid[i+1][j] + grid[i+1][j+1] + grid[i+1][j-1];
		        	}
		        	// case: far side of grid
		        	else if (j == 0) {
		        		totalNeighbours = grid[i+1][j] + grid[i-1][j] + grid[i][j+1] + grid[i+1][j+1] + grid[i-1][j+1];
		        	} 
		        	// Ordinary case
		        	else {
		        		totalNeighbours = grid[i+1][j] + grid[i-1][j] + grid[i+1][j-1] + grid[i+1][j+1]
		        				+ grid[i-1][j-1] + grid[i-1][j+1];
		        	}
		        	// If there are exactly 2 neighbours create cell
		        	if(totalNeighbours == 3) {
		        		grid[i][j] = 2;
		        		cells.add(new Cell(i+firstXPos-1, j+firstYPos-1));
		        	}
	        	}
	        }
    	}
    	
    	// Print intermediate grid with 2s in positions where cells are being created and 1s where current cells are
    	// Has not removed dead cells yet
//		String line = "";
//		for(int i=0; i<grid.length; i++) {
//	        for(int j=0; j<grid[i].length; j++) {
//	            line = line + " " + grid[i][j];
//	        }
//	        line = line + "\n";
//	    }
//		System.out.println(line);
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
		String line = "";
    	for(int i=0; i<finalGrid.length; i++) {
	        for(int j=0; j<finalGrid[i].length; j++) {
	            line = line + " " + finalGrid[i][j];
	        	if(finalGrid[i][j] instanceof Cell) {
	        		// Count neighbours
	        		int totalNeighbours = 0;
		        	// case: corners of grid
		        	if(i == 0 && j == 0) {
		        		if(finalGrid[i+1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j+1] instanceof Cell) totalNeighbours++;
		        	} else if (i == finalGrid.length-1 && j == finalGrid[i].length-1) {
		        		if(finalGrid[i-1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i][j-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j-1] instanceof Cell) totalNeighbours++;
		        	} else if(i == finalGrid.length-1 && j == 0) {
		        		if(finalGrid[i-1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j+1] instanceof Cell) totalNeighbours++;
		        	} else if(i == 0 && j == finalGrid[i].length-1) {
		        		if(finalGrid[i+1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i][j-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j-1] instanceof Cell) totalNeighbours++;
		        	}
		        	// case: top of grid
		        	else if (i == finalGrid.length-1) {
		        		if(finalGrid[i][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i][j-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j-1] instanceof Cell) totalNeighbours++;
		        	}
		        	// Case: Side of grid
		        	else if (j == finalGrid[i].length-1) {
		        		if(finalGrid[i+1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i][j-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j-1] instanceof Cell) totalNeighbours++;
		        	}
		        	// case: bottom of grid
		        	else if(i == 0) {
		        		if(finalGrid[i][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i][j-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j-1] instanceof Cell) totalNeighbours++;
		        	}
		        	// case: far side of grid
		        	else if (j == 0) {
		        		if(finalGrid[i+1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j+1] instanceof Cell) totalNeighbours++;
		        	} 
		        	// Ordinary case
		        	else {
		        		if(finalGrid[i+1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i+1][j+1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j-1] instanceof Cell) totalNeighbours++;
		        		if(finalGrid[i-1][j+1] instanceof Cell) totalNeighbours++;
		        	}
		        	((Cell) finalGrid[i][j]).setNumberOfNeighbours(totalNeighbours);
	        	}
	        }
	        line = line + "\n";
    	}
		System.out.println(line);
	}
}
