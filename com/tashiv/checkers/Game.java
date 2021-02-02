package com.tashiv.checkers;

//// imports
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Sub-interface GUI Class for rendering the core window along with simulating the 
 * game.
 * The Game (Program) Modes may be:
 * <1> "GAMEPVC" - Game running PVC 
 * <2> "GAMEPVP" - Game running PVP 
 * <3> "MENUPVC" - Menu for PVC
 * <4> "MENUPVP" - Menu fo PVP
 * <5> "GAMEPVCSTART" - Preparations for Game PVC
 * <6> "GAMEPVPSTART" - Preparations for Game PVP 
 * <7> "TUTORIAL" - Help menu
 * <8> "ABOUT" - Author menu
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014 
 */
public class Game extends JPanel implements ActionListener
{
	//// instance constants
	public static final int GAMETICKINTERVAL = 50;									// How long between render ticks
	public static final int AITHINKTIME = 600;										// How long AI takes to move
	//// Instance Variables
	private Timer tTimer;															// Rendering Timer
	private static Graphics2D gRender;												// Render Graphic
	private TextBoard fBoard;														// The game simulator
	private CoreImageHive imageStore = new CoreImageHive();							// Graphics resources
	private String fMode;															// Game Interface Mode
	private GlobalDetails fGlobalDetails;											// Sub-interface communcation object
	private CoreAnimationManager fAnimationManager = new CoreAnimationManager();	// Animations manager
	private int fAIDelay;															// AI time tracker
	private boolean fAIGame;														// is AI playing
	private boolean fTilesAlternator;												// for alternating tile animation
	private int fAlternateDelayer;													// Used for menu board animation background

	/**
	* A constructors for initializing the instance variables
	* and starting the render timer.
	*/
	public Game(GlobalDetails objDetails)
	{
		// GUI Settings
		setSize(400,400);
		setFocusable(true);
		// initializing instance variables
		fGlobalDetails = objDetails;
		fAIDelay = AITHINKTIME;
		fAlternateDelayer = 0;
		fTilesAlternator = false;
		// Set default starting mode
		setMode("MENUPVC");
		// Start rendering system
		tTimer = new Timer(GAMETICKINTERVAL,this);
		tTimer.start();
	}

	/**
	 * A centralised mutator for changing the game program mode.
	 *
	 * @param theMode The new mode.
	 */
	public void setMode(String theMode)
	{
		fMode = theMode;
		Debugger.report("GAME MODE SET TO: "+fMode);
		fGlobalDetails.setMode(theMode);
	}

	/**
	 * This would be the real time part of the game, ie all actions
	 * performed by the rendering timer.
	 *
	 * @param theEvent An object containing the supplied timers information.
	 */
	public void actionPerformed(ActionEvent theEvent)
	{
		// AI take bead animations check
		if ((fBoard != null) && (fMode.equals("GAMEPVC")))
		{
			String sResult = fBoard.getAIAnimationID();
			if (sResult.equals("") == false)
			{
				fAnimationManager.addTakeAnimation(sResult);
			}
		}
		// AI thinking - real time part - ensures delays so user has a chance to see movements of AI
		if ((fBoard != null) && (fBoard.getPlayerTurn()== 2) && (fMode.equals("GAMEPVC")))
		{
			// AI thinking time delay, give a more real feel to the interaction 
			if (fAIDelay >= 0)
			{
				fAIDelay -= GAMETICKINTERVAL;
			}
			else // Delay ended, now move
			{
				fBoard.moveAI();
				fAIDelay = AITHINKTIME; // reset delay
				// Check for Wins for PVC mode
				String sGameStatus = fBoard.getGameStatus();
				if (sGameStatus.indexOf("WIN") > -1)
					setMode(sGameStatus);
				else if (sGameStatus.equals("DRAW") == true)
					setMode(sGameStatus);
			}
		}
		// Repaint the GUI core window
		this.repaint();
	}

	/**
	 * A method for carrying out all preparation required for the PVP Game mode
	 */
	private void startPVPGame()
	{
		fAIGame = false;
		fBoard = new TextBoard(fGlobalDetails);
		setMode("GAMESTARTPVP");	
	}

	/**
	 * A method for carrying out all preparation required for the PVC Game mode
	 */
	private void startPVCGame()
	{
		fAIGame = true;
		fBoard = new TextBoard(fGlobalDetails);
		setMode("GAMESTARTPVC");
	}

	/**
	 * A method for interpreting all user clicks on the core window by means of looking at supplied location.
	 * made up of the clicks x and y coordinates.
	 *
	 * @param theLocation The location of the click in a Location object
	 */
	public void clickSpace(Location theLocation)
	{
		// controls the running of the game
		if (fMode.equals("GAMEPVC")||fMode.equals("GAMEPVP"))
		{
			// during game play
			if ((fBoard.isControlEnabled() == false) && (fMode.equals("GAMEPVC")) && (fBoard.getPlayerTurn() == 1))
				{
					Debugger.report("Hey, not your turn!");
					return;
				}
			// AI Take Animation
			// Human Player interaction
			int xPoint = (int) (theLocation.getX() / CoreImageHive.TILEWIDTH);
			int yPoint = (int) (theLocation.getY() / CoreImageHive.TILEWIDTH);
			Location targetBlock = new Location(xPoint,yPoint);
			// indicate result of click
			String sResult = fBoard.getPossibleMoves(targetBlock);
			// take animation
			if (sResult.equals("") == false)
				fAnimationManager.addTakeAnimation(sResult);
			String sGameStatus = fBoard.getGameStatus();
			if (sGameStatus.indexOf("WIN") > -1)
				setMode(sGameStatus);
			else if (sGameStatus.equals("DRAW") == true)
				setMode(sGameStatus);
		}
		// Click on the PVC menu
		else if (fMode.equals("MENUPVC"))
		{
			startPVCGame();
		}
		// Click on the PVP menu
		else if (fMode.equals("GAMESTARTPVP"))
		{
			setMode("GAMEPVP");
		}
		// Click on the starting dialog window in PVC
		else if (fMode.equals("GAMESTARTPVC"))
		{
			setMode("GAMEPVC");
		}
		// Click on the starting dialog window in PVP
		else if (fMode.equals("MENUPVP"))
		{
			startPVPGame();
		}
	}

	/**
	* A method for rendering the Core Window
	*/
	public void paint(Graphics theRender)
	{
		gRender = (Graphics2D) theRender;
		// THe checkers Board
		boolean bExclude = false;
		// alternation tile animation during menu time
		if (fMode.indexOf("GAME") == -1)
		{
			// Print alternated board
			if (fTilesAlternator == true)
			{
				bExclude = true;
			}
			// Delay is over, alternate the boolean tile alternator
			if (fAlternateDelayer >= 500)
			{
				if (fTilesAlternator == true)
				{
					fTilesAlternator = false;
					fAlternateDelayer = 0;
				}
				else
				{
					fTilesAlternator = true;
					fAlternateDelayer = 0;
				}
			}
			// Delay for alternating the tile alternator is not over
			else
			{
				fAlternateDelayer += 50;
			}
		}
		// Rendering the actual board
		int iTileWidth = CoreImageHive.TILEWIDTH;
		for (int y = 0; y < 8; y++)
		{
			bExclude = !(bExclude);
			
			for (int x = 0; x < 8; x++)
			{
				if (bExclude == true)
				{
					gRender.drawImage(imageStore.getIllegalTile(),x*iTileWidth,y*iTileWidth,null);
					bExclude = false;
				}
				else
				{
					gRender.drawImage(imageStore.getLegalTile(),x*iTileWidth,y*iTileWidth,null);
					bExclude = true;
				}
			}
		}
		// Renders game animations
		if (fMode.equals("GAMEPVP")||fMode.equals("GAMEPVC"))
		{
			String[][] sBoard = fBoard.getBoard();
			int iFrame = 0;
			for (int y = 0; y < 8; y++)
			{
				for (int x = 0; x < 8; x++)
				{
					// beads rendering
					if (STools.isDigit(sBoard[y][x]))
					{
						int iBeadID = Integer.parseInt(sBoard[y][x]);
						int iPlayerID = fBoard.getPlayerID(iBeadID);
						int sShift = imageStore.SHADOWSHIFT;
						iFrame = fBoard.getBeadFrame(iBeadID);
						if (iPlayerID == 1) // Case: Player 1 bead
						{
							if (fBoard.isBeadCrowned(iBeadID) == true) // Case: Crowened bead render
							{
								gRender.drawImage(imageStore.getCrownedShadow(iFrame),sShift+x*iTileWidth,sShift+y*iTileWidth,null);
								gRender.drawImage(imageStore.getUnit1Crowned(iFrame),x*iTileWidth,y*iTileWidth,null);
							}
							else // Case: Uncrowened bead render
							{
								gRender.drawImage(imageStore.getUnitShadow(iFrame),sShift+x*iTileWidth,sShift+y*iTileWidth,null);
								gRender.drawImage(imageStore.getUnit1(iFrame),x*iTileWidth,y*iTileWidth,null);
							}
						}
						else if (iPlayerID == 2) // Case: player 2 bead
						{
							if (fBoard.isBeadCrowned(iBeadID) == true) // Case: Crowened bead render
							{
								gRender.drawImage(imageStore.getCrownedShadow(iFrame),sShift+x*iTileWidth,sShift+y*iTileWidth,null);
								gRender.drawImage(imageStore.getUnit2Crowned(iFrame),x*iTileWidth,y*iTileWidth,null);
							}
							else // Case: Uncrowened bead render
							{
								gRender.drawImage(imageStore.getUnitShadow(iFrame),sShift+x*iTileWidth,sShift+y*iTileWidth,null);
								gRender.drawImage(imageStore.getUnit2(iFrame),x*iTileWidth,y*iTileWidth,null);
							}
						}
					}
					// Render a move locator
					else if (sBoard[y][x].equals("*"))
					{
						int iPlayerTurn = fBoard.getPlayerTurn();
						boolean bMustTake = false;
						iFrame = fAnimationManager.getMoveLocatorFrame();
						if (fBoard.getGameStatus().equals("MUSTTAKE"))
							bMustTake = true;
						gRender.drawImage(imageStore.getMoveTile(iPlayerTurn,iFrame,bMustTake),x*iTileWidth,y*iTileWidth,null);
					}
					// render a take locator
					else if (sBoard[y][x].equals("#"))
					{
						gRender.drawImage(imageStore.getTakeTile(fBoard.getPlayerTurn(),fAnimationManager.getTakeLocatorFrame()),x*iTileWidth,y*iTileWidth,null);
					}
				}
			}
			// extra take animations
			String[] sAnimations = fAnimationManager.getTakeAniFrames();
			if (sAnimations != null)
			{
				for (int k = 0; k < sAnimations.length; k++)
				{
					// Render Take animations
					String animateID = sAnimations[k];
					String[] sData = STools.animateIDtoData(animateID); // x,y,player,frame
					int xLoc = Integer.parseInt(sData[0]);
					int yLoc = Integer.parseInt(sData[1]);
					int currentFrame = Integer.parseInt(sData[3]);
					if (sData[2].equals("1"))
						gRender.drawImage(imageStore.getUnit2Take(currentFrame),xLoc*iTileWidth,yLoc*iTileWidth,null);
					else
						gRender.drawImage(imageStore.getUnit1Take(currentFrame),xLoc*iTileWidth,yLoc*iTileWidth,null);
				}
			}
		}
		// Render PVP Menu
		else if (fMode.equals("MENUPVC"))
		{
			gRender.drawImage(imageStore.getPVCBk(),0,0,400,400,null);
		}
		// Render PVP Menu
		else if (fMode.equals("MENUPVP"))
		{
			gRender.drawImage(imageStore.getPVPBk(),0,0,400,400,null);
		}
		// Render Tutorial Menu
		else if (fMode.equals("TUTORIAL"))
		{
			gRender.drawImage(imageStore.getTutorialBk(),0,0,400,400,null);
		}
		// Render About Menu
		else if (fMode.equals("ABOUT"))
		{
			int iFrame = fAnimationManager.getAboutFrame();
			gRender.drawImage(imageStore.getAboutBk(iFrame),0,0,400,400,null);
		}
		// Render Player Start dialog during PVP
		else if (fMode.equals("GAMESTARTPVP"))
		{
			if (fBoard.getPlayerTurn() == 1)
				gRender.drawImage(imageStore.getPlayer1BeginDLG(),0,0,400,400,null);
			else
				gRender.drawImage(imageStore.getPlayer2BeginDLG(),0,0,400,400,null);
		}
		// Render Player Start dialog during PVC
		else if (fMode.equals("GAMESTARTPVC"))
		{
			String[][] sBoard = fBoard.getBoard();
			if (fBoard.getPlayerTurn() == 1)
				gRender.drawImage(imageStore.getPlayer1BeginDLG(),0,0,400,400,null);
			else
				gRender.drawImage(imageStore.getAIBeginDLG(),0,0,400,400,null);
		}
		// Render Win Banner for player 1
		else if (fMode.equals("WIN1"))
		{
			gRender.drawImage(imageStore.getPlayer1WinBanner(),0,0,400,400,null);
		}
		// Render Win Banner for player 1
		else if (fMode.equals("WIN2"))
		{
			if (fAIGame == false)
				gRender.drawImage(imageStore.getPlayer2WinBanner(),0,0,400,400,null);
			else
				gRender.drawImage(imageStore.getAIWinBanner(),0,0,400,400,null);
		}
		// Render Draw Banner - Depreciated
		else if (fMode.equals("DRAW"))
		{
			gRender.drawImage(imageStore.getDrawBanner(),0,0,400,400,null);
		}
		// Report an unhandled mode
		else
		{
			Debugger.report("Unhandelled Render Mode: "+fMode);
		}
	}
}