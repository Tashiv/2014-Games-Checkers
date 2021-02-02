package com.tashiv.checkers;

//// class
/**
 * A class that represent a piece on the checkers board used for 
 * simulating the checkers game and for rendering purposes.
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class Bead
{
	//// instance variables
	private static final int DELAY = 4; // Delay value for animation delay
	private int fAccumulator;			// used to track delay
	private byte fPlayerNr;			// Player who own the piece
	private boolean fActive;		// is it on the board still - alive
	private Location fLocation;		// coordinates of the piece
	private int fFrame;				// current frame of animation
	private boolean fCrowned;		// is it crowned

	//// instance methods
	/**
	* A constructor for inistializing instance variables.
	*
	* @param iPlayerNr the number of the Player to whom the piece belongs
	* @param iX The x coordinate of the bead
	* @param iY The y coordinate of the bead
	* @param iFrameSeed the starting frame of the animation, should be random for nice effect
	*/
	public Bead(byte iPlayerNr, int iX, int iY, int iFrameSeed)
	{
		// initialize instance variables
		fPlayerNr = iPlayerNr;
		fLocation = new Location(iX,iY);
		fFrame = iFrameSeed;
		fCrowned = false;
		fAccumulator = 0;
		// activate bead
		fActive = true;
	}

	/**
	* A accessor for checking if the bead is active / on the board still.
	*
	* @return returns a boolean expression representing the status of the bead
	*/
	public boolean isActive()
	{
		return fActive;
	}

	/**
	* A mutator for deactivating a bead once it has been taken by the other player
	*/
	public void killBead()
	{
		fActive = false;
	}

	/**
	* A mutator for crowning a bead once it has reached the other end of the board
	*/
	public void crownBead()
	{
		fCrowned = true;
	}

	/**
	* A accessor for getting the x coordinate of a bead on the board.
	*
	* @return returns x-coordinate of bead
	*/
	public int getX()
	{
		return fLocation.getX();
	}

	/**
	* A accessor for getting the y coordinate of a bead on the board.
	*
	* @return returns y-coordinate of bead
	*/
	public int getY()
	{
		return fLocation.getY();
	}

	/**
	* A mutator for setting the x coordinate of a bead on the board.
	*
	* @param xCoordinate the x-coordinate of bead
	*/
	public void setX(int xCoordinate)
	{
		fLocation.setX(xCoordinate);
	}

	/**
	* A mutator for setting the y coordinate of a bead on the board.
	*
	* @param yCoordinate the y-coordinate of bead
	*/
	public void setY(int yCoordinate)
	{
		fLocation.setY(yCoordinate);
	}

	/**
	* A method for getting the current animation frame of the bead for rendering 
	* and then automatically incrementing it for the next time it is needed.
	*
	* @return returns the frame number of the beads current animation. 
	*/
	public int getFrame()
	{
		// store current frame
		int iResult = fFrame;
		// crown increment
		if (isCrowned() == true)
		{
			fAccumulator += 100;
			if (fAccumulator >= DELAY)
			{
				fFrame++;
				fAccumulator = 0;
			}
		}
		// standard piece increment
		else
			fFrame++;
		if (fFrame == CoreImageHive.BEADFRAMES) // Case: when current frame surpasses available frames
		{
			fFrame = 0;
		}
		return iResult;
	}

	/**
	* A accessor for getting the owner of the bead.
	*
	* @return returns player number
	*/
	public int getPlayerNR()
	{
		return fPlayerNr;
	}
	
	/**
	* A accessor for checking if a bead is crowned.
	*
	* @return returns boolean representing if bead is crowned
	*/
	public boolean isCrowned()
	{
		return fCrowned;
	}
	
	/**
	* A method indicating all the possible moves a bead can make
	* using symbols for simulation and rendering purposes
	* 
	* @param theBoard the 2D string array representing the game board
	*/
	public void showMoves(String[][] theBoard)
	{
		showGeneralMoves(theBoard,true);
		showTakes(theBoard,true);
	}

	/**
	* A method indicating all the possible translational moves a bead can make
	* using symbols for simulation and rendering purposes
	* 
	* @param theBoard the 2D string array representing the game board
	* @param markMoves a boolean indicating if the board should be marked
	* @return returns boolean representing if general moves are possible 
	*/
	public boolean showGeneralMoves(String[][] theBoard, boolean markMoves)
	{
		// Note: Player one starts at bottom of screen, but top left represents 0,0
		//       - all coordinate calculations are based on this fact
		if ((fPlayerNr == 1) || (isCrowned() == true))
		{
			// Player 1 Movement Direction
			if (canMoveForwardRight(theBoard) == true)
			{
				int y = getY()-1;
				int x = getX()+1;
				if (markMoves == true)
					theBoard[y][x] = new String (theBoard[y][x] + "*");
				else
					return true;
			}
			if (canMoveForwardLeft(theBoard) == true)
			{
				int y = fLocation.getY()-1;
				int x = fLocation.getX()-1;
				if (markMoves == true)
					theBoard[y][x] = new String (theBoard[y][x] + "*");
				else
					return true;
			}
		}
		if ((fPlayerNr == 2) || (isCrowned() == true))
		{
			// Player 2 Movement Direction
			if (canMoveDownwardRight(theBoard) == true)
			{
				int y = fLocation.getY()+1;
				int x = fLocation.getX()+1;
				if (markMoves == true)
					theBoard[y][x] = new String (theBoard[y][x] + "*");
				else
					return true;
			}
			if (canMoveDownwardLeft(theBoard) == true)
			{
				int y = fLocation.getY()+1;
				int x = fLocation.getX()-1;
				if (markMoves == true)
					theBoard[y][x] = new String (theBoard[y][x] + "*");
				else
					return true;
			}	
		}
		return false;
	}

	/**
	* A method indicating all the possible bead-take moves a bead can make
	* using symbols for simulation and rendering purposes
	* 
	* @param theBoard the 2D string array representing the game board
	* @param markMoves a boolean indicating if the board should be marked
	* @return returns boolean representing if bead-taking moves are possible 
	*/
	public boolean showTakes(String[][] theBoard, boolean markMoves)
	{
		// Note: Player one starts at bottom of screen, but top left represents 0,0
		//       - all coordinate calculations are based on this fact
		if ((fPlayerNr == 1) || (isCrowned() == true))
		{
			// Player 1 movement direction
			if (canTakeForwardRight(theBoard) == true)
			{
				int y = fLocation.getY()-2;
				int x = fLocation.getX()+2;
				if (markMoves == true)
					theBoard[y][x] = new String (theBoard[y][x] + "#");
				else
					return true;
			}
			if (canTakeForwardLeft(theBoard) == true)
			{
				int y = fLocation.getY()-2;
				int x = fLocation.getX()-2;
				if (markMoves == true)
					theBoard[y][x] = new String (theBoard[y][x] + "#");
				else
					return true;
			}
		}
		if ((fPlayerNr == 2) || (isCrowned() == true))
		{
			// player 2 movement direction
			if (canTakeDownwardRight(theBoard) == true)
			{
				int y = fLocation.getY()+2;
				int x = fLocation.getX()+2;
				if (markMoves == true)
					theBoard[y][x] = new String (theBoard[y][x] + "#");
				else
					return true;
			}
			if (canTakeDownwardLeft(theBoard) == true)
			{
				int y = fLocation.getY()+2;
				int x = fLocation.getX()-2;
				if (markMoves == true)
					theBoard[y][x] = new String (theBoard[y][x] + "#");
				else
					return true;
			}
		}
		return false;
	}

	/*
	* A method for checking if a bead can move forward right from player 1s perspective
	* 
	* @param theBoard the 2D string array representing the game board
	* @result returns boolean representing if move is possible 
	*/
	private boolean canMoveForwardRight(String[][] theBoard)
	{
		// Note: Player one starts at bottom of screen, but top left represents 0,0
		//       - all coordinate calculations are based on this fact
		int y = fLocation.getY()-1;
		int x = fLocation.getX()+1;
		if ((y > -1) && (x < 8))
		{
			if ((theBoard[y][x]).equals("")) // "" on board means nothing is there
			{
				return true;
			}
		}
		return false;
	}

	/*
	* A method for checking if a bead can move down right from player 1s perspective
	* 
	* @param theBoard the 2D string array representing the game board
	* @result returns boolean representing if move is possible 
	*/
	private boolean canMoveDownwardRight(String[][] theBoard)
	{
		// Note: Player one starts at bottom of screen, but top left represents 0,0
		//       - all coordinate calculations are based on this fact
		int y = fLocation.getY()+1;
		int x = fLocation.getX()+1;
		if ((y < 8) && (x < 8))
		{
			if ((theBoard[y][x]).equals(""))
			{
				return true;
			}

		}
		return false;
	}

	/*
	* A method for checking if a bead can move forware left from player 1s perspective
	* 
	* @param theBoard the 2D string array representing the game board
	* @result returns boolean representing if move is possible 
	*/
	private boolean canMoveForwardLeft(String[][] theBoard)
	{
		// Note: Player one starts at bottom of screen, but top left represents 0,0
		//       - all coordinate calculations are based on this fact
		int y = fLocation.getY()-1;
		int x = fLocation.getX()-1;
		if ((y > -1) && (x > -1))
		{
			if (theBoard[y][x].equals(""))
				return true;
		}
		return false;
	}

	/*
	* A method for checking if a bead can move down left from player 1s perspective
	* 
	* @param theBoard the 2D string array representing the game board
	* @result returns boolean representing if move is possible 
	*/
	private boolean canMoveDownwardLeft(String[][] theBoard)
	{
		// Note: Player one starts at bottom of screen, but top left represents 0,0
		//       - all coordinate calculations are based on this fact
		int y = fLocation.getY()+1;
		int x = fLocation.getX()-1;
		if ((y < 8) && (x > -1))
		{
			if (theBoard[y][x].equals(""))
				return true;
		}
		return false;
	}

	/*
	* A method for checking if a bead can take forward right from player 1s perspective
	* 
	* @param theBoard the 2D string array representing the game board
	* @result returns boolean representing if take is possible 
	*/
	private boolean canTakeForwardRight(String[][] theBoard)
	{
		int y = fLocation.getY()-2;
		int x = fLocation.getX()+2;
		if ((y > -1) && (x < 8))
		{
			// check if there is an empty space to move to
			if ((theBoard[y][x].equals("")) && (STools.isDigit(theBoard[y+1][x-1])))
			{
				// check if the is an enemy piece to take
				int iTarget = Integer.parseInt(theBoard[y+1][x-1]);
				if ((fPlayerNr == 1) && (iTarget < 12)) 
					return true;
				else if ((fPlayerNr == 2) && (iTarget > 11)) 
					return true;
			}

		}
		return false;
	}

	/*
	* A method for checking if a bead can take down right from player 1s perspective
	* 
	* @param theBoard the 2D string array representing the game board
	* @result returns boolean representing if take is possible 
	*/
	private boolean canTakeDownwardRight(String[][] theBoard)
	{
		int y = fLocation.getY()+2;
		int x = fLocation.getX()+2;
		if ((y < 8) && (x < 8))
		{
			// check if there is an empty space to move to
			if ((theBoard[y][x]).equals("") && (STools.isDigit(theBoard[y-1][x-1])))
			{
				// check if the is an enemy piece to take
				if (((fPlayerNr == 1) && (Integer.parseInt(theBoard[y-1][x-1]) < 12)))
					return true;
				else if (((fPlayerNr == 2) && (Integer.parseInt(theBoard[y-1][x-1]) > 11))) 
					return true;
			}

		}
		return false;
	}

	/*
	* A method for checking if a bead can take forward left from player 1s perspective
	* 
	* @param theBoard the 2D string array representing the game board
	* @result returns boolean representing if take is possible 
	*/
	private boolean canTakeForwardLeft(String[][] theBoard)
	{
		int y = fLocation.getY()-2;
		int x = fLocation.getX()-2;
		if ((y > -1) && (x > -1))
		{
			// check if there is an empty space to move to
			if (theBoard[y][x].equals("") && (STools.isDigit(theBoard[y+1][x+1])))
			{
				// check if the is an enemy piece to take
				if (((fPlayerNr == 1) && (Integer.parseInt(theBoard[y+1][x+1]) < 12)))
					return true;
				else if (((fPlayerNr == 2) && (Integer.parseInt(theBoard[y+1][x+1]) > 11))) 
					return true;
			}
		}
		return false;
	}

	/*
	* A method for checking if a bead can take down left from player 1s perspective
	* 
	* @param theBoard the 2D string array representing the game board
	* @result returns boolean representing if take is possible 
	*/
	private boolean canTakeDownwardLeft(String[][] theBoard)
	{
		int y = fLocation.getY()+2;
		int x = fLocation.getX()-2;
		if ((y < 8) && (x > -1))
		{
			// check if there is an empty space to move to
			if (theBoard[y][x].equals("") && (STools.isDigit(theBoard[y-1][x+1])))
			{
				// check if the is an enemy piece to take
				if (((fPlayerNr == 1) && (Integer.parseInt(theBoard[y-1][x+1]) < 12)))
					return true;
				else if (((fPlayerNr == 2) && (Integer.parseInt(theBoard[y-1][x+1]) > 11))) 
					return true;
			}
		}
		return false;
	}

}