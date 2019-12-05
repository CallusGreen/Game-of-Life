package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import logic.Cell;

class CellTest {

	
	@Test
	void testGetNeighbours() {
		
		int coordinate1 = 2;
		int coordinate2 = 2;
		
		Cell testCell = new Cell(coordinate1, coordinate2);
		
		List<Cell> neighbours = new ArrayList<>();
		
		for(int rowCounter = coordinate2 - 1; rowCounter <= coordinate2 + 1; rowCounter++) {
			for(int cellCounter = coordinate1 - 1; cellCounter <= coordinate1 + 1; cellCounter++) {
				
				Cell neighbouringCell = new Cell(cellCounter, rowCounter);
				boolean cellIsOriginalCell = neighbouringCell.equals(testCell);
				
				if(!cellIsOriginalCell) {
					neighbours.add(neighbouringCell);
				}
			}
		}
		
		List<Cell> actual = testCell.getNeighbours();
		List<Cell> expected = neighbours;
		
		assertEquals(actual, expected);
		
	}
	
	@Test
	void testHashCodeIsTheSame() {
		
		int coordinate1 = 1;
		int coordinate2 = 0;
		
		Cell c1 = new Cell(coordinate1, coordinate2);
		Cell c2 = new Cell(coordinate1, coordinate2);
		
		int c1HashCode = c1.hashCode();
		int c2HashCode = c2.hashCode();
		
		assertTrue(c1HashCode == c2HashCode);
	}
	
	@Test
	void testHashCodeIsDifferent() {
		
		int coordinate1 = 0;
		int coordinate2 = 1;
		
		Cell c1 = new Cell(coordinate1, coordinate2);
		Cell c2 = new Cell(coordinate2, coordinate1);
		
		int c1HashCode = c1.hashCode();
		int c2HashCode = c2.hashCode();
		
		assertFalse(c1HashCode == c2HashCode);
	}

	@Test
	void testEqualsIsTheSame() {
		
		int coordinate1 = 1;
		int coordinate2 = 0;
		
		Cell c1 = new Cell(coordinate1, coordinate2, true);
		Cell c2 = new Cell(coordinate1, coordinate2, true);
		
		assertTrue(c1.equals(c2));
	}
	
	@Test
	void testEqualsIsDifferent() {
		
		int coordinate1 = 1;
		int coordinate2 = 0;
		
		Cell c1 = new Cell(coordinate1, coordinate2, true);
		Cell c2 = new Cell(coordinate2, coordinate1, true);
		
		assertFalse(c1.equals(c2));
	}

}
