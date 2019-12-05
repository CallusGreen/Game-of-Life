package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Map;

public class Grid {

	// ------------ Class Variables ------------ //

	private Map<CoordinateKey, Cell> liveCells;
	private Map<CoordinateKey, Cell> cellsToBeDestroyed;
	private Map<CoordinateKey, Cell> cellsToBeCreated;

	// ------------- Constructors -------------- //

	public Grid() {
		this.liveCells = new HashMap<>();
		this.cellsToBeDestroyed = new HashMap<>();
		this.cellsToBeCreated = new HashMap<>();
	}

	// ---------------- Methods ---------------- //

	public int howManyLiveNeighbours(Cell cell) {
		int numLiveNeighbours = 0;

		for(Cell tempCell : cell.getNeighbours()) {
			boolean neighbourIsAlive = isCellAlive(tempCell.getxCoordinate(), tempCell.getyCoordinate());
			if(neighbourIsAlive) {
				numLiveNeighbours++;
			}
		}

		return numLiveNeighbours;
	}

	// Method for creating cells with 3 live neighbours
	public void cellCreation() {
		// Iterates through currently living cells
		for(Cell tempCell : getLiveCells().values()) {
			// Iterates through neighbours of live cells
			for(Cell theNeighbour : tempCell.getNeighbours()) {
				// ensures cell is not already up for creation and has 3 living neighbours
				if(howManyLiveNeighbours(theNeighbour) == 3 
						&& !isCellAlive(theNeighbour.getxCoordinate(), theNeighbour.getyCoordinate()) 
						&& !cellExistsInMap(theNeighbour, getCellsToBeCreated())) {
					getCellsToBeCreated().put(new CoordinateKey(theNeighbour.getxCoordinate(), theNeighbour.getyCoordinate()), theNeighbour);
				}
			}
		}
	}

	public void killCell(Cell cell) {
		CoordinateKey tempKey = new CoordinateKey(cell.getxCoordinate(), cell.getyCoordinate());
		getCellsToBeDestroyed().put(tempKey, cell);
	}

	public boolean cellExistsInMap(Cell cell, Map<CoordinateKey, Cell> map) {
		if(map.containsKey(new CoordinateKey(cell.getxCoordinate(), cell.getyCoordinate()))) {
			return true;
		}
		return false;
	}

	public void createCell(int x, int y) {
		Cell tempCell = new Cell(x, y, true);
		getCellsToBeCreated().put(new CoordinateKey(x, y), tempCell);
	}

	// Method that is called at the end of each turn that applies changes made
	public void endOfIteration() {

		// Destroys cells up for destruction
		if(getCellsToBeDestroyed().size() > 0) {
			for(Cell tempCell : getCellsToBeDestroyed().values()) {
				tempCell.setCellAlive(false);
			}
			getLiveCells().keySet().removeAll(getCellsToBeDestroyed().keySet());
		}

		// Creates cells up for creation
		if(getCellsToBeCreated().size() > 0) {
			for(Cell tempCell : getCellsToBeCreated().values()) {
				tempCell.setCellAlive(true);
			}
			getLiveCells().putAll(getCellsToBeCreated());
		}

		// Clears the maps, ready for next iteration
		getCellsToBeDestroyed().clear();
		getCellsToBeCreated().clear();
	}

	public boolean isCellAlive(int x, int y) {
		if(cellExistsInMap(new Cell(x, y), getLiveCells())) {
			return true;
		}
		return false;
	}

	// Adds number of cells defined by parameter at random points on the grid
	// Needs work as these cells are very unlikely to ever interact with each other
	// and will usually only survive one generation
	public void addCellsRandomly(int numCells) {

		// max int value, could be extended with long type
		final int MAX_INT_VALUE = 2147483647;

		Random rand = new Random();

		for(int i = 0; i < numCells; i++) {

			int xCoordinate = 0;
			int yCoordinate = 0;

			Cell createdCell;
			CoordinateKey theKey;

			while(true) {
				// randomly generates x & y coordinates, ~50% chance each coordinate will be negative
				double xNegativeChance = rand.nextDouble();
				xCoordinate = rand.nextInt(MAX_INT_VALUE);

				if(xNegativeChance < 0.5) {
					xCoordinate *= -1;
				}

				double yNegativeChance = rand.nextDouble();
				yCoordinate = rand.nextInt(MAX_INT_VALUE);

				if(yNegativeChance < 0.5) {
					yCoordinate *= -1;
				}

				createdCell = new Cell(xCoordinate, yCoordinate, true);

				// generates new cell, if cell is already alive
				if(!isCellAlive(xCoordinate, yCoordinate)) {
					theKey = new CoordinateKey(xCoordinate, yCoordinate);
					break;
				}
			}
			getLiveCells().put(theKey, createdCell);
		}
	}

	// Allows the user to add a pre-defined list of cells to the grid.
	public void addCellsManually(List<Cell> cells) {

		for(Cell tempCell : cells) {

			int xCoord = tempCell.getxCoordinate();
			int yCoord = tempCell.getyCoordinate();

			CoordinateKey cellKey = new CoordinateKey(xCoord, yCoord);
			boolean cellIsNotAlreadyAlive = !isCellAlive(xCoord, yCoord);

			if(cellIsNotAlreadyAlive) {
				getLiveCells().put(cellKey, tempCell);
			}
		}
	}

	// Prints the currently living cell's coordinates
	public void printLiveCells() {
		String output = "";

		for(Cell tempCell : getLiveCells().values()) {
			output += tempCell.cellCoordinates() + " ";
		}

		if(getLiveCells().size() != 0) {
			System.out.println("Live Cells: " + output);
		}
	}

	// ----------- Getters & Setters ----------- //

	public Map<CoordinateKey, Cell> getLiveCells() {
		return liveCells;
	}

	public void setLiveCells(Map<CoordinateKey, Cell> liveCells) {
		this.liveCells = liveCells;
	}

	public Map<CoordinateKey, Cell> getCellsToBeDestroyed() {
		return cellsToBeDestroyed;
	}

	public void setCellsToBeDestroyed(Map<CoordinateKey, Cell> cellsToBeDestroyed) {
		this.cellsToBeDestroyed = cellsToBeDestroyed;
	}

	public Map<CoordinateKey, Cell> getCellsToBeCreated() {
		return cellsToBeCreated;
	}

	public void setCellsToBeCreated(Map<CoordinateKey, Cell> cellsToBeCreated) {
		this.cellsToBeCreated = cellsToBeCreated;
	}
}
