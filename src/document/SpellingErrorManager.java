package document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class SpellingErrorManager {
	private HashMap<String,ArrayList<SpellingError>> spellingErrorMap;
	private Comparator<SpellingError> errorComparator;
	public SpellingErrorManager() {
		this.spellingErrorMap = new HashMap<>();
		this.errorComparator = getComparator();
	}
	public void clearMap() {
		this.spellingErrorMap.clear();
	}
	/**
	 * Adds a SpellingError to the SpellingErrorManager. 
	 * @param word String that represents the error
	 * @param position int[] referring to the position of the error in the text
	 * @return true if added successfully, false if already exists or
	 * if any of the arguments are invalid (including blank and null values)
	 */
	public boolean addError(String word, int[] position) {
		if(word == null || word.isBlank()|| position == null) {
			System.out.println("Invalid arguments.");
			return false;
		}
		ArrayList<SpellingError> errors = this.spellingErrorMap.get(word);
		if(errors != null) {
			for(SpellingError e : errors) {
				if(e.getPosition() == position) {
					return false;
				}
			}
			errors.add(new SpellingError(word,position));
			return true;
		}
		else {
			ArrayList<SpellingError> newErrorList = new ArrayList<>();
			newErrorList.add(new SpellingError(word,position));
			this.spellingErrorMap.put(word, newErrorList);
			return true;
		}
	}
	public int[] getLatestPosition(String word) {
		if(spellingErrorMap.containsKey(word)) {
			ArrayList<SpellingError> errors = spellingErrorMap.get(word);
			errors.sort(errorComparator);
			return errors.get(errors.size()-1).getPosition();
		}
		else return new int[] {-1,-1};
	}
	public ArrayList<SpellingError> getSpellingErrors(){
		ArrayList<SpellingError> errors = new ArrayList<>();
		//Add all spelling errors
		for(String key : spellingErrorMap.keySet()) {
			errors.addAll(spellingErrorMap.get(key));
		}
		//Sort spelling errors by startPosition
		errors.sort(errorComparator);
		return errors;
	}
	public int getCount() {
		int count = 0;
		for(String key : this.spellingErrorMap.keySet()) {
			count+=this.spellingErrorMap.get(key).size();
		}
		return count;
	}
	private Comparator<SpellingError> getComparator() {
		return new Comparator<SpellingError>() {

			@Override
			public int compare(SpellingError o1, SpellingError o2) {
				// TODO Auto-generated method stub
				if(o1.getPosition()[0] - o2.getPosition()[0] == 0){
                    return 0;
                }
                else if(o1.getPosition()[0] - o2.getPosition()[0] > 0){
                    return 1;
                }
                else{
                    return -1;
                }
			}
			
		};
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
