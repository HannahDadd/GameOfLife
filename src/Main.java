import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		// Loop through each cell and draw the grid
		ArrayList<Cell> cells = new ArrayList<Cell>();
		cells.add(new Cell(0,0));
		cells.add(new Cell(1,0));
		cells.add(new Cell(2,0));
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
		
		// Make grid based of the cell positions with surrounding boarder for new cells
		// Math abs used if grid is in negative pos
		int[][] grid = new int[Math.abs(lastXPos - firstXPos)+3][Math.abs(lastYPos - firstYPos)+3];
    	for(Cell cell:cells) {
    		grid[cell.getxPos()-firstXPos+1][cell.getyPos()-firstYPos+1] = 1;
    	}
    	
    	// Loop through each cell in the grid and see if it's neighbours are live cells, if so create new cell
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
		        	}
		        	else if(i == grid.length-1 && j == 0) {
		        		totalNeighbours = grid[i-1][j] + grid[i][j+1] + grid[i-1][j+1];
		        	}
		        	else if(i == 0 && j == grid[i].length-1) {
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
		        		totalNeighbours = grid[i+1][j] + grid[i-1][j] + grid[i+1][j] + grid[i+1][j-1] + grid[i+1][j+1]
		        				+ grid[i-1][j] + grid[i-1][j-1] + grid[i-1][j+1];
		        	}
		        	// If there are exactly 2 neighbours create cell
		        	if(totalNeighbours == 3) {
		        		grid[i][j] = 2;
		        	}
	        	}
	        }
    	}
    	
    	// Print grid
		String line = "";
		for(int i=0; i<grid.length; i++) {
	        for(int j=0; j<grid[i].length; j++) {
	            line = line + " " + grid[i][j];
	        }
	        line = line + "\n";
	    }
		System.out.println(line);
	}
}
