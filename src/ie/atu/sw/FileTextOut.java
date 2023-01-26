/**
 * FileTextOut.java
 */
package ie.atu.sw;

/**
 * 
 * Define the output file or use the default value "index.txt" This class
 * implements FileText interface
 * 
 * 
 * @fileName FileTextOut.java
 * @author Edivagne Ribeiro / ID G00411275 <br>
 *         </br>
 *         See: {@link BuildWordIndex}
 */
public class FileTextOut implements FileText {

	private static String fileNameOutput = "index.txt";

	/**
	 * set file name to output
	 * 
	 * @param dirPath path input by user to output file
	 */
	@Override
	public void setFile(String dirPath, int choice) throws Exception {
		setPathFile(dirPath);
	}

	/**
	 * get file name to output
	 * 
	 * @return the fileNameOutput
	 */
	public String getPathFile() {
		return fileNameOutput;
	}

	/**
	 * get file name to output
	 * 
	 * @return the fileNameOutput
	 */
	public static String getFileNameOutput() {
		return fileNameOutput;
	}

	/**
	 * set file name to output
	 * 
	 * @param fileNameOutput
	 */
	public static void setFileNameOutput(String fileNameOutput) {
		FileTextOut.fileNameOutput = fileNameOutput;
	}

	/**
	 * set file name
	 * 
	 * @param pathFile
	 */
	@Override
	public void setPathFile(String pathFile) {
		fileNameOutput = pathFile;
		System.out.println("Output file: " + getPathFile());
	}

}
