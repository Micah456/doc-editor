package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;
import document.SpellingErrorManager;

public class TestSpellingErrorManager {
	private final ArrayList<String> dict = getDictionary();
	
	@Test
	public void testClearMap() {
		fail();
		
	}
	@Test
	public void testAddError() {
		fail();
		
	}
	@Test
	public void testGetLatestPosition() {
		fail();
		
	}
	@Test
	public void testGetSpellingErrors() {
		fail();
		
	}
	/*@Test
	public void testGetTopAlternatives() {
		//TODO To implement getAlternatives before running this test
		fail();
	}*/
	
	private ArrayList<String> getDictionary(){
		ArrayList<String> dict = new ArrayList<>();
		dict.add("hello");
		dict.add("my");
		dict.add("name");
		dict.add("is");
		dict.add("bob");
		return dict;
	}
}
