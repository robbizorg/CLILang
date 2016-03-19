import java.io.File;
import java.io.FileNotFoundException;
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
			//Scanner in = new Scanner(System.in);
			System.out.println("Please Provide a File");
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