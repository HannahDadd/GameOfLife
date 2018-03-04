# GameOfLife

An implementation of the Game of Life written in Java. Results are written to the console, live cells are Os and empty spaces are -. 

The grid is drawn around the living cells with a surrounding, single cell wide, empty space border. Empty spaces are not shown if they are not close to a living cell i.e. 0, 0 is sometimes not shown. Cells can be drawn in states at any co-ordinate within the natural numbers, including negative co-ordinates but not decimals. The grid is infinitely long.

The first grid printed is the initial grid followed by subsequent states from each iteration. If 5 iterations are put into the game, 6 grids will be produced, the first one being the initial seed.

## Assumptions

* Live cells created by the user in the initial seed are provided with the x then y co-ordinate i.e. new Cell(xPos, yPos).
* All co-ordinates provided are integers, not floats. Cells can only be at integer co-ordinates.
* The user can provide the program with the initial cell positions and how many iterations should be performed. There is no GUI for this program.
* Users can access the console to view the printed results after running the program.
