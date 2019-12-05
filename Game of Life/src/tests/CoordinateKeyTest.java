package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import logic.CoordinateKey;

class CoordinateKeyTest {

	@Test
	void testHashCodeIsTheSame() {
		
		int num1 = 1;
		int num2 = 0;
		
		CoordinateKey c1 = new CoordinateKey(num1, num2);
		CoordinateKey c2 = new CoordinateKey(num1, num2);
		
		int c1HashCode = c1.hashCode();
		int c2HashCode = c2.hashCode();
		
		assertTrue(c1HashCode == c2HashCode);
	}
	
	@Test
	void testHashCodeIsDifferent() {
		
		int num1 = 0;
		int num2 = 1;
		
		CoordinateKey c1 = new CoordinateKey(num1, num2);
		CoordinateKey c2 = new CoordinateKey(num2, num1);
		
		int c1HashCode = c1.hashCode();
		int c2HashCode = c2.hashCode();
		
		assertFalse(c1HashCode == c2HashCode);
	}

	@Test
	void testEqualsIsTheSame() {
		
		int num1 = 1;
		int num2 = 0;
		
		CoordinateKey c1 = new CoordinateKey(num1, num2);
		CoordinateKey c2 = new CoordinateKey(num1, num2);
		
		assertTrue(c1.equals(c2));
	}
	
	@Test
	void testEqualsIsDifferent() {
		
		int num1 = 1;
		int num2 = 0;
		
		CoordinateKey c1 = new CoordinateKey(num1, num2);
		CoordinateKey c2 = new CoordinateKey(num2, num1);
		
		assertFalse(c1.equals(c2));
	}

}
