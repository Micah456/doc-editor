package document;

public class Document {
	private String text;
	public Document(String text) {
		// TODO Auto-generated constructor stub
		this.text = text;
	}
	/**
	 * Counts the number of words in a document. A word is any non-space character
	 * @return int number of words
	 */
	public int getNumWords(){
		if(this.text.isBlank()) {
			return 0;
		}
		String[] tokens = this.text.split("[\s\n]+");
		int words = tokens.length;
		for(String s : tokens) {
			if(s.matches("[^a-zA-Z0-9]+")) {
				words--;
			}
		}
		return words;
		
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
}
