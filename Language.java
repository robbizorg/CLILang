import java.util.ArrayList;
import java.util.Collections;

public class Language {
	private String lang;
	private ArrayList<WordWrapper> words;
	private int size;

	public Language(String lang, ArrayList<String[]> words) {
		ArrayList<WordWrapper> wrappers = new ArrayList<WordWrapper>();

		this.lang = lang;
		for (String[] wordPair : words) {
			wrappers.add(new WordWrapper(wordPair, 0));
		}
		this.words = wrappers;
		size = words.size();
	}

	// Accessor Methods
	public String getLang() {
		return lang;
	}

	public ArrayList<String[]> getWords() {
		ArrayList<String[]> listOfWords = new ArrayList<String[]>();
		for (WordWrapper wrapper : words) {
			listOfWords.add(wrapper.wordPair);
		}

		return listOfWords;
	}

	public int size() {
		return size;
	}

	// Difficulty Setter/Getter Methods
	// Difficulty Getter Method
	public int getDifficulty(String word) {
		for (WordWrapper wrapper : words) {
			if (wrapper.wordPair[0].equals(word)) {
				return wrapper.difficulty;
			}
		}
		// -1 means the word was not found
		return -1;
	}

	// Dificulty Setter Method
	public void setDifficulty(String word, int newDif) {
		for (WordWrapper wrapper : words) {
			if (wrapper.wordPair[0].equals(word)) {
				wrapper.difficulty = newDif;
			}
		}
	}

	// Sort the Words based on Difficulty
	public void sortWords() {
		Collections.sort(words);
	}

	public void printWords() {
		for (WordWrapper wrapper : words) {
			System.out.println(wrapper.wordPair[0] + ": " + wrapper.wordPair[1]);
		}
	}

	// Mutator Methods

	// Adds a word and its translation to the list of words
	public void add(String[] word) {
		words.add(new WordWrapper(word, 0));
		size++;
	}

	public String[] remove(String[] word) {
		words.remove(word);
		size--;
		return word;
	}

	// Word Wrapper Class
	private static class WordWrapper implements Comparable<WordWrapper> {
		String[] wordPair;
		int difficulty;

		WordWrapper(String[] wordPair, int difficulty) {
			this.wordPair = wordPair;
			this.difficulty = difficulty;
		}

		public int compareTo(WordWrapper other) {
			if (this.difficulty < other.difficulty) {
				return -1;
			} else if (this.difficulty > other.difficulty) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}