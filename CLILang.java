import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class CLILang {
	public static final void main(String[] args) {
		try {

			ArrayList<String[]> words = new ArrayList<String[]>();

			Scanner in = new Scanner(new File(args[0]));
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
				parseChoice(choice, lang, input);			
			}

		} catch (Exception e) {
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
				PrintWriter out = new PrintWriter("dict.txt");
				out.println("language:" + lang.getLang());
				ArrayList<String[]> placeHolder = lang.getWords();
				for (String[] word : placeHolder) {
					out.println(word[0] + ":" + word[1]);
					out.flush();
				}

			} catch (Exception ex) {
				System.out.println("File Not Found");
			}


			System.out.println("Looks like we're ready to go! We just made a file");
			System.out.println("for you called 'dict.txt' that has your language and");
			System.out.println("the words you're learning!");

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

	public static void parseChoice(String choice, Language lang, Scanner scan) {
		if (choice.equals("exit")) {
			return;
		} else if (choice.equals("test")) {
			test(lang, scan);
			System.out.println("");
		} else if (choice.equals("help")) {
			help();
			System.out.println("");
		} else {
			System.out.println("Sorry, I didn't catch that. Try typing 'help'");
			System.out.println("if you're stuck.");
		}
	}

	public static void test(Language lang, Scanner scan) {

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

			System.out.println(" ");
		}

		System.out.println("Congratulations! You got them all!");
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