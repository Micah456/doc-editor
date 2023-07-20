package test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import document.Document;
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
}
	