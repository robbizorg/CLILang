import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class CLILang {
	public static final void main(String[] args) {
		try {

			ArrayList<String[]> words = new ArrayList<String[]>();

			Scanner in = new Scanner(new File(args[0]));
			System.out.println("===CLILANG===");
			if (!in.hasNextLine()) {
				System.out.println("Please rerun with proper dictionary format described in README");
			}

			String languageName = in.nextLine().split(":")[1];

			while (in.hasNextLine()) {
				String[] wordPair = parseLine(in.nextLine());
				words.add(wordPair);
			}

			Language lang = new Language(languageName, words);

			String choice = "";
			while (!choice.equals("exit")) {
				System.out.println("What would you like to do?");
				Scanner input = new Scanner(System.in);
				choice = input.nextLine();	
				lang = parseChoice(choice, lang, input);

				// To Update for Difficulty, Reload the language
				if (choice.equals("test")) {
					lang.sortWords();
				}			
			}

		} catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
			System.out.println("===CLILANG===");
			System.out.println("Welcome to CLILang! You didn't provide a file,");
			System.out.println("here are some preliminary questions to get you set up.");
			System.out.println("");

			System.out.println("What Language will you be working with? ");
			System.out.print("=> ");
			Scanner in = new Scanner(System.in);
			String langName = in.nextLine();
			System.out.println("");

			System.out.println("Awesome! I love " + langName + ". Add some words to your");
			System.out.println("language in the format 'word:definition'. Type 'STOP'");
			System.out.println("when you're ready to stop typing.");

			String wordPair = "";
			ArrayList<String[]> words = new ArrayList<String[]>();
			while (true) {
				wordPair = in.nextLine();

				if (wordPair.equals("STOP"))
					break;

				words.add(wordPair.split(":"));
			}

			Language lang = new Language(langName, words);

			try {
				PrintWriter out = new PrintWriter(langName + ".txt");

				out.println("language:" + lang.getLang());

				ArrayList<String[]> placeHolder = lang.getWords();

				for (String[] word : placeHolder) {
					out.println(word[0] + ":" + word[1] + ":0");
					out.flush();
				}

			} catch (Exception ex) {
				System.out.println(ex);
			}

			System.out.println("");
			System.out.println("Looks like we're ready to go! We just made a file");
			System.out.println("for you called '" + langName + ".txt' that has your language and");
			System.out.println("the words you're learning, along with a number describing their ");
			System.out.println("difficulty. Right now, they're all labeled with a zero, meaning");
			System.out.println("that they haven't been practiced. Type 'test' to try it out! Type 'help'" );
			System.out.println("to get additional info.");
			System.out.println("");

			String choice = "";
			while (!choice.equals("exit")) {
				System.out.println("What would you like to do?");
				choice = in.nextLine();	
				parseChoice(choice, lang, in);			
			}
			
		}
	}

	public static String[] parseLine(String line) {
		return line.split(":");
	}

	public static Language parseChoice(String choice, Language lang, Scanner scan) {
		if (choice.equals("exit")) {
			return lang;
		} else if (choice.equals("test")) {
			lang = test(lang, scan);
			System.out.println("");
		} else if (choice.equals("help")) {
			help();
			System.out.println("");
		} else {
			System.out.println("Sorry, I didn't catch that. Try typing 'help'");
			System.out.println("if you're stuck.");
		}

		return lang;
	}

	public static Language test(Language lang, Scanner scan) {

		for (String[] pair : lang.getWords()) {
			String answer = "";
			int tries = 0;

			while (!answer.equals(pair[1])) {
				if (tries == 5) {
					System.out.println("You just didn't get it");
					System.out.println("Answer: " + pair[1]);
					break;
				}

				System.out.print(pair[0] + ": ");
				answer = scan.nextLine();

				tries++;
			}

			// Changes the Difficulty of Word
			lang.setDifficulty(pair[0], tries);

			System.out.println(" ");
		}

		System.out.println("Congratulations! You got them all!");

		try {
			PrintWriter changes = new PrintWriter(lang.getLang() + ".txt");

			changes.println("language:" + lang.getLang());

			ArrayList<String[]> placeHolder = lang.getWords();
			LinkedList<String> sortedWords = new LinkedList<String>();

			for (String[] word : placeHolder) {
				String wordValue = word[0] + ":" + word[1] + ":" + lang.getDifficulty(word[0]);

				// If Statement to Determine how to handle wordValue
				if (sortedWords.size() == 0) {
					sortedWords.add(wordValue);
				} else if (lang.getDifficulty(word[0]) < lang.getDifficulty(sortedWords.peek().split(":")[0])) {
					sortedWords.add(wordValue);
				} else {
					sortedWords.push(wordValue);
				}
			}

			for (String word : sortedWords) {
				changes.println(word);
				changes.flush();
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return lang;
	}

	public static void help() {
		System.out.println("Here are the Following Commands you can type: ");
		System.out.println(" ");
		System.out.println("'help'");
		System.out.println("    Prints out a list of commands");
		System.out.println("'test'");
		System.out.println("    Tests you on the vocab provided in your");
		System.out.println("    .txt file.");
		System.out.println("'exit'");
		System.out.println("    Stops the program.");
	}
}