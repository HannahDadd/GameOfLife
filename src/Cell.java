public class Cell {
	// Store co-odinate location of cell and neighbour number
	int xPos;
	int yPos;
	int numberOfNeighbours;
	int gridPosX;
	int gridPosY;
	
	public Cell(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.numberOfNeighbours = 0;
	}
	
	/**
	 * Run an evolutionary iteration on this cell
	 * Death of cell = return false, else true
	 */
	public boolean iterate() {
		System.out.println(this.numberOfNeighbours);
		// Cell has fewer than two neighbours it dies
		if(this.numberOfNeighbours < 2) {
			return false;
		}
		// Cells with more than 3 neighbours it is overcrowded and dies
		if(this.numberOfNeighbours > 3) {
			return false;
		}
		return true;
	}
	
	/**
	 * Set the x, y grid pos of this cell in the current's state grid
	 */
	public void setGridPos(int x, int y) {
		this.gridPosX = x;
		this.gridPosY = y;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public int getGridPosX() {
		return gridPosX;
	}

	public int getGridPosY() {
		return gridPosY;
	}

	public void setNumberOfNeighbours(int numberOfNeighbours) {
		this.numberOfNeighbours = numberOfNeighbours;
	}
}
