/**
 * APIMenu.java
 */
package ie.atu.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The APIMenu class creates a menu that provides the user with six different
 * options:
 * <ol>
 * <li>Specify Text File - gives the user the option of inserting the text file
 * which is then sent to the ProcessParseFile Class.</li>
 * <li>Configure Dictionary - gives the user the option of inserting the
 * Dictionary or use the default value.</li>
 * <li>Configure Common Words - gives the user the option of inserting a file
 * with a StopWords List or use the default value</li>
 * <li>Specify Output File - give the user the option to specify the file to
 * output or use the default value "index.txt"</li>
 * <li>Execute - This option calls the BuildWordIndex class to process the files
 * and generate the report.</li>
 * <li>Quit - End the program.</li>
 * </ol>
 * 
 * @fileName APIMenu.java
 * 
 * @author Edivagne Ribeiro / ID G00411275
 * <br></br>
 * See: 
 * {@link FileTextImpl}
 * {@link FileTextOut}
 * {@link BuildWordIndex}
 * 
 */
public class APIMenu {
	private final Scanner s;
	private boolean keepRunning = true;
	private FileTextImpl fti = new FileTextImpl();

	/**
	 * Creates a new instance of a scanner, s. Waits on Input from User
	 */
	public APIMenu() {
		s = new Scanner(System.in);
	}

	/**
	 * Show options to the user
	 */
	public void start() {
		do {
			showOptions();
			try {
				int choice = Integer.parseInt(s.next()); // Waits on Input from User

				if (choice == 1) {// (1) Specify Text File
					System.out.println("");
					System.out.print(">> Specify Text File (test.txt): ");
					//String txtDefault = "./text-files/BibleGod.txt";
					// String txtDefault = "./shakespeare.txt";
					String txtDefault = "./test.txt";
					getTextFile(txtDefault, choice);
				} else if (choice == 2) {// (2) Configure Dictionary
					System.out.println("");
					System.out.print(">> Configure Dictionary File (dictionary.csv): ");
					String txtDefault = "./dictionary.csv";
					getTextFile(txtDefault, choice);
				} else if (choice == 3) {// (3) Configure Common Words
					System.out.println("");
					System.out.print(">> Configure Common Words File (google-1000.txt): ");
					String txtDefault = "./google-1000.txt";
					getTextFile(txtDefault, choice);
				} else if (choice == 4) {// (4) Specify Output File
					System.out.println("");
					System.out.println(">> Specify Output File Name (index.txt): ");
					String outDefault = "index.txt";
					getTextFile(outDefault, choice);
				} else if (choice == 5) {// (5) Execute
					buildIndex();
				} else if (choice == 6) {// (6) Quit
					System.out.println("[INFO] Shutting down...please wait...");
					keepRunning = false;
				} else {
					System.out.println("");
					System.out.println("[ERROR] Invalid input.");
				}
			} catch (NumberFormatException nfe) {
				System.out.println("");
				System.out.println("[ERROR] Invalid input.");
				System.out.println(nfe.getMessage());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} while (keepRunning);
	}

	/*
	 * The getTextFile method calls classes for each text file entered by the user.
	 * 
	 * @param txtUserInput
	 * 
	 * @param choice
	 * 
	 * @throws Exception
	 */
	private void getTextFile(String txtUserInput, int choice) throws Exception {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String userInput = input.readLine();
		// if the user entered non-whitespace characters then
		// actually parse their input
		if (!"".equals(userInput.trim())) {
			txtUserInput = String.valueOf(userInput);
		}
		if (choice != 4) {
			System.out.println("[INFO] File = " + txtUserInput);
			fti.setFile(txtUserInput, choice);
		} else {
			System.out.println("[INFO] File = " + txtUserInput);
			new FileTextOut().setFile(txtUserInput, choice);
		}
	}

	/*
	 * calls the BuildWordIndex class after checking if all files are ok.
	 * 
	 * @throws IOException
	 */

	private void buildIndex() throws IOException {
		if (FileTextImpl.getBookName() == null || ProcessParseFile.getWordsPages().size() == 0) {
			System.out.println("[ERROR] >> Please Specify Text File... ");
		} else if (FileTextImpl.getDictionaryName() == null || ProcessParseFile.getDictionary().size() == 0) {
			System.out.println("[ERROR] >> Please Configure Dictionary... ");
		} else if (FileTextImpl.getStopWordsName() == null || ProcessParseFile.getStopWords().size() == 0) {
			System.out.println("[ERROR] >> Please Configure Common Words (Stop Words)... ");
		} else {
			new BuildWordIndex().BuildIndex();
			;
		}
	}

	/*
	 * display the options menu
	 */
	private void showOptions() {
		System.out.println("");
		System.out.println("");
		System.out.println("************************************************************");
		System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
		System.out.println("*                                                          *");
		System.out.println("*              Virtual Threaded Text Indexer               *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Text File");
		System.out.println("(2) Configure Dictionary");
		System.out.println("(3) Configure Common Words");
		System.out.println("(4) Specify Output File");
		System.out.println("(5) Execute");
		System.out.println("(6) Quit");
		System.out.println("");
		System.out.print("Select Option [1-6]> ");

	}

}
