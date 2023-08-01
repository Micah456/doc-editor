package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import document.Document;

public class TestDocument {
	@Test
	public void testWordCount() {
		String text = "This sentence has 5 words.";
		String text2 = "This document has 7 words.\nIt's true!";
		String text3 = "";
		String text4 = "-";
		String text5 = "Hello - there!";
		String text6 = "Yo";
		String text7 = "Yo!";
		String text8 = " ";
		String text9 = "Hi\n\nthere!";
		Document d = new Document(text);
		Document d2 = new Document(text2);
		Document d3 = new Document(text3);
		Document d4 = new Document(text4);
		Document d5 = new Document(text5);
		Document d6 = new Document(text6);
		Document d7 = new Document(text7);
		Document d8 = new Document(text8);
		Document d9 = new Document(text9);
		assertEquals(5, d.getNumWords());
		assertEquals(7, d2.getNumWords());
		assertEquals(0, d3.getNumWords());
		assertEquals(0, d4.getNumWords());
		assertEquals(2, d5.getNumWords());
		assertEquals(1, d6.getNumWords());
		assertEquals(1, d7.getNumWords());
		assertEquals(0, d8.getNumWords());
		assertEquals(2, d9.getNumWords());
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
	@Test
	public void testFindNext() {
		//Find next (first)
		int startIndex = 0;
		String text = "I love pie so much love!";
		Document d = new Document(text);
		int[] location = d.find(true, startIndex, "love");
		assertEquals(2, location[0]);
		assertEquals(5, location[1]);
		assertEquals("love", text.substring(location[0], location[1] +1 ));
		
		//Find next again (last)
		startIndex = location[0] + 1;
		location = d.find(true, startIndex, "love");
		assertEquals(19, location[0]);
		assertEquals(22, location[1]);
		assertEquals("love", text.substring(location[0], location[1] + 1));
		
		//Find prev (last)
		startIndex = text.length() - 1;
		location = d.find(false, startIndex, "love");
		assertEquals(19, location[0]);
		assertEquals(22, location[1]);
		assertEquals("love", text.substring(location[0], location[1] + 1));
		
		//Find prev again (first)
		startIndex = 18;
		location = d.find(false, startIndex, "love");
		assertEquals(2, location[0]);
		assertEquals(5, location[1]);
		assertEquals("love", text.substring(location[0], location[1] + 1));
		
		//Not found (next)
		startIndex = 0;
		text = "I dislike pie so much.";
		d = new Document(text);
		location = d.find(true, startIndex, "love");
		assertEquals(-1, location[0]);
		assertEquals(-1, location[1]);
		
		//Not found (prev)
		startIndex = text.length() - 1;
		location = d.find(false, startIndex, "love");
		assertEquals(-1, location[0]);
		assertEquals(-1, location[1]);
	}
	@Test
	public void testRunSpellCheck() {
		//TODO Create tests
		fail();
	}
	@Test
	public void testStrip() {
		String answer = "Hello";
		String strip1 = "Hello.";
		String strip2 = "'Hello";
		String strip3 = "'Hello,";
		String answer2 = "I'm";
		String strip4 = "I'm,";
		String strip5 = "'I'm";
		String strip6 = "I'm-";
		String strip7 = "-I'm-";
		
		assertEquals(answer, Document.strip(answer));
		assertEquals(answer, Document.strip(strip1));
		assertEquals(answer, Document.strip(strip2));
		assertEquals(answer, Document.strip(strip3));
		assertEquals(answer2, Document.strip(answer2));
		assertEquals(answer2, Document.strip(strip4));
		assertEquals(answer2, Document.strip(strip5));
		assertEquals(answer2, Document.strip(strip6));
		assertEquals(answer2, Document.strip(strip7));
	}
	@Test
	public void testContainsLetters() {
		String lettersUpper = "ABC";
		String lettersLower = "abc";
		String lettersBoth = "AbC";
		String lettersNum = "ABC1";
		String lettersPunc = ")-ABC";
		String nums = "123";
		String numPunc = "123-.;";
		String punc = ".,)!";
		assertTrue(Document.containsLetters(lettersUpper));
		assertTrue(Document.containsLetters(lettersLower));
		assertTrue(Document.containsLetters(lettersBoth));
		assertTrue(Document.containsLetters(lettersNum));
		assertTrue(Document.containsLetters(lettersPunc));
		assertFalse(Document.containsLetters(nums));
		assertFalse(Document.containsLetters(numPunc));
		assertFalse(Document.containsLetters(punc));
	}
}
