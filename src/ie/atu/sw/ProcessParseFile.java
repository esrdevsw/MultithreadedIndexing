/**
 * ProcessParseFile.java
 */
package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The ProcessParseFile class uses virtual threads to process files entered by
 * the user. For each type of file, a method is defined that cleans the file,
 * excluding unwanted characters and makes the proper separation of words. After
 * processing the file, the information is stored to build the report in the
 * BuildWordIndex class.
 * 
 * 
 * @fileName ProcessParseFile.java
 * @author Edivagne Ribeiro / ID G00411275 <br>
 *         </br>
 *         See: {@link BuildWordIndex}
 * 
 */
public class ProcessParseFile {

	private static Set<String> stopWords = new ConcurrentSkipListSet<>();
	private static Map<String, List<String>> dictionary = new ConcurrentSkipListMap<>();
	private static Map<String, Set<Integer>> wordsPages = new ConcurrentSkipListMap<>();
	// private static Map<String, List<Integer>> wordsPages = new
	// ConcurrentSkipListMap<>();
	private static Map<String, Integer> wordFrequency = new ConcurrentSkipListMap<>();

	// use AtomicInteger as a counter to ensure the best functioning of virtua
	// threads.
	private final AtomicInteger counter = new AtomicInteger(1);

	/**
	 * get StopWords collection
	 * 
	 * @return the stopWords
	 */
	public static Set<String> getStopWords() {
		return stopWords;
	}

	/**
	 * get word Frequency collection
	 * 
	 * @return the wordFrequency
	 */
	public static Map<String, Integer> getWordFrequency() {
		return wordFrequency;
	}

	/**
	 * get dictionary map
	 * 
	 * @return the dictionary
	 */
	public static Map<String, List<String>> getDictionary() {
		return dictionary;
	}

	/**
	 * get WordsPages map
	 * 
	 * @return the wordsPages
	 */
	public static Map<String, Set<Integer>> getWordsPages() {
		return wordsPages;
	}

	/**
	 * ParseFile method, first clears the previously stored data so that there is no
	 * data overlap. And then it calls a parseVThread method where each file will be
	 * processed according to the input type.
	 * 
	 * @param textFile - string file to process
	 * @param choice   - Defines the type of processing according to the file
	 *                 introduced.
	 * @throws Exception - <br>
	 *                   </br>
	 *                   See: {@link FileTextImpl}
	 */
	public void parseFile(String textFile, int choice) throws Exception {

		SetMapRemoveAll(choice);

		parseVThread(textFile, choice);

		System.out.println("");
		System.out.println("************************************************************");
		System.out.print("*    words book size: ");
		System.out.println(wordsPages.size());
		System.out.print("*    dictionary size: ");
		System.out.println(dictionary.size());
		System.out.print("*    stop Words size: ");
		System.out.println(stopWords.size());
		System.out.println("************************************************************");
	}

	/*
	 * Prepare and run virtual threads for each file processing.
	 * 
	 * @param textFile
	 * 
	 * @param choice
	 * 
	 * @throws Exception
	 */
	private void parseVThread(String textFile, int choice) throws Exception {

		try (@SuppressWarnings("preview")
		var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
			Files.lines(Paths.get(textFile)).forEach(text -> executorService.execute(() -> {
				try {
					process(text, choice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}));
		}
	}

	/*
	 * Clear previously stored data
	 * 
	 * @param choice
	 */
	private synchronized void SetMapRemoveAll(int choice) {
		if (choice == 1) {
			wordsPages.clear();
		} else if (choice == 2) {
			dictionary.clear();
		} else if (choice == 3) {
			stopWords.clear();
		}
	}

	/*
	 * Calls each method for processing the files
	 * 
	 * @param txtFile
	 * 
	 * @param choice
	 * 
	 * @throws Exception
	 */
	private synchronized void process(String txtFile, int choice) throws Exception {
		if (choice == 1) {
			processText(txtFile);
		} else if (choice == 2) {
			processDictionary(txtFile);
		} else if (choice == 3) {
			processStopWords(txtFile);
		}
	}

	/*
	 * process StopWords split the string into string of array with separator as
	 * space or multiple spaces The Big-O is O(M+N) where M = array size (the
	 * ArrayList) and N = collection size
	 * 
	 * @param txtFile
	 */
	private synchronized void processStopWords(String txtFile) {
		stopWords.addAll(Arrays.asList(txtFile.split("\\s+")));
	}

	/*
	 * process Dictionary split the string into string of array with separator as
	 * comma Separates the first value for the key(word). and then Split the string
	 * to get a list of definitions
	 * 
	 * @param txtFile
	 */
	private synchronized void processDictionary(String txtFile) {
		List<String> w = Arrays.asList(txtFile.split(","));
		String word = w.get(0).toLowerCase(); // O(1)

		List<String> definition = Arrays.asList(txtFile.split("[.:];"));
		definition.remove(""); // O(n)

		dictionary.put(word, definition);
	}

	/*
	 * process Text Processes the text, changes everything to lowercase, divides the
	 * text into words and increments the lines of with the atomic variable call the
	 * method addWord
	 * 
	 * @param line
	 * 
	 * @throws Exception
	 */
	private synchronized void processText(String line) throws Exception {
		String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		counter.getAndIncrement();
		for (String word : words) { // O(n)
			addWord(word);
		}
	}

	/*
	 * addWord checks if the word already exists, and counts the frequency of the
	 * word in the text.
	 * 
	 * @param word
	 * 
	 * @throws Exception
	 */
	private synchronized void addWord(String word) throws Exception {
		Set<Integer> pages;
		AtomicInteger counterFreq = new AtomicInteger(1);
		if (wordsPages.containsKey(word)) { // O(1)
			pages = wordsPages.get(word); // O(1)
			counterFreq.addAndGet(wordFrequency.get(word));
		} else {
			pages = new HashSet<Integer>();
		}
		if (!word.equals("")) {
			pages.add(((counter.intValue()) / 40) + 1); // O(1)
			wordsPages.put(word, pages); // O(1)
			wordFrequency.put(word, counterFreq.intValue());
		}
	}
}
