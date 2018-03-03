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
		
		// Make grid based on the cell positions
		// Math abs used if grid is in negative pos
		boolean[][] grid = new boolean[Math.abs(lastXPos - firstXPos)+1][Math.abs(lastYPos - firstYPos)+1];
    	for(Cell cell:cells) {
    		grid[cell.getxPos()-firstXPos][cell.getyPos()-firstYPos] = true;
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
