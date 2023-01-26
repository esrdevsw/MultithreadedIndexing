/**
 * Runner.java
 */
package ie.atu.sw;

/**
 * The application is run using the Runner Class. It accomplishes this by
 * starting the APIMenu Class. That class is used to show a menu and call all
 * the classes required to run and configure the API.
 * 
 * @fileName Runner.java
 * @author Edivagne Ribeiro / ID G00411275 <br>
 *         </br>
 *         See: {@link APIMenu}
 */
public class Runner {

	/**
	 * Main argument carries out all the methods in the API.
	 * 
	 * The only function of creating a new APIMenu instance to display the options
	 * menu with the start method.
	 * 
	 * @param args --<br>
	 *             </br>
	 *
	 *             See: {@link APIMenu}
	 */
	public static void main(String[] args) {
		APIMenu m = new APIMenu();
		m.start();
	}
}
