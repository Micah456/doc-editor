package document;

import java.util.ArrayList;

import document.SpellingErrorManager.SpellingError;

public class Document {
	private String text;
	private SpellingErrorManager sem;
	public Document(String text) {
		this.text = text;
		this.sem = new SpellingErrorManager();
	}
	/**
	 * Counts the number of words in a document. A word is any non-space character
	 * @return int number of words
	 */
	public int getNumWords(){
		/*if(this.text.isBlank()) {
			return 0;
		}
		String[] tokens = this.text.split("[\s\n]+");
		int words = tokens.length;
		for(String s : tokens) {
			if(s.matches("[^a-zA-Z0-9]+")) {
				words--;
			}
		}
		return words;*/
		return getWords().size();
		
	}
	/**
	 * Counts the number of lines in a document. If there is a newline at the
	 * end of the document, this does not count as a new line.
	 * @return int number of lines
	 */
	public int getNumLines() {
		if(this.text.isBlank()) {
			return 0;
		}
		return this.text.split("\n").length;
	}
	public int[] find(boolean findNext, int fromIndex, String query) {
		int[] location = new int[2];
		try {
			if(findNext) {
				location[0] = this.text.indexOf(query, fromIndex);
				
			}
			else {
				location[0] = this.text.lastIndexOf(query, fromIndex);
			}
			if(location[0] == -1) {
				location[1] = -1;
			}
			else {
				location[1] = location[0] + query.length() -1;
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			location[0] = -1;
			location[1] = -1;
		}
		return location;
	}
	public String getText() {
		return this.text;
	}
	private ArrayList<String> getWords(){
		ArrayList<String> words = new ArrayList<>();
		if(this.text.isBlank() || this.text.isEmpty()) {
			return words;
		}
		String[] tokens = this.text.split("[\s\n]+");
		for(String token : tokens) {
			if(!token.matches("[^a-zA-Z0-9]+")) {
				words.add(token);
			}
		}
		return words;
	}
	
	public int getNumCharacters() {
		return this.text.length();
	}
	
	public int getNumCharNoSpace() {
		int count = 0;
		for(int i = 0; i < this.text.length(); i++) {
			if(!Character.isWhitespace(this.text.charAt(i))) {
				count++;
			}
		}
		return count;
	}
		
	public void runSpellCheck(ArrayList<String> dictionary) {
		for(String word : dictionary) {
			word = word.toLowerCase();
		}
		//Clear SEM map before anything
		this.sem.clearMap();
		//Split document into words
		ArrayList<String> words = this.getWords();
		//Scan until
		for(String word : words) {
			//word containing letters is not in dictionary
			word = strip(word);
			if(containsLetters(word) && !dictionary.contains(word.toLowerCase())) {
				//Check if error already exists and get position of latest occurrence
				int[] latestPosition = sem.getLatestPosition(word);
				//Search original text for location starting from location of last occurrence
				int startIndex = this.text.indexOf(word, latestPosition[0] + 1);
				int endIndex = startIndex + word.length() - 1;
				//Create spelling error and add to sem
				sem.addError(word, new int[] {startIndex, endIndex});
			}
		}
	}
	public static boolean containsLetters(String word) {
		return word.matches("[^a-zA-Z]*[a-zA-Z]+[^a-zA-Z]*");
	}
	public static String strip(String word) {
		int startIndex = indexOfAlphaNumberical(word);
		int endIndex = lastIndexOfAlphaNumberical(word);
		return word.substring(startIndex,endIndex + 1);
	}
	private static int indexOfAlphaNumberical(String word) {
		
		for(int i = 0; i<word.length(); i++) {
			char c = word.charAt(i);
			if(Character.isDigit(c) || Character.isAlphabetic(c)) {
				return i;
			}
		}
		return -1;
	}
	private static int lastIndexOfAlphaNumberical(String word) {
		
		for(int i = word.length()-1; i>0; i--) {
			char c = word.charAt(i);
			if(Character.isDigit(c) || Character.isAlphabetic(c)) {
				return i;
			}
		}
		return -1;
	}

	public ArrayList<SpellingError> getSpellingErrors(){
		return this.sem.getSpellingErrors();
	}
	
}
