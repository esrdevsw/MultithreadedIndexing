/**
 * WordComparator.java
 */
package ie.atu.sw;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * the WordSortMap class provides two methods to sort words by value by the
 * frequency in which they appear in the text.
 * 
 * 
 * @fileName WordComparator.java
 * @author Edivagne Ribeiro / ID G00411275 <br>
 *         </br>
 *         See: {@link BuildWordIndex}
 *
 */
public class WordSortMap {

	/**
	 * sort ascending Order
	 * 
	 * @param unSorted - unsorted map
	 * @return sortedMap - sorted map <br>
	 *         </br>
	 * 
	 *         See: {@link BuildWordIndex}
	 */
	public LinkedHashMap<String, Integer> ascendingOrder(Map<String, Integer> unSorted) {

		// LinkedHashMap preserve the ordering of elements in which they are inserted
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

		unSorted.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

		return sortedMap;
	}

	/**
	 * sort descendin Order
	 * 
	 * @param unSorted - unsorted map
	 * @return reverseSortedMap - reverse ordering sorted map <br>
	 *         </br>
	 *         See: {@link BuildWordIndex}
	 */
	public LinkedHashMap<String, Integer> descendingOrder(Map<String, Integer> unSorted) {

		// LinkedHashMap preserve the ordering of elements in which they are inserted
		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();

		// Use Comparator.reverseOrder() for reverse ordering
		unSorted.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

		return reverseSortedMap;
	}
}
