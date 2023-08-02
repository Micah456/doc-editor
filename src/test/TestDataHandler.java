package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import main.DataHandler;

public class TestDataHandler{
	@Test
	public void testAppendFileExt() {
		String path = "Test.txt";
		String path2 = "Test";
		String path3 = "Test.txt.hi";
		assertEquals(path, DataHandler.appendFileExt(path));
		assertEquals(path2 + ".txt", DataHandler.appendFileExt(path2));
		assertEquals(path3 + ".txt", DataHandler.appendFileExt(path3));
	}
	
	@Test
	public void testGetDictionaries() {
		HashMap<String, ArrayList<String>> dictionaries = 
				DataHandler.getDictionaries(new File("testData/dictionaries"));
		ArrayList<String> test_dict = new ArrayList<>();
		test_dict.add("Hello");
		test_dict.add("My");
		test_dict.add("Name");
		test_dict.add("Is");
		test_dict.add("Bob");
		ArrayList<String> test2_dict = new ArrayList<>();
		test2_dict.add("I");
		test2_dict.add("Like");
		test2_dict.add("Orange");
		test2_dict.add("Juice");
		test2_dict.add("A");
		test2_dict.add("Lot");
		
		assertTrue(dictionaries.containsKey("test_dict"));
		assertTrue(dictionaries.containsKey("test2_dict"));
		
		for(int i = 0; i<test_dict.size(); i++) {
			assertEquals(test_dict.get(i), dictionaries.get("test_dict").get(i));
		}
		for(int i = 0; i<test2_dict.size(); i++) {
			assertEquals(test2_dict.get(i), dictionaries.get("test2_dict").get(i));
		}
	}
	
}
	