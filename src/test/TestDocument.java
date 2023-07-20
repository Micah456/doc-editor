package test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import document.Document;

public class TestDocument {
	@Test
	public void testWordCount() {
		String text = "This sentence has 5 words.";
		String text2 = "This document has 7 words.\nIt's true!";
		String text3 = "";
		Document d = new Document(text);
		Document d2 = new Document(text2);
		Document d3 = new Document(text3);
		assertEquals(5, d.getNumWords());
		assertEquals(7, d2.getNumWords());
		assertEquals(0, d3.getNumWords());
	}
	@Test
	public void testLineCount() {
		String text = "This document has 1 line.";
		String text2 = "This document has 2 lines.\nIt's true!";
		String text3 = "This document has 1 line.\n";
		String text4 = "";
		Document d = new Document(text);
		Document d2 = new Document(text2);
		Document d3 = new Document(text3);
		Document d4 = new Document(text4);
		assertEquals(1, d.getNumLines());
		assertEquals(2, d2.getNumLines());
		assertEquals(1, d3.getNumLines());
		assertEquals(0, d4.getNumLines());
	}
}
