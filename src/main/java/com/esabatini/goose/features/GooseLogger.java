/**
 * 
 */
package com.esabatini.goose.features;

/**
 * @author es
 *
 */
public class GooseLogger {
	
	public static String PROMPT = "Goose:/input>";
	public static String lastGivenLine;

	public static void output(String s) {
	      System.out.println("Goose:/output>" + s + "\n");
	      lastGivenLine = s;
	}
}
