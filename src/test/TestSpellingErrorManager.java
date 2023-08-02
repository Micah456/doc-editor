package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import document.SpellingErrorManager;
import document.SpellingErrorManager.SpellingError;

public class TestSpellingErrorManager {
	private SpellingErrorManager sem;
	
	@Before
	public void setUp() {
		sem = new SpellingErrorManager();
	}
	
	@Test
	public void testClearMap() { 
		assertEquals(0, sem.getCount());
		sem.addError("Hello", new int[] {3,8});
		sem.addError("you", new int[] {9,11});
		assertEquals(2, sem.getCount());
		sem.clearMap();
		assertEquals(0, sem.getCount());
		
	}
	@Test
	public void testGetCount() {
		assertEquals(0, sem.getCount());
		sem.addError("Hello", new int[] {3,8});
		sem.addError("you", new int[] {9,11});
		assertEquals(2, sem.getCount());
		
	}
	@Test
	public void testAddError() {
		String word = "Hello";
		int[] intArr1 = new int[] {3,8};
		int[] intArr2 = new int[] {10,15};
		
		//Add successfully
		assertTrue(sem.addError(word, intArr1));
		ArrayList<SpellingError> errors = sem.getSpellingErrors();
		assertEquals(1, errors.size());
		assertEquals(word, errors.get(0).getWord());
		assertEquals(intArr1, errors.get(0).getPosition());
		
		//Word already exists
		assertTrue(sem.addError(word, intArr2));
		errors = sem.getSpellingErrors();
		assertEquals(2, errors.size());
		assertEquals(intArr2, errors.get(1).getPosition());
		
		//Word and position already exists
		assertFalse(sem.addError(word, intArr1));
		assertEquals(2, sem.getCount());
		
		//Invalid parameters
		assertFalse(sem.addError("", intArr1));
		assertFalse(sem.addError(null, intArr1));
		assertFalse(sem.addError(word, null));
		assertEquals(2, sem.getCount());
	}
	@Test
	public void testGetLatestPosition() {
		String error = "Error";
		String panda = "Panda";
		int[] intArr1 = new int[] {3,8}; // first
		int[] intArr2 = new int[] {17,23}; // last
		int[] intArr3 = new int[] {10,15}; // middle
		int[] notFoundintArr = new int[] {-1,-1}; // not found
		//Single item
		sem.addError(error, intArr1);
		assertEquals(1, sem.getCount());
		assertEquals(intArr1, sem.getLatestPosition(error));
		//Two items
		sem.addError(error, intArr2);
		assertEquals(2, sem.getCount());
		assertEquals(intArr2, sem.getLatestPosition(error));
		//Three items
		sem.addError(error, intArr3);
		assertEquals(3, sem.getCount());
		assertEquals(intArr2[0], sem.getLatestPosition(error)[0]);
		assertEquals(intArr2[1], sem.getLatestPosition(error)[1]);
		//Doesn't exist
		assertEquals(notFoundintArr[0], sem.getLatestPosition(panda)[0]);
		assertEquals(notFoundintArr[1], sem.getLatestPosition(panda)[1]);
	}
	@Test
	public void testGetSpellingErrors() {
		String error = "Error";
		String panda = "Panda";
		int[] intArr1 = new int[] {17,23}; // third
		int[] intArr2 = new int[] {25,30}; // last
		int[] intArr3 = new int[] {10,15}; // second
		int[] intArr4 = new int[] {3,8}; // first
		sem.addError(error, intArr1); // third
		sem.addError(error, intArr2); // last
		sem.addError(panda, intArr3); // second
		sem.addError(error, intArr4); // first
		ArrayList<SpellingError> errors = sem.getSpellingErrors();
		assertEquals(4, errors.size());
		assertEquals(error, errors.get(0).getWord());
		assertEquals(intArr4, errors.get(0).getPosition());
		assertEquals(panda, errors.get(1).getWord());
		assertEquals(intArr3, errors.get(1).getPosition());
		assertEquals(error, errors.get(2).getWord());
		assertEquals(intArr1, errors.get(2).getPosition());
		assertEquals(error, errors.get(3).getWord());
		assertEquals(intArr2, errors.get(3).getPosition());
		
	}
	/*@Test
	public void testGetTopAlternatives() {
		//TODO To implement getAlternatives before running this test
		fail();
	}*/
}
