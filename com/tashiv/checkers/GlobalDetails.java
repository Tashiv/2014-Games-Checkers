package com.tashiv.checkers;

//// Class
/**
 * A class for communication between the two GUI sub-interfaces of the game
 * namely the Core Window and the Side Window which both render
 * specific images depending on the same Game Mode and Game Data,
 * but since they both fall under the main interface class, this object
 * which is provided to the GUI sub-interfaces as a shallow copy of an original
 * (held by the main GUI interface) allows for the to render in synchronization
 * with one another.
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class GlobalDetails
{
	/// Instance Variables
	private int fPlayerTurn;		 // Turn Tracker, [1] = Player1 Turn, [2] = Player2 Turn
	private String fGameMode; 		 // Game Mode Tracker, ie inGame Mode, inMenu Mode,...	
	private boolean fMustTake;		 // Indicates if there is a manditory take available according to rules of checkers 
	private int fP1BeadsCnt;		 // Amount of beads player 1 has left 
	private int fP2BeadsCnt;		 // Amount of beads player 2 has left 

	//// Instance Methods
	/**
	* A constructor for initialising the instance variables
	* to non-Game mode values.(eg Player turn is set to 0,
	* ie: it is no players turn) 
	*/
	public GlobalDetails()
	{
		fPlayerTurn = 0;
		fMustTake = false;
	}

	/**
	* A mutator that updates the amount of beads player 1 has left
	* which is stored in this class.
	*
	* @param iBeadCount The new amount of beads remaining for Player 1
	*/
	public void setP1BeadsCnt(int iBeadCount)
	{
		fP1BeadsCnt = iBeadCount;
	}

	/**
	* A mutator that updates the amount of beads player 2 has left
	*
	* @param iBeadCount The new amount of beads remaining for Player 2
	*/
	public void setP2BeadsCnt(int iBeadCount)
	{
		fP2BeadsCnt = iBeadCount;
	}

	/**
	* A accessor that indicates the amount of beads player 1 has left.
	*
	* @return returns an integer representing the amount of beads Player 1 has.
	*/
	public int getP1BeadsCnt()
	{
		return fP1BeadsCnt;
	}

	/**
	* A accessor that indicates the amount of beads player 1 has left.
	*
	* @return returns an integer representing the amount of beads Player 1 has.
	*/
	public int getP2BeadsCnt()
	{
		return fP2BeadsCnt;
	}

	/**
	* A mutator that updates the Manditory Take Indicator
	* stored in the class.
	*
	* @param mustTake A indicator of if there is a Manditory Take Available in current game.
	*/
	public void setMustTake(boolean mustTake)
	{
		fMustTake = mustTake;
	}

	/**
	* A accessor that indicates if the is a Manditory Take available
	* stored in the class.
	*
	* @return returns a boolean indicating if there is a take available.
	*/public boolean mustTake()
	{
		return fMustTake;
	}

	/**
	* A mutator that updates the stored value indicating
	* the player's turn.
	*
	* @param playerID In integer representing who's turn it is. (eg [1] => Player 1's turn) 
	*/
	public void setPlayerTurn(int playerID)
	{
		fPlayerTurn = playerID;
	}

	/**
	* A accessor that returns which player's turn it is.
	*
	* @return an integer that represent who's turn it is. (eg [1] => Player 1's turn)
	*/
	public int getPlayerTurn()
	{
		return fPlayerTurn;
	}

	/**
	* A mutator for updating the stored game mode
	*
	* @param theMode A string for the game mode. (See Game Class Documentation for examples)
	*/ 
	public void setMode(String theMode)
	{
		fGameMode = theMode;
	}

	/**
	* An accessor for checking whick player's turn it is.
	*
	* @return A String that represents the current game mode (eg: Strings that mean "inGame" or "inMenu", see
	* Game Class documentation for all modes)
	*/
	public String getMode()
	{
		return fGameMode;
	}
}
