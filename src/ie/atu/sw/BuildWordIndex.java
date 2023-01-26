/**
 * BuildWordIndex.java
 */
package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Set;

/**
 * BuildWordIndex makes the correlation between the file to be analyzed with the
 * StopWords and searches the definitions of the words in the dictionary.
 * 
 * Make a list of the five most frequent words in the text and the least
 * frequent ones.
 * 
 * Finally, prepare a report with the set of information from the entire text.
 * 
 * @fileName BuildWordIndex.java
 * @author Edivagne Ribeiro / ID G00411275<br>
 *         </br>
 * 
 *         See: {@link WordSortMap} {@link ProcessParseFile}
 */

public class BuildWordIndex {

	private static Map<String, Set<Integer>> idx = new ConcurrentSkipListMap<>(ProcessParseFile.getWordsPages());
	private FileWriter report;
	private BufferedWriter bw;
	private String nameOutput = FileTextOut.getFileNameOutput();
	private int wordsTotal = (ProcessParseFile.getWordsPages()).size();
	private int wordsAfterStop;
	private static LinkedHashMap<String, Integer> freqAscending = new LinkedHashMap<String, Integer>();
	private static LinkedHashMap<String, Integer> freqDescending = new LinkedHashMap<String, Integer>();

	/**
	 * Processes the file sequentially Call the methods: removeStopWords,
	 * filterDictionaryWords, sortFreq, reportWriting and printMAP.
	 * 
	 * @throws IOException - Writing the report with the indexing information<br>
	 *                     </br>
	 *                     See: {@link ProcessParseFile}
	 */
	public void BuildIndex() throws IOException {

		// System.out.println(ProcessParseFile.getWordsPages());
		// System.out.println(ProcessParseFile.getWordFrequency());

		removeStopWords();

		filterDictionaryWords();

		sortFreq();

		reportWriting();

		printMAP(idx);
	}

	/*
	 * makes a copy of the word map with frequency and calls the WordSortMap class
	 */
	private void sortFreq() {
		LinkedHashMap<String, Integer> freqMapAscending = new WordSortMap()
				.ascendingOrder(ProcessParseFile.getWordFrequency());
		LinkedHashMap<String, Integer> freqMapDescending = new WordSortMap()
				.descendingOrder(ProcessParseFile.getWordFrequency());

		System.out.println("-> The top 5 most frequent words");
		printSortFreq(freqMapDescending, freqDescending);

		System.out.println("-> The top 5 most infrequent words");
		printSortFreq(freqMapAscending, freqAscending);
		System.out.println("************************************************************");
	}

	/*
	 * shows the 5 most frequent / infrequent words
	 * 
	 * @param mapIn
	 * 
	 * @param mapOut
	 */
	private void printSortFreq(LinkedHashMap<String, Integer> mapIn, LinkedHashMap<String, Integer> mapOut) {
		int count = 1;
		for (Map.Entry<String, Integer> entry : mapIn.entrySet()) {
			String key = entry.getKey();
			Integer val = entry.getValue();
			if (idx.containsKey(key)) {
				count++;
				System.out.println("\t" + val + " | " + key);
				mapOut.put(key, val);
				if (count > 5)
					break;
			}
		}
	}

	/*
	 * remove StopWords the Big-O for removeAll is O(n) and idx.size O(1)
	 */

	private void removeStopWords() {

		System.out.println("************************************************************");
		System.out.println("The File -> " + FileTextImpl.getBookName());
		System.out.print("The total number of unique words: ");
		System.out.println(idx.size());
		idx.keySet().removeAll(ProcessParseFile.getStopWords());
		System.out.print("Unique words after stopwords: ");
		wordsAfterStop = idx.size();
		System.out.println(wordsAfterStop);
	}

	/*
	 * filter Dictionary Words Filters the words only available in the dictionary
	 * the Big-O for retainAll is O(n)
	 * 
	 */
	private void filterDictionaryWords() {
		(idx.keySet()).retainAll((ProcessParseFile.getDictionary()).keySet());
		System.out.print("Words with definition: ");
		System.out.println(idx.size());
	}

	/*
	 * Shows information for the 5 most frequent words in the text, as well as page
	 * numbers and their definitions.
	 * 
	 * @param map
	 */
	private void printMAP(Map<String, Set<Integer>> map) {
		System.out.println("************************************************************");
		System.out.println("============================================================");
		System.out.println("                 Words and Definitions                      ");
		System.out.println("                        Top 5                               ");
		System.out.println("============================================================");
		for (Entry<String, Set<Integer>> entry : map.entrySet()) {
			String k = String.valueOf(entry.getKey());
			if (freqDescending.containsKey(k)) {
				String f = String.valueOf(ProcessParseFile.getWordFrequency().get(k.toLowerCase()));
				Set<Integer> v = entry.getValue();
				System.out.println("____________________________________________________________");
				System.out.println("-> " + k.toUpperCase());
				System.out.println("\t Frequency: " + f);
				System.out.println("\t Pages: " + v);
				List<String> val = ProcessParseFile.getDictionary().get(k);
				System.out.println("\t Definitions: ");
				for (String def : val) {
					System.out.println("\t\t" + def + ".");
				}
			}
		}
		System.out.println("");
		System.out.println("************************************************************");
		System.out.println("Total output on report: " + map.size());

	}

	/*
	 * Writing the report with the indexing information
	 * 
	 * @throws IOException
	 */
	private void reportWriting() throws IOException {
		try {
			report = new FileWriter(nameOutput);
			bw = new BufferedWriter(report);
			bw.write("************************************************************");
			bw.newLine();
			bw.write("*       ATU - Dept. Computer Science & Applied Physics     *");
			bw.newLine();
			bw.write("*                                                          *");
			bw.newLine();
			bw.write("*              Virtual Threaded Text Indexer               *");
			bw.newLine();
			bw.write("*                                                          *");
			bw.newLine();
			bw.write("************************************************************");
			bw.newLine();
			bw.newLine();
			bw.write("The File -> " + FileTextImpl.getBookName());
			bw.newLine();
			bw.newLine();
			bw.write("============================================================");
			bw.newLine();
			bw.write("                     Frequent Words                         ");
			bw.newLine();
			bw.write("============================================================");
			bw.newLine();
			bw.newLine();
			bw.write("-> The total number of unique words: ");
			bw.write(Integer.toString(wordsTotal));
			bw.newLine();
			bw.newLine();
			bw.write("-> Unique words after stopwords: ");
			bw.write(Integer.toString(wordsAfterStop));
			bw.newLine();
			bw.newLine();
			bw.write("-> Total output on report: " + Integer.toString(idx.size()));
			bw.newLine();
			bw.newLine();
			bw.write("************************************************************");
			bw.newLine();
			bw.newLine();
			bw.write("-> The top 5 most frequent words");
			bw.newLine();
			bw.newLine();
			for (Map.Entry<String, Integer> entry : freqDescending.entrySet()) {
				String key = entry.getKey();
				Integer val = entry.getValue();
				bw.write("\t" + val + " | " + key);
				bw.newLine();
			}
			bw.newLine();
			bw.write("************************************************************");
			bw.newLine();
			bw.newLine();
			bw.write("-> The top 5 most infrequent words");
			bw.newLine();
			bw.newLine();
			for (Map.Entry<String, Integer> entry : freqAscending.entrySet()) {
				String key = entry.getKey();
				Integer val = entry.getValue();
				bw.write("\t" + val + " | " + key);
				bw.newLine();
			}
			bw.newLine();
			bw.newLine();
			bw.write("============================================================");
			bw.newLine();
			bw.write("                 Words and Definitions                      ");
			bw.newLine();
			bw.write("============================================================");
			bw.newLine();
			for (Entry<String, Set<Integer>> entry : idx.entrySet()) {
				String k = String.valueOf(entry.getKey());
				String f = String.valueOf(ProcessParseFile.getWordFrequency().get(k.toLowerCase()));
				Set<Integer> v = entry.getValue();
				bw.newLine();
				bw.write("____________________________________________________________");
				bw.newLine();
				bw.write("-> " + k.toUpperCase());
				bw.newLine();
				bw.write("\t Frequency: " + f);
				bw.newLine();
				bw.write("\t Pages: " + v);
				List<String> val = ProcessParseFile.getDictionary().get(k);
				bw.newLine();
				bw.write("\t Definitions: ");
				for (String def : val) {
					bw.newLine();
					bw.write("\t\t" + def + ".");
				}
			}
			bw.newLine();
			bw.newLine();
			bw.newLine();
			bw.write("************************************************************");
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
