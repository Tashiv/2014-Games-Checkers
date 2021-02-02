package com.tashiv.checkers;

// imports
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * A class that handles all resources for the core window GUI Interface
 **
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class CoreImageHive
{
	//// Instance Constants
	/**
	* A constant representing the Standard Tile Width, useful for rendering tiles.
	*/
	public static final int TILEWIDTH = 50; 	//Width of a unit tile in Pixels 
	/**
	* A constant representing the Standard amount of frames in an animation, useful for loading images.
	*/
	public static final int BEADFRAMES = 4;		// Nr of frames in uncrowned bead Type
	/**
	* A constant representing the Standard shift of a unit's shadow, useful for rendering. 
	*/
	public static final int SHADOWSHIFT = 5;	// Shift of the shadow in Pixels in X and Y Direction
	//// Graphics
	// Backgrounds
	private static final String bkRoot = "images/Backgrounds/";								// Backgrounds Root Folder
	private static ImageIcon imgPVCBackground = new ImageIcon(bkRoot+"PVC.png"); 			// PVP Menu background
	private static ImageIcon imgPVPBackground = new ImageIcon(bkRoot+"PVP.png");			// PVC Menu background
	private static ImageIcon imgTutorialBackground = new ImageIcon(bkRoot+"Tutorial.png");	// Tutorial Slide
	private static ImageIcon imgAboutBackgrounds[] = new ImageIcon[BEADFRAMES];				// About Slide 
	// Unit Icons
	private static final String unitsRoot = "images/Units/";								// unit icon folder root
	private static ImageIcon[] imgsUnit1 = new ImageIcon[BEADFRAMES];						// Player 1 uncrowned beads
	private static ImageIcon[] imgsUnit1Crowned = new ImageIcon[BEADFRAMES];				// Player 1 crowned bead
	private static ImageIcon[] imgsUnit2 = new ImageIcon[BEADFRAMES];						// Player 2 uncrowned beads
	private static ImageIcon[] imgsUnit2Crowned = new ImageIcon[BEADFRAMES];				// Player 2 crowned bead
	// Unit Effects
	private static ImageIcon[] imgsUnitShadow = new ImageIcon[BEADFRAMES];					// Crowned bead shadows
	private static ImageIcon[] imgsCrownedShadow = new ImageIcon[BEADFRAMES];				// Uncrowned bead shadows
	private static ImageIcon[] imgsUnit1Take = new ImageIcon[BEADFRAMES];					// P1 Unit's taken animation
	private static ImageIcon[] imgsUnit2Take = new ImageIcon[BEADFRAMES];					// P2 Unit's taken animation
	// Board Effects
	private static final String boardEffectRoot = "images/BoardEffects/";					// unit effects root folder
	private static ImageIcon[] imgsUnit1TakeTile = new ImageIcon[BEADFRAMES];				// P1 take-locator animation
	private static ImageIcon[] imgsUnit2TakeTile = new ImageIcon[BEADFRAMES];				// P2 take-locator animation
	private static ImageIcon[] imgsUnit1MoveTile = new ImageIcon[BEADFRAMES];				// P1 move-locator animation
	private static ImageIcon[] imgsUnit2MoveTile = new ImageIcon[BEADFRAMES];				// P2 move-locator animation
	private static ImageIcon[] imgsUnit1CantMoveTile = new ImageIcon[BEADFRAMES];			// P1 cannot-move-locator animation
	private static ImageIcon[] imgsUnit2CantMoveTile = new ImageIcon[BEADFRAMES];			// P2 cannot-move-locator animation	
	// Board Tiles
	private static final String tilesRoot = "images/Board/";								// board tiles root folder
	private static ImageIcon legalTile = new ImageIcon(tilesRoot+"legal.PNG");				// block that is moved on
	private static ImageIcon illegalTile = new ImageIcon(tilesRoot+"illegal.PNG");			// block that is NOT moved on
	// Starting Game Dialogs
	private static final String dialogsRoot = "images/Dialogs/";								// dialogs root directory
	private static ImageIcon imgAIBegins = new ImageIcon(dialogsRoot+"AIBegins.png");			// Player2 AI begins
	private static ImageIcon imgPlayer1Begins = new ImageIcon(dialogsRoot+"P1HumanBegins.png");	// Player1 Human begins
	private static ImageIcon imgPlayer2Begins = new ImageIcon(dialogsRoot+"P2HumanBegins.png"); // Player2 Human begins
	// Game Outcome Banners
	private static final String bannerRoot = "images/Banners/";								// banner storage root folder
	private static ImageIcon imgPlayer1Wins = new ImageIcon(bannerRoot+"P1Win.png");		// Player 1 Human Victory
	private static ImageIcon imgPlayer2Wins = new ImageIcon(bannerRoot+"P2Win.png");		// Player 2 Human Victory
	private static ImageIcon imgAIWins = new ImageIcon(bannerRoot+"AIWin.png");				// Player 2 AI victory
	private static ImageIcon imgDraw = new ImageIcon(bannerRoot+"Draw.png");				// No Victory or Loss - A Draw

	//// Instance methods
	/**
	 * A constructor method for loading all the graphics that occur in series.
	 */
	public CoreImageHive()
	{
		// variable used to generate file names:
		String sImgName = ""; 
		for (int i = 0; i < BEADFRAMES;i++ )
		{
			// Normal Shadow Loading
			sImgName = unitsRoot + "UnitShadow"+i+".png";
			imgsUnitShadow[i] = new ImageIcon(sImgName);
		 	// crowned shadow loading
			sImgName = unitsRoot + "SShadow"+i+".png";
			imgsCrownedShadow[i] = new ImageIcon(sImgName);
			// Player1 Bead Loading
			sImgName = unitsRoot + "Unit"+(1)+i+".png";
			imgsUnit1[i] = new ImageIcon(sImgName);
			// Player 1 Crowned Bead Loading
			sImgName = unitsRoot + "SBead"+(1)+i+".png";
			imgsUnit1Crowned[i] = new ImageIcon(sImgName);
			// Player 2 Bead Loading
			sImgName = unitsRoot + "Unit"+(2)+i+".png";
			imgsUnit2[i] = new ImageIcon(sImgName);
			// Player 2 Crowned Bead Loading
			sImgName = unitsRoot + "SBead"+(2)+i+".png";
			imgsUnit2Crowned[i] = new ImageIcon(sImgName);
			// Player 1 bead taken sprites
			sImgName = boardEffectRoot + "TakeAni"+(1)+i+".png";
			imgsUnit1Take[i] = new ImageIcon(sImgName);
			// Player 2 bead taken sprites
			sImgName = boardEffectRoot + "TakeAni"+(2)+i+".png";
			imgsUnit2Take[i] = new ImageIcon(sImgName);
			// Player 1 take tile indicators
			sImgName = boardEffectRoot + "takeTile"+(1)+i+".png";
			imgsUnit1TakeTile[i] = new ImageIcon(sImgName);
			// Player 2 take tile indicators
			sImgName = boardEffectRoot + "takeTile"+(2)+i+".png";
			imgsUnit2TakeTile[i] = new ImageIcon(sImgName);
			// Player 1 move tile indicators
			sImgName = boardEffectRoot+"moveTile"+(1)+i+".png";
			imgsUnit1MoveTile[i] = new ImageIcon(sImgName);
			// Player 2 move tile indicators
			sImgName = boardEffectRoot+"moveTile"+(2)+i+".png";
			imgsUnit2MoveTile[i] = new ImageIcon(sImgName);
			// Player 1 cannot move tile indicators
			sImgName = boardEffectRoot+"cantmoveTile"+(1)+i+".png";
			imgsUnit1CantMoveTile[i] = new ImageIcon(sImgName);
			// Player 2 cannot move tile indicators
			sImgName = boardEffectRoot+"cantmoveTile"+(2)+i+".png";
			imgsUnit2CantMoveTile[i] = new ImageIcon(sImgName);
			/// About menu loading
			sImgName = bkRoot+"About"+i+".png";
			imgAboutBackgrounds[i] = new ImageIcon(sImgName);
		}
	}

	// backgrounds section
	/**
	* An accessor for getting the image for the player vs AI menu dialog.
	*
	* @return an corresponding Image object
	*/
	public static Image getPVCBk()
	{
		return imgPVCBackground.getImage();
	}

	/**
	* An accessor for getting the image for the player vs Player menu dialog.
	*
	* @return an corresponding Image object
	*/
	public static Image getPVPBk()
	{
		return imgPVPBackground.getImage();
	}

	/**
	* An accessor for getting the image for the tutorial menu dialog.
	*
	* @return an corresponding Image object
	*/
	public static Image getTutorialBk()
	{
		return imgTutorialBackground.getImage();
	}

	/**
	* An accessor for getting the current image frame for the creator info menu dialog.
	*
	* @param iFrame A number representing the current frame of dialog animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getAboutBk(int iFrame)
	{
		return imgAboutBackgrounds[iFrame].getImage();
	}
	
	// unit icons
	/**
	* An accessor for getting the current image frame for an uncrowned player one bead.
	*
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getUnit1(int iFrame)
	{
		return imgsUnit1[iFrame].getImage();
	}

	/**
	* An accessor for getting the current image frame for an uncrowned player two bead.
	*
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getUnit2(int iFrame)
	{
		return imgsUnit2[iFrame].getImage();
	}

	/**
	* An accessor for getting the current image frame for a crowned player one bead.
	*
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getUnit1Crowned(int iFrame)
	{
		return imgsUnit1Crowned[iFrame].getImage();
	}

	/**
	* An accessor for getting the current image frame for a crowned player two bead.
	*
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getUnit2Crowned(int iFrame)
	{
		return imgsUnit2Crowned[iFrame].getImage();
	}

	// Unit Effects
	/**
	* An accessor for getting the current image frame for an uncrowned player bead shadow.
	*
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getUnitShadow(int iFrame)
	{
		return imgsUnitShadow[iFrame].getImage();
	}
	
	/**
	* An accessor for getting the current image frame for a crowned player bead shadow.
	*
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getCrownedShadow(int iFrame)
	{
		return imgsCrownedShadow[iFrame].getImage();
	}

	/**
	* An accessor for getting the current image frame for the player 1's bead being taken.
	*
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getUnit1Take(int iFrame)
	{
		return imgsUnit1Take[iFrame].getImage();
	}

	/**
	* An accessor for getting the current image frame for the player 2's bead being taken.
	*
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object
	*/
	public static Image getUnit2Take(int iFrame)
	{
		return imgsUnit2Take[iFrame].getImage();
	}

	// Board Effects
	/**
	* An accessor for getting the current image frame for the move indicator animation.
	* if the player must take an opponent's bead, then an "non-move" indicator animation
	* frame will be returned to enphasize the game condition.
	*
	* @param iPlayer the player whose turn it is.
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @param mustTake A boolean representing if the supplied player must take his opponent's bead
	* @return an corresponding Image object
	*/
	public static Image getMoveTile(int iPlayer, int iFrame, boolean mustTake)
	{
		if (iPlayer == 1) // Player 1's Move indicator
		{
			if (mustTake == false) 
				return imgsUnit1MoveTile[iFrame].getImage();
			else // case: must take opponent
				return imgsUnit1CantMoveTile[iFrame].getImage();
		}
		else // Player 2's Move indicator
		{
			if (mustTake == false) 
				return imgsUnit2MoveTile[iFrame].getImage();
			else // case: must take opponent
				return imgsUnit2CantMoveTile[iFrame].getImage();
		}
	}

	/**
	* An accessor for getting the current image frame for the supplied player's take-locator tile.
	*
	* @param iPlayer The player whose turn it is.
	* @param iFrame A number representing the current frame of the animation in integer form.
	* @return an corresponding Image object.
	*/
	public static Image getTakeTile(int iPlayer, int iFrame)
	{
		if (iPlayer == 1)
			return imgsUnit1TakeTile[iFrame].getImage();
		return imgsUnit2TakeTile[iFrame].getImage();
	} 

	// Board Tiles
	/**
	* An accessor for getting the image for a legal (ie the tiles on the board that are used) board tile.
	*
	* @return an corresponding Image object
	*/
	public static Image getLegalTile()
	{
		return legalTile.getImage();
	}

	/**
	* An accessor for getting the image for a illegal (ie the tiles on the board that arent used) board tile.
	*
	* @return an corresponding Image object
	*/
	public static Image getIllegalTile()
	{
		return illegalTile.getImage();
	}

	// Starting Game Dialogs
	/**
	* An accessor for getting the image for the Player 1 Begins Dialog used to show
	* that player 1 (human) goes first.
	*
	* @return an corresponding Image object
	*/
	public static Image getPlayer1BeginDLG()
	{
		return imgPlayer1Begins.getImage();
	}

	/**
	* An accessor for getting the image for the Player 2 Begins Dialog used to show
	* that player 2 (human) goes first.
	*
	* @return an corresponding Image object
	*/
	public static Image getPlayer2BeginDLG()
	{
		return imgPlayer2Begins.getImage();
	}

	/**
	* An accessor for getting the image for the Player 2 Begins Dialog used to show
	* that player 2 (AI) goes first.
	*
	* @return an corresponding Image object
	*/
	public static Image getAIBeginDLG()
	{
		return imgAIBegins.getImage();
	}

	// Game Outcome Banners
	/**
	* An accessor for getting the image for the Player 1 Wins Banner used to show
	* that player 1 (Human) wins.
	*
	* @return an corresponding Image object
	*/
	public static Image getPlayer1WinBanner()
	{
		return imgPlayer1Wins.getImage();
	}

	/**
	* An accessor for getting the image for the Player 2 Wins Banner used to show
	* that player 2 (Human) wins.
	*
	* @return an corresponding Image object
	*/
	public static Image getPlayer2WinBanner()
	{
		return imgPlayer2Wins.getImage();
	}

	/**
	* An accessor for getting the image for the Player 2 Wins Banner used to show
	* that player 2 (AI) wins.
	*
	* @return an corresponding Image object
	*/
	public static Image getAIWinBanner()
	{
		return imgAIWins.getImage();
	}

	/**
	* An accessor for getting the image for the Draw Banner used to show
	* that the game ended in no legal moves
	*
	* Depreciated as the is currently no rules to define a draw.
	* Initially, coming from a chess background I put the Draw
	* in as no legal moves for the enemy is a stale-mate which
	* is equalivalent to a draw, but the assignment states 
	* it is a opponent win.
	*
	* @return an corresponding Image object
	*/
	public static Image getDrawBanner()
	{
		return imgDraw.getImage();
	}
}

	