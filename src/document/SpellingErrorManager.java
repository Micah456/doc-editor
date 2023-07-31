package document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

public class SpellingErrorManager {
	private HashMap<String,ArrayList<SpellingError>> spellingErrorMap;
	public SpellingErrorManager() {
		this.spellingErrorMap = new HashMap<>();
	}
	public void clearMap() {
		this.spellingErrorMap.clear();
	}
	public void addError(String word, int[] position) {
		
	}
	public int[] getLatestPosition(String word) {
		if(spellingErrorMap.containsKey(word)) {
			ArrayList<SpellingError> errors = spellingErrorMap.get(word);
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
		errors.sort(new Comparator<SpellingError>() {

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
			
		});
		return errors;
	}
	public class SpellingError{
		private String word;
		private ArrayList<String> topAlternatives;
		private int[] position;
		private int occurrance;
		protected SpellingError(String word, int[] position, int occurrance) {
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
