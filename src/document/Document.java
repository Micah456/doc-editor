package document;

import java.util.ArrayList;

public class Document {
	private String text;
	private ArrayList<SpellingError> spellingErrors;
	public Document(String text) {
		// TODO Auto-generated constructor stub
		this.text = text;
		this.spellingErrors = new ArrayList<>();
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
	public void runSpellCheck() {
		//Split document into words
		
		//Scan until spelling error found
		//Search original text for location
		//Check spelling error with same word and location don't exist
		//If exists, find next and so on
		//If doesn't exist, create spelling error and add to spellingerrors.
	}
	public ArrayList<SpellingError> getSpellingErrors(){
		return this.spellingErrors;
	}
	public class SpellingError{
		private String word;
		private ArrayList<String> topAlternatives;
		private int[] position;
		protected SpellingError(String word, int[] position) {
			this.word = word;
			this.position = position;
			this.topAlternatives = getAlternatives();
		}
		public String getWord() {
			return this.word;
		}
		public int[] getPosition() {
			return this.position;
		}
		public ArrayList<String> getTopAlternatives(){
			return this.topAlternatives;
		}
		private ArrayList<String> getAlternatives() {
			//TODO to be implemented
			return new ArrayList<>();
		}
		
	}
}
