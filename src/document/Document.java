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
		return this.text.split("[\s\n]+").length;
		
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
}
