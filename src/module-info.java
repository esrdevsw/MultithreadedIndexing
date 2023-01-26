/**
 * module-info.java
 */
/**
 * jdk.incubator.concurrent
 *  
 *  @version 
 *  
 *  java 19
 *  
 *  <p>Set VM to:</p> 
 *  <p>--add-modules jdk.incubator.concurrent</p>
 *  <p>--enable-preview</p>
 * 
 * <p><b>Multithreaded indexing API - Java 19+</b></p>
 * <p>For the API to run properly the VM must be properly configured.</p>
* 	<p>Running from the command line should activate the preview function</p>
 * <p><b>java --enable-preview -cp ./indexer.jar ie.atu.sw.Runner</b></p>
 * 
 * @author Edivagner Ribeiro
 *
 */
module MultithreadedIndexing {
	exports ie.atu.sw;
	requires jdk.incubator.concurrent;
}