import java.util.ArrayList;

public class Language {
	private String lang;
	private ArrayList<String[]> words;
	private int size;

	public Language(String lang, ArrayList<String[]> words) {
		this.lang = lang;
		this.words = words;
		size = words.size();
	}

	// Accessor Methods
	public String getLang() {
		return lang;
	}

	public ArrayList<String[]> getWords() {
		return words;
	}

	public int size() {
		return size;
	}

	public void printWords() {
		for (String[] wordPair : words) {
			System.out.println(wordPair[0] + ": " + wordPair[1]);
		}
	}

	// Mutator Methods

	// Adds a word and its translation to the list of words
	public void add(String[] word) {
		words.add(word);
		size++;
	}

	public String[] remove(String[] word) {
		words.remove(word);
		return word;
	}
}