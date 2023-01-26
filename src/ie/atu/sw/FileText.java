/**
 * FileText.java
 */
package ie.atu.sw;

/**
 * 
 * FileText interface defines the method signatures for the methods in the
 * FileTextImpl Class and FileTextOut Class.
 * 
 * 
 * @fileName FileText.java
 * @author Edivagne Ribeiro / ID G00411275
 *
 * 
 *         <br>
 *         </br>
 *         See: {@link FileTextImpl} {@link FileTextOut}
 */
public interface FileText {
	/**
	 * set the files to process
	 * 
	 * @param dirPath - file
	 * @param choice  - Stopwords, dictionary or text file
	 * @throws Exception - if don't have file
	 */
	void setFile(String dirPath, int choice) throws Exception;

	/**
	 * get Path File to process
	 * 
	 * @return - File
	 */
	String getPathFile();

	/**
	 * set Path File
	 * 
	 * @param inputPathFile - Path File
	 */
	void setPathFile(String inputPathFile);

}
