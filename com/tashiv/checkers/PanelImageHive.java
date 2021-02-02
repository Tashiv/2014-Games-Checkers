package com.tashiv.checkers;

//// imports
import javax.swing.ImageIcon;
import java.awt.Image;

//// Class
/**
 * A class for storing and managing all externally stored graphical
 * images used by the Side Window when rendering an output. This class
 * provides a central means for expanding the graphical capabilities
 * of the Side Window.
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class PanelImageHive
{
	//// Graphics
	// Player Portraits
	private static final String portraitsRoot = "images/Portraits/";						// Portrait Root Folder
	private static ImageIcon activeP1Portrait = new ImageIcon(portraitsRoot+"P1A.JPG");		// Portrait Player 1 - Active
	private static ImageIcon activeP2Portrait = new ImageIcon(portraitsRoot+"P2A.JPG");		// Portrait Player 2 - Deactive
	private static ImageIcon inactiveP1Portrait = new ImageIcon(portraitsRoot+"P1I.JPG");	// Portrait Player 1 - Active
	private static ImageIcon inactiveP2Portrait = new ImageIcon(portraitsRoot+"P2I.JPG");	// Portrait Player 1 - Deactive
	// Buttons
	private static final String btnsRoot = "images/Buttons/";								// Buttons Root Folder
	private static ImageIcon imgPVCBtn = new ImageIcon(btnsRoot+"PVC.png");					// Player vs Player Button
	private static ImageIcon imgPVCBtnDim = new ImageIcon(btnsRoot+"PVCDim.png");			// Player vs Player Button Dim
	private static ImageIcon imgPVPBtn = new ImageIcon(btnsRoot+"PVP.png");					// Player vs AI Button
	private static ImageIcon imgPVPBtnDim = new ImageIcon(btnsRoot+"PVPDim.png");			// Player vs AI Button Dim
	private static ImageIcon imgTutorialBtn = new ImageIcon(btnsRoot+"Tutorial.png");		// Tutorial Button
	private static ImageIcon imgTutorialBtnDim = new ImageIcon(btnsRoot+"TutorialDim.png");	// Tutorial Button Dim
	private static ImageIcon imgAboutBtn = new ImageIcon(btnsRoot+"About.png");				// About Button
	private static ImageIcon imgAboutBtnDim = new ImageIcon(btnsRoot+"AboutDim.png");		// About Button Dim
	private static ImageIcon imgMenuBtn = new ImageIcon(btnsRoot+"Menu.png");				// Quit End Game Button
	private static ImageIcon imgQuitBtn = new ImageIcon(btnsRoot+"Quit.png");				// Menu End Game Button
	// Status bar
	private static final String sBarRoot = "images/StatusBar/";								// Status Bar Root Folder
	private static ImageIcon[] imgStatusBar = new ImageIcon[3];								// All Status Bar Modes
	// banners
	private static final String bannersRoot = "images/Banners/";							// Banner Root Folder
	private static ImageIcon imgMenuBanner = new ImageIcon(bannersRoot+"Menu.png");			// Side Window Background Banner
	// taken beads counter
	private static final String nrsRoot = "images/Numbers/";								// Number Images root folder
	private static ImageIcon imgP1Counter = new ImageIcon(portraitsRoot+"P1Counter.png");	// P1 Counter Background 
	private static ImageIcon imgP2Counter = new ImageIcon(portraitsRoot+"P2Counter.png");	// P2 Counter Background
	private static ImageIcon[] imgNumbers = new ImageIcon[13];								// Counter Numbers
	/**
	* A constructor for initialising the class but loading 
	* the appropriate grapihcal components.
	*/
	public PanelImageHive()
	{
		// Status Bar Bead Loading
		for (int i = 0; i < 3; i++)
		{
			String sImgName = sBarRoot + "statusBar"+i+".png";  // Builds filename
			imgStatusBar[i] = new ImageIcon(sImgName);			// Loads the image
		}
		// numbers loading
		for (int i = 0; i < 13; i++)
		{
			String sImgName = nrsRoot + i +".png"; 	
			imgNumbers[i] = new ImageIcon(sImgName);				
		}
	}
	
	/**
	* A accessor for getting the Menu Button.
	*
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getMenuBtn()
	{
		return imgMenuBtn.getImage();
	}

	/**
	* A accessor for getting the Quit Button.
	*
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getQuitBtn()
	{
		return imgQuitBtn.getImage();
	}

	/**
	* A accessor for getting the PVC Button which corresponds to the game state of the graphic.
	* @param isSelected a boolean expression indicating if the button is pressed.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getPVCBtn(boolean isSelected)
	{
		if (isSelected == true)
			return imgPVCBtn.getImage();		// Pressed in
		else			
			return imgPVCBtnDim.getImage();		// Not Pressed in
	}

	/**
	* A accessor for getting the PVP Button which corresponds to the game state of the graphic.
	* @param isSelected a boolean expression indicating if the button is pressed.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getPVPBtn(boolean isSelected)
	{
		if (isSelected == true)
			return imgPVPBtn.getImage();		// Pressed
		else
			return imgPVPBtnDim.getImage();		// Not Pressed
	}

	/**
	* A accessor for getting the Tutorial Button which corresponds to the game state of the graphic.
	* @param isSelected a boolean expression indicating if the button is pressed.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getTutorialBtn(boolean isSelected)
	{
		if (isSelected == true)
			return imgTutorialBtn.getImage();		// Pressed
		else
			return imgTutorialBtnDim.getImage();	// Not Pressed
	}

	/**
	* A accessor for getting the background graphic for player 1's
	* score.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getP1Counter()
	{
		return imgP1Counter.getImage();
	}

	/**
	* A accessor for getting the background graphic for player 2's
	* score.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getP2Counter()
	{
		return imgP2Counter.getImage();
	}


	/**
	* A accessor for getting a graphic representing a number from 0 to 12
	* @param theNumber the number of the graphic desired
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getNumber(int theNumber)
	{
		return imgNumbers[theNumber].getImage();
	}

	/**
	* A accessor for getting the About Button which corresponds to the game state of the graphic.
	* @param isSelected a boolean expression indicating if the button is pressed.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getAboutBtn(boolean isSelected)
	{
		if (isSelected == true)
			return imgAboutBtn.getImage();		// Pressed
		else
			return imgAboutBtnDim.getImage();	// Not Pressed
	}

	/**
	* A accessor for getting the Player 1's Portrait corresponding to the state of the player (Playing vs Not Playing).
	* @param currentPlayer a int representing the current playing player.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getP1Portrait(int currentPlayer)
	{
		if (currentPlayer == 1) 
			return activeP1Portrait.getImage();  // Playing
		return inactiveP1Portrait.getImage();	 // Not Playing
	}

	/**
	* A accessor for getting the Player 2's Portrait corresponding to the state of the player (Playing vs Not Playing).
	* @param currentPlayer a int representing the current playing player.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getP2Portrait(int currentPlayer)
	{
		if (currentPlayer == 2) 
			return activeP2Portrait.getImage(); 	// Playing
		return inactiveP2Portrait.getImage();		// Not Playing
	}

	/**
	* A accessor for getting the status bar corresponding to is came state ([0] - Normal Mode, [1,2] - There is
	* a Manditory take available.)
	* @param iMode The current frame(Mode) of the status bar. 
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getStatusbar(int iMode)
	{
		return imgStatusBar[iMode].getImage();
	}

	/**
	* A accessor for getting the Side Bar background.
	* @return returns the corresponding graphic in an Image object.
	*/
	public static Image getMenuBanner()
	{
		return imgMenuBanner.getImage();
	}

} 