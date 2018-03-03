import java.util.ArrayList;

public class Cell {
	// Store co-odinate location of cell and neighbour number
	int xPos;
	int yPos;
	ArrayList<Cell> neighbours;
	
	public Cell(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		
		// Store references to 8 possible neighbours
		this.neighbours = new ArrayList<Cell>();
	}
	
	
	/**
	 * Run an evolutionary iteration on this cell
	 * Death of cell = return false, else true
	 */
	public boolean iterate() {
		// Cell has fewer than two neighbours it dies
		if(this.neighbours.size() < 2) {
			return false;
		}
		// Cells with more than 3 neighbours it is overcrowded and dies
		if(this.neighbours.size() > 3) {
			return false;
		}
		return true;
	}
	
	/**
	 * Cell that was neighbour has died so must be removed
	 */
	public boolean removeNeighbour(Cell cell) {
		return this.neighbours.remove(cell);
	}
	
	/**
	 * Add a neighbour, left, right, up, down or diagonal to this cell
	 */
	public boolean addNeighbour(Cell cell) {
		// No. of neighbours capped at 8
		if(this.neighbours.size() < 8) {
			neighbours.add(cell);
			return true;
		} else {
			return false;
		}
	}
}
