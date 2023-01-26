/**
 * FileTextImpl.java
 */
package ie.atu.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;

/**
 * 
 * This FileTextImpl class stores the name of the files introduced by the user
 * that will be used to process the text. This class implements FileText
 * interface
 * 
 * 
 * 
 * @fileName FileTextImpl.java
 * @implSpec FileText.java
 * @author Edivagne Ribeiro / ID G00411275 <br>
 *         </br>
 *         See: {@link ProcessParseFile}
 *
 */

public class FileTextImpl implements FileText {

	private static String bookName;
	private static String dictionaryName;
	private static String stopWordsName;
	private String inputPathFile;

	/**
	 * get the file name to process
	 * 
	 * @return the bookName
	 */
	public static String getBookName() {
		return bookName;
	}

	/**
	 * get the dictionary file name
	 * 
	 * @return the dictionaryName
	 */
	public static String getDictionaryName() {
		return dictionaryName;
	}

	/**
	 * get the stopwords file name
	 * 
	 * @return the stopWordsName
	 */
	public static String getStopWordsName() {
		return stopWordsName;
	}

	/**
	 * Set file to process and parse In this method we call ProcessParseFile class,
	 * where there is a need for functions in preview mode. Returning an error if
	 * the API is not running in preview mode
	 * 
	 * @param dirPath - text path input by user
	 * @throws Exception - Invalid File or not --enable-preview<br>
	 *                   </br>
	 * 
	 *                   See: {@link ProcessParseFile}
	 */

	public void setFile(String dirPath, int choice) throws Exception {
		try {
			File file = new File(dirPath);
			setPathFile(file.getPath());

			if (choice == 1) {
				bookName = file.getName();
			} else if (choice == 2) {
				dictionaryName = file.getName();
			} else if (choice == 3) {
				stopWordsName = file.getName();
			}

			new ProcessParseFile().parseFile(dirPath, choice);

		} catch (NoSuchFileException e) {
			System.out.println("");
			System.out.println("[ERROR] Invalid input.");
			System.out.println(">> Invalid File: " + e.getMessage());
			System.out.println("");
			System.out.println("[INFO] Please enter a valid file...");
			System.out.println("");
		} catch (UnsupportedOperationException e) {
			System.out.println("");
			System.out.println("[ERROR] Run.");
			System.out.println(">> " + e.getMessage());
			System.out.println("");
			System.out.println("[INFO] try Run with");
			System.out.println(" java --enable-preview -cp ./indexer.jar ie.atu.sw.Runner");
			System.out.println("");
		}
	}

	/**
	 * get Path File
	 * 
	 * @return the inputPathFile
	 */
	public String getPathFile() {
		return getPathFile();
	}

	/**
	 * set Path File
	 * 
	 * @param inputPathFile - the inputPathFile to set
	 */
	public void setPathFile(String inputPathFile) {
		this.inputPathFile = inputPathFile;
	}
}
