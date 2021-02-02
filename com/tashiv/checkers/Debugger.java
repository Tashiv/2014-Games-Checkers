package com.tashiv.checkers;

//// Class
/**
 * This class is a simple central reporting tool for outputting  
 * important details to the command line for better debugging.
 * It aims to do so in a non-GUI intrusive means which separates
 * code related information from gamer related information.
 * <p>
 *	Class is not instantiated, it is accessed dirrectly via the class name.
 * <p>
 * It should be noted that it will be disabled in the
 * when this game is not being tested.
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class Debugger
{
	//// Instance Constants
	/**
 	* A flag for indicating the game mode, true for debugging mode, false for regular game mode
 	*/
	public static final boolean CANREPORT = false;

	//// Instance Methods
	/**
 	* A method for requesting the debugger to write
 	* debugging information to the command line,
 	* which will only be written if the Class
 	* is in debugging mode.
 	*
 	* @param reportText The text to be outputted.
 	*/
	public static void report(String reportText)
	{
		if (CANREPORT == true) // CASE: Debugger-Mode [ON]
			System.out.println(reportText);
	}
}