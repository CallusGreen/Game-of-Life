package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import logic.Cell;
import logic.CoordinateKey;
import logic.Grid;

class GridTest {

	@Test
	void testHowManyLiveNeighbours() {
		
		Cell centralCell = new Cell(5, 5, true);
		Cell liveNeighbour1 = new Cell(5, 4, true);
		Cell liveNeighbour2 = new Cell(5, 6, true);
		
		Grid grid = new Grid();
		
		grid.getLiveCells().put(new CoordinateKey(5,5), centralCell);
		grid.getLiveCells().put(new CoordinateKey(5,4), liveNeighbour1);
		grid.getLiveCells().put(new CoordinateKey(5,6), liveNeighbour2);
		
		int expected = 2;
		int actual = grid.howManyLiveNeighbours(centralCell);
		
		assertTrue(expected == actual);
	}

	@Test
	void testCellCreation() {
		
		Cell liveNeighbour1 = new Cell(5, 4, true);
		Cell liveNeighbour2 = new Cell(5, 6, true);
		Cell liveNeighbour3 = new Cell(4, 5, true);
		
		Grid grid = new Grid();
		
		grid.getLiveCells().put(new CoordinateKey(5,4), liveNeighbour1);
		grid.getLiveCells().put(new CoordinateKey(5,6), liveNeighbour2);
		grid.getLiveCells().put(new CoordinateKey(4,5), liveNeighbour3);
		
		int expectedBefore = 0;
		int actualBefore = grid.getCellsToBeCreated().size();
				
		assertTrue(expectedBefore == actualBefore);
		
		grid.cellCreation();
		
		int expectedAfter = 1;
		int actualAfter = grid.getCellsToBeCreated().size();
		
		assertTrue(expectedAfter == actualAfter);
	}

	@Test
	void testKillCell() {
		
		Cell livingCell = new Cell(5, 5, true);
		Grid grid = new Grid();
		
		int expectedBefore = 0;
		int actualBefore = grid.getCellsToBeDestroyed().size();
		
		assertTrue(expectedBefore == actualBefore);
		
		grid.killCell(livingCell);
		
		int expectedAfter = 1;
		int actualAfter = grid.getCellsToBeDestroyed().size();
		
		assertTrue(expectedAfter == actualAfter);

	}

	@Test
	void testCellExistsInMap() {
		
		Cell cell = new Cell(5,5,true);
		Cell cell1 = new Cell(5,6,true);
		Cell cell2 = new Cell(5,7,true);
		
		Grid grid = new Grid();
		
		HashMap<CoordinateKey, Cell> map = new HashMap<CoordinateKey, Cell>();
		
		map.put(new CoordinateKey(5,5), cell);
		map.put(new CoordinateKey(5,6), cell1);
		map.put(new CoordinateKey(5,7), cell2);
		
		boolean expected = true;
		boolean actual = grid.cellExistsInMap(cell, map);
		
		assertTrue(expected == actual);
	}

	@Test
	void testCreateCell() {
		
		Grid grid = new Grid();
		
		Cell cell = new Cell(5,5);
		
		grid.createCell(5, 5);
		
		boolean expected = true;
		boolean actual = grid.cellExistsInMap(cell, grid.getCellsToBeCreated());
		
		assertTrue(expected == actual);
		
	}

	@Test
	void testEndOfIteration() {
		Grid grid = new Grid();
		Cell liveCell = new Cell(5,5,true);
		Cell deadCell = new Cell(1,1,false);
		
		grid.getLiveCells().put(new CoordinateKey(5,5), liveCell);
		grid.killCell(liveCell);
		
		grid.createCell(1, 1);
		
		grid.endOfIteration();
		
		boolean expected = true;
		boolean actual = grid.getLiveCells().size() == 1 && grid.cellExistsInMap(deadCell, grid.getLiveCells());
		
		assertTrue(expected == actual);
		
	}

	@Test
	void testIsCellAlive() {
	
		Grid grid = new Grid();
		Cell cell = new Cell(5,5);
		
		grid.getLiveCells().put(new CoordinateKey(5,5), cell);
		
		boolean expected = true;
		boolean actual = grid.isCellAlive(5, 5);
		
		assertTrue(expected == actual);
	}

	@Test
	void testAddCellsRandomly() {
		
		Grid grid = new Grid();
		
		grid.addCellsRandomly(5);
		
		int expected = 5;
		int actual = grid.getLiveCells().size();
		
		assertTrue(expected == actual);
		
	}
	
	@Test
	void testAddCellsManually() {
		
		Grid grid = new Grid();
		
		ArrayList<Cell> cells = new ArrayList<>();
		
		cells.add(new Cell(5,5));
		cells.add(new Cell(5,6));
		cells.add(new Cell(5,7));
		cells.add(new Cell(5,8));
		cells.add(new Cell(5,9));
		
		grid.addCellsManually(cells);
		
		int expected = 5;
		int actual = grid.getLiveCells().size();
		
		assertTrue(expected == actual);
		
		Cell tempCell = new Cell(5,9);
		
		boolean expected2 = true;
		boolean actual2 = grid.cellExistsInMap(tempCell, grid.getLiveCells());
		
		assertTrue(expected2 == actual2);
	}

}
