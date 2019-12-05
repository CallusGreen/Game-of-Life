package logic;

import java.util.ArrayList;
import java.util.List;

public class Cell {

	
	// ------------ Class Variables ------------ //

	private int xCoordinate;
	private int yCoordinate;
	private boolean cellAlive;

	// ------------- Constructors -------------- //

	public Cell(int xCoordinate, int yCoordinate, boolean cellAlive) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.cellAlive = cellAlive;
	}

	public Cell(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	public Cell() {}

	// ---------------- Methods ---------------- //

	// returns the cell as an easy-to-read string
	public String cellCoordinates() {
		return "(" + getxCoordinate() + "," + getyCoordinate() + ")";
	}


	// returns the eight neighbouring cells in an arraylist
	public List<Cell> getNeighbours(){

		ArrayList<Cell> neighbours = new ArrayList<Cell>();

		for(int rowCounter = getyCoordinate() - 1; rowCounter <= getyCoordinate() + 1; rowCounter++) {
			for(int cellCounter = getxCoordinate() - 1; cellCounter <= getxCoordinate() + 1; cellCounter++) {
				Cell tempCell = new Cell(cellCounter, rowCounter);

				// original cell is not added to the list
				if(!tempCell.equals(this)) {
					neighbours.add(tempCell);
				}
			}
		}

		return neighbours;
	}

	// ----------- Getters & Setters ----------- //

	public int getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public boolean isCellAlive() {
		return cellAlive;
	}
	public void setCellAlive(boolean cellAlive) {
		this.cellAlive = cellAlive;
	}

	// ----------- Equals & HashCode ----------- //

	public boolean equals(Object object) {
		if(object == null) {
			return false;
		}

		if(getClass() != object.getClass()) {
			return false;
		}

		Cell compared = (Cell) object;

		if(this.xCoordinate != compared.getxCoordinate() || this.yCoordinate != compared.getyCoordinate()) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		return 17 * this.xCoordinate + this.yCoordinate;
	}

}
