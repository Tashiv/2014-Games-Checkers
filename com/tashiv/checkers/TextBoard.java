package com.tashiv.checkers;

// Imports
import java.util.Random;

/**
 * A class for simulating the checkers game which is the central part of this checkers GUI game.
 * This class is incharge of tracking pieces and their information, ensuring moves are valid
 * and checking the overall status of the game. Information used to render an output to the screen.
 * <p>
 * The Gamestatus may be:
 * <1> "WIN1" - Player 1 (blue) Wins; 
 * <2> "WIN2" - Player 2 (red) wins; 
 * <3> "MUSTTAKE" - There is a manditory take (a jump) available; 
 * <4> "BUSY" - There are only normal moves available;
 * <5> "DRAW" - This has been drepreciated; 
 * <p>
 * <p> Additionaly there are also BoardModes which are used to track the nature of what the user clicks on
 * and this can possible be:
 * <1> "SELECT" - the player's click will "select" a bead.
 * <2> "MOVE" - the player's click will select a position to "move" a bead
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
 
public class TextBoard
{
	//// Instance Constante
	private static final int AIMOVEDELAY = 50;				// Movement Delay Threshhold of AI, So that a user can see the indicators 
	//// instance variables
	private String[][] fBoard = new String[8][8];			// The game board - used to simulate game
	private Bead[] fBeads = new Bead[24];					// The game beads - used to keep track of bead's information
	private String fMode;									// The Simulation Mode:
	private Random ranGen = new Random();					// random number generator
	private int fCurrentBead = 0; 							// Tracks the current bead selected
	private int fPlayer; 									// the player's turn it is, 1 vs 2
	private GlobalDetails fGlobalDetails;					// sub-interface communication channel
	private int fPlayer1BeadsCnt;							// Number of beads P1 has left
	private int fPlayer2BeadsCnt;							// Number of beads P2 has left
	private String fGameStatus;								// Current Game Status
	private boolean fControlsEnabled;						// Can the user click - used in AI game
	private int iRecurssiveTaker = -1;						// Tracks if a bead {by index} must take again
	// Instance variables for AI
	private int fAIMoveTime;								// Used to track delay in move time					
	private Location fAIMoveLocation;						// Where the AI chooses to move
	private boolean fAIWaiting;								// Is the AI waiting to make a chosen move
	private String fAITakeAnimationID;						// The take animation ID of the AIs movement
	
	//// instance methods
	/**
	 * A constructor for the TextBoard tracking class which initializes
	 * the variables so that he class represents a new game.
	 */
	public TextBoard(GlobalDetails objDetails)
	{
		// initialising variables
		Debugger.report("Restarting Board");
		fControlsEnabled = true;
		fGameStatus = "BUSY";
		fGlobalDetails = objDetails;
		// Randomizes starting player - Done to improve on number of possible strategies 
		// of Players as the can either play 1st or 2nd. And is Red really Darker then Blue?
		fPlayer = ranGen.nextInt(2) + 1;
		Debugger.report("First Player: "+fPlayer);
		fGlobalDetails.setPlayerTurn(fPlayer);
		// Continues to initialize game
		fMode = "SELECT";
		int iCntBeads = 0;
		int randomNr;
		// initializes board
		// Board Symbols: "X" means illegal tile, 
		// while all other are legal, thus are set to ""
		boolean bExclude = false;									// Ensures correct board tile alternation 					
		for (int y = 0; y < 8; y++)
		{
			bExclude = !(bExclude);
			for (int x = 0; x < 8; x++)
			{
				if (bExclude == true)
				{
					fBoard[y][x] = "X";
					bExclude = false;
				}
				else
				{
					fBoard[y][x] = "";
					bExclude = true;
				}
			}
		}
		// player 2 beads setup (top of the board)
		// Board Symbols: "0".."11" represent player 2 beads
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				if (fBoard[y][x].equals("X") == false)
				{
					// Adds P2 beads
					fBoard[y][x] = Integer.toString(iCntBeads);
					randomNr = ranGen.nextInt(CoreImageHive.BEADFRAMES);
					fBeads[iCntBeads] = new Bead((byte) 2, x, y, randomNr);
					iCntBeads++;
				}
			}
		}
		// player 1 beads setup (bottom of board)
		for (int y = 5; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				if (fBoard[y][x].equals("X") == false)
				{
					// Adds P1 Beads
					fBoard[y][x] = Integer.toString(iCntBeads);
					randomNr = ranGen.nextInt(CoreImageHive.BEADFRAMES);
					fBeads[iCntBeads] = new Bead((byte) 1, x, y, randomNr);
					iCntBeads++;
				}
			}
		}
		// Update Player Bead Counters
		fPlayer1BeadsCnt = 12;
		fPlayer2BeadsCnt = 12;
		// Update sub-interface communication channel
		setPlayer1BeadsCnt(12);
		setPlayer2BeadsCnt(12);
		// Intialise AI variables
		fAIMoveTime=0;
		fAIWaiting = false;
		fAITakeAnimationID = "";
	}

	/**
	* An accessor that checks to see if the player is allowed to click on the board
	* an this is important when disabling Player 1's board interaction so that
	* the player cannot interfere with the AI's movement.
	*
	* @return returns a boolean representing if the player can press on the board.
	*/ 
	public boolean isControlEnabled()
	{
		return fControlsEnabled;
	}

	/*
	* Used to switch the players ability to click on the board to 
	* the opposite mode of what it currently is.
	*/
	private void toggleControls()
	{
		fControlsEnabled = !(fControlsEnabled);
		// Seen when debugging mode is on
		if (fControlsEnabled == true)
			Debugger.report("Controls Enabled");
		else
			Debugger.report("Controls Disabled");
	}

	/**
	* Accessor used to see what the current Game Status is as defined in this classes description.
	*
	* @return returns a string representing the Game Status.
	*/
	public String getGameStatus()
	{
		return fGameStatus;
	}

	/*
	* Used to update the game status according to an assessment of the game
	*/
	private void setGameStatus()
	{
		fGameStatus = assessGameStatus();
	}

	/*
	* Assess the game to determine the current game status.
	*
	* @return returns a string representing this.
	*/
	private String assessGameStatus()
	{ 
		printBoardState();
		// Win & Lose Status - By no beads lefs
		if (fPlayer2BeadsCnt == 0)
			return "WIN1";
		if (fPlayer1BeadsCnt == 0)
			return "WIN2";
		// Check Secondary Win - Opponent has no legal moves left:
		// Shift index according to which player is playing
		int iStartPos = 0;
		if (fPlayer == 1)
		{
			iStartPos = 12;
		}
		else
			iStartPos = 0;
		// Scan for moves of all types
		boolean bMovePossible = false;
		boolean bTakePossible = false;
		for (int i = 0; i < 12; i++)
		{
			if (fBeads[i+iStartPos].isActive() == true)
			{

				if (fBeads[i+iStartPos].showTakes(getBoard(),false) == true)
					{
						bTakePossible = true;
					}
				if (fBeads[i+iStartPos].showGeneralMoves(getBoard(),false) == true)
					{
						bMovePossible = true;
					}
			}
		}
		// Secondary Win Check
		if ((bMovePossible == false) && (bTakePossible == false))
		{
			if (fPlayer == 1)
				return "WIN2";
			else
				return "WIN1";
		}
		// Manditory takes chack
		if (bTakePossible == true)
		{
			fGlobalDetails.setMustTake(true);
			return "MUSTTAKE";
		}
		// "normal" moving around only
		fGlobalDetails.setMustTake(false);
		return "BUSY";
	}

	/**
	 * An accessor for seeing which player's turn it currently is.
	 *
	 * @return returns an integer representing this (eg: [1] = Player 1 (blue), [1] = Player 2 (red)); 
	 */
	public int getPlayerTurn()
	{
		return fPlayer;
	}

	/*
	 * An private mutator for centralising the setting of the board mode.
	 * useful for seeing changes during debugging.
	 */
	private void setMode(String theMode)
	{
		fMode = theMode;
		Debugger.report(theMode);
	}

	/**
	 * A method for changing the player turn at the end a player's turn.
	 */
	public void alternatePlayer()
	{
		if (fPlayer == 1) 	// Case: Player 1's turn Ends
			fPlayer = 2;
		else
			fPlayer = 1;
		// Update sub-interface communication channel
		fGlobalDetails.setPlayerTurn(fPlayer);
		// Updates game status to check for wins,...
		setGameStatus();
	}

	/**
	 * A method used to check if any beads need crowing and then crowns where applicatble
	 */
	private void checkForCrowns()
	{
		// iterate all beads
		for (int i = 0; i < 23; i++)
		{
			if ((fBeads[i].isActive() == true) & (fBeads[i].isCrowned() == false))
			{
				int iPlayerNr = fBeads[i].getPlayerNR();
				int iYPoint = fBeads[i].getY();
				if ((iPlayerNr == 1) && (iYPoint == 0) && (fBeads[i].isCrowned() == false))			// Case: P1 Bead needs crowning
				{
					fBeads[i].crownBead();
				}
				else if ((iPlayerNr == 2) && (iYPoint == 7) && (fBeads[i].isCrowned() == false)) 	// Case: P2 Bead needs crowning
				{
					fBeads[i].crownBead();
				}
			}  
		}
	}

	/**
	 * A method for contolling the clicks of the player / movements of beads in the game simulation
	 * and is arguably the most important method of the program as it is what allows for beads to be interacted
	 * with and for them to move according to the rules of checkers.
	 *
	 * @return Returns a string that is used to determine if a bead-take animation must be made. (eg:""-no Animation, or the animation ID)
	 */ 
	public String getPossibleMoves(Location theLocation)
	{
		// Note: Being able to take immediately after being crowned is not allowed as
		//		 I believe it is far too over powered.
		printBoardState();
		// Load the location desired
		String sBead = fBoard[theLocation.getY()][theLocation.getX()];
		// Case: Location desired has no bead / move locator / take locator  on it or is an illegal tile.
		if (sBead.equals("") || sBead.equals("X"))
			return "";
		// Case: there is a move locator being pressed
		else if (sBead.equals("*") && fMode.equals("MOVE")) // Board Symbol: "*" means move
		{
			// sub-case: there is a manditory take available, so ignore the click
			if (getGameStatus().equals("MUSTTAKE")==true)
				return "";
			// clear old bead number;
			fBoard[fBeads[fCurrentBead].getY()][fBeads[fCurrentBead].getX()] = "";
			// update coordinates of bead to be move
			fBeads[fCurrentBead].setX(theLocation.getX());
			fBeads[fCurrentBead].setY(theLocation.getY());
			// update bead on map
			fBoard[fBeads[fCurrentBead].getY()][fBeads[fCurrentBead].getX()] = fCurrentBead+"";
			// clean board
			clearMoves();
			// end turn
			alternatePlayer();
			setMode("SELECT");
			// check for crowning
			checkForCrowns();
			return "";
		}
		// Case: Take indicator pressed
		else if (sBead.equals("#") && fMode.equals("MOVE")) // Board Symbol: "#" means take
		{
			// clear old bead
			fBoard[fBeads[fCurrentBead].getY()][fBeads[fCurrentBead].getX()] = "";
			// delete target bead
			int xDel = fBeads[fCurrentBead].getX() + ((theLocation.getX()-fBeads[fCurrentBead].getX())/2);
			int yDel = fBeads[fCurrentBead].getY() + ((theLocation.getY()-fBeads[fCurrentBead].getY())/2);
			int iBeadIndex = Integer.parseInt(fBoard[yDel][xDel]);
			fBeads[iBeadIndex].killBead();
			fBoard[yDel][xDel] = "";
			// Update bead counters
			if (fBeads[iBeadIndex].getPlayerNR() == 1)
				setPlayer1BeadsCnt(--fPlayer1BeadsCnt);
			else
				setPlayer2BeadsCnt(--fPlayer2BeadsCnt);
			// update taker bead
			int newX = theLocation.getX();
			int newY = theLocation.getY();
			fBeads[fCurrentBead].setX(newX);
			fBeads[fCurrentBead].setY(newY);
			fBoard[newY][newX] = fCurrentBead+""; // quick int conversion used
			// clean board
			clearMoves();
			// check for more moves again
			if (fBeads[fCurrentBead].showTakes(fBoard,false) == true)
			{
				iRecurssiveTaker = fCurrentBead;
				fBeads[fCurrentBead].showTakes(fBoard,true);
				// Now end by sending over by sending take animation ID
				return xDel+","+yDel+","+fBeads[fCurrentBead].getPlayerNR();
			}
			else // Case: no more takes available
			{
				iRecurssiveTaker = -1;
			}
			// end turn
			alternatePlayer();
			setMode("SELECT");
			// check for crowning
			checkForCrowns();
			// end by sending over take animation ID
			return xDel+","+yDel+","+fBeads[fCurrentBead].getPlayerNR();
		}
		if (iRecurssiveTaker > -1) // Case: Prevents user from moving another bead instead of taking
			return "";
		// Case: If seleceting a new piece
		// clear old move indicators
		if (fMode.equals("MOVE")) 
		{
			clearMoves();
		}
		// add new move indicators
		int iIndex = Integer.parseInt(sBead);
		fCurrentBead = iIndex;
		if (fBeads[iIndex].getPlayerNR() == fPlayer)
		{
			fBeads[iIndex].showMoves(fBoard);
		}
		fMode = "MOVE";
		return "";
	}

	/*
	* A private mutator for changing Player 1's bead counter
	*/
	private void setPlayer1BeadsCnt(int iBeadsCnt)
	{
		fGlobalDetails.setP1BeadsCnt(iBeadsCnt);
	}

	/*
	* A private mutator for changing Player 2's bead counter
	*/
	private void setPlayer2BeadsCnt(int iBeadsCnt)
	{
		fGlobalDetails.setP2BeadsCnt(iBeadsCnt);
	}

	/*
	* A private method for cleaning all move indicators("*"-Move,"#"-Take) from the board
	*/
	private void clearMoves()
	{
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				if ((fBoard[y][x].equals("*")) || (fBoard[y][x].equals("#")))
				{
					fBoard[y][x] = "";
				}
			}
		} 
	}

	/**
	 * An accessor for getting the current game board, here a shallow copy is made to ensure that
	 * all parts of the system contiually have the abillity to access the latest board configuration.
	 * It also allow beads to indicate their moves directly onto the board an this is why cleaning the
	 * board is important by means of clearMoves().
	 *
	 * @return returns a 2D string array (shallow copy) of the current game configuration 
	 */
	public String[][] getBoard()
	{
		return fBoard;
	}

	/**
	 * A accessor method used to see if a supplied bead has been crowned
	 *
	 * @return returns a boolean representing if the supplied bead (via index) has been crowned
	 */
	public boolean isBeadCrowned(int iBeadID)
	{
		return fBeads[iBeadID].isCrowned();	
	}

	/**
	 * A accessor method used to see what animation frame a supplied bead is on
	 *
	 * @return returns a integer representing the frame.
	 */
	public int getBeadFrame(int iBeadID)
	{
		return fBeads[iBeadID].getFrame();	
	}

	/**
	 * A accessor method used to see which player owns a supplied bead.
	 *
	 * @return returns a integer representing the player. (eg: 1 = blue player 1)
	 */
	public int getPlayerID(int iBeadID)
	{
		return fBeads[iBeadID].getPlayerNR();
	}

	/**
	 * A debugging method used to print the current board configuration to the command line
	 * in a beautiful 2D format so that visually it represents the 2D board
	 */
	public void printBoardState()
	{
		if (Debugger.CANREPORT == false) //Case: if debugger mode is off
		{
			return;
		}
		// iterate through text board
		for (int y = 0; y < 8; y++)
		{
			System.out.print("\n-------------------------\n" + "|");
			for (int x = 0; x < 8; x++)
			{
				if (fBoard[y][x].equals(""))
					System.out.print("  |");
				else if (fBoard[y][x].length() == 1)
					System.out.print(fBoard[y][x] + " |");
				else
					System.out.print(fBoard[y][x] + "|");
			}
		}	
		System.out.println("\n-------------------------");
	}

	/**
	 * A method for getting the AIs take animation ID and clears the ID afterwards to ensure
	 * only one animation.
	 *
	 * @return returns a String representing a take animation ID
	 */
	public String getAIAnimationID()
	{
		String sID = fAITakeAnimationID;
		fAITakeAnimationID = "";
		return sID;
	}

	/**
	 * A method that represents the AI and its decision making, once called the AI will make a move
	 * based on its logic. -Note: the AI uses randomization to determine its desired moves, but
	 * from experiece it may appear quite deliberate :).
	 */
	public void moveAI()
	{
		// Case: AI is allowing time for move idicator to be displayed correclty
		if (fAIWaiting == true)
		{
			// Case: Delay Finished, move to location
			if (fAIMoveTime >= AIMOVEDELAY)
			{
				fAIMoveTime = 0;
				fAIWaiting = false;
				// ensures take animation
				fAITakeAnimationID = new String(getPossibleMoves(fAIMoveLocation));
				toggleControls();
				return;
			}
			// Case: Still waiting
			else
			{
				fAIMoveTime += Game.GAMETICKINTERVAL;
				return;
			}
		}
		// Prevent User from interfering
		toggleControls();
		Debugger.report("AI: Im thinking..."+fGameStatus);
		// Case: if general moves are possible
		if (fGameStatus.equals("BUSY"))
		{
			Debugger.report("AI: Im thinking of moving ...");
			// find a movable piece
			boolean bMoveFound = false;
			int iBeadIndex = 0;
			while (bMoveFound == false)
			{
				iBeadIndex = ranGen.nextInt(12);
				if ((fBeads[iBeadIndex].isActive() == true) && (fBeads[iBeadIndex].showGeneralMoves(fBoard,false)))
				{
					bMoveFound = true;
					Debugger.report("AI: found a move...");
				}
			}
			// idicate movement
			Location chosenPieceLoc = new Location(fBeads[iBeadIndex].getX(),fBeads[iBeadIndex].getY());
			getPossibleMoves(chosenPieceLoc);
			// Store move
			for (int y = 0; y < 8; y++)
			{
				for (int x = 0; x < 8; x++)
				{
					if (fBoard[y][x].equals("*"))
					{
						fAIMoveLocation = new Location(x,y);
						fAIWaiting = true;
						fAIMoveTime = 0;
						return;
					}
				}
			}
		}
		else if (fGameStatus.equals("MUSTTAKE"))
		{
			// find a movable piece
			boolean bMoveFound = false;
			int iBeadIndex = 0;
			// recurssive take case
			if (iRecurssiveTaker > -1)
			{
				bMoveFound = true;
				iBeadIndex = iRecurssiveTaker;
			}
			while (bMoveFound == false)
			{
				iBeadIndex = ranGen.nextInt(12);
				Debugger.report("AI: Im thinking of taking ..."+iBeadIndex);
				if ((fBeads[iBeadIndex].isActive() == true) && (fBeads[iBeadIndex].showTakes(fBoard,false)))
				{
					bMoveFound = true;
					Debugger.report("AI: found a move...");
				}
			}
			// idicate movement
			Location ChosenPiece = new Location(fBeads[iBeadIndex].getX(),fBeads[iBeadIndex].getY());
			getPossibleMoves(ChosenPiece);
			// Store move
			for (int y = 0; y < 8; y++)
			{
				for (int x = 0; x < 8; x++)
				{
					if (fBoard[y][x].equals("#"))
					{
						fAIMoveLocation = new Location(x,y);
						fAIWaiting = true;
						fAIMoveTime = 0;
						return;
					}
				}
			}
		}
	}

}