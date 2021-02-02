package com.tashiv.checkers;

//// Imports
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//// Class
/**
 * A GUI class that operates the Side Window (panel) of the program which is
 * generally either used to provide buttons to press of the 
 * or show the game status during game time.
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class SidePanel extends JPanel implements ActionListener
{
	private Timer tTimer;														// Used to render the side panel
	private static Graphics2D gPanelRender;										// Graphic to be rendered to panel
	private PanelImageHive imageStore = new PanelImageHive();					// connection to panel image resources
	private GlobalDetails fGlobalDetails;										// object for inter sub-interface communcations
	private Animation statusBarAni = new Animation("Status Bar",500,2,true);	// Animation tracker for the Status Bar

	/**
	* A constructor for initializing the class and starting the rendering process
	*/
	public SidePanel(GlobalDetails globalDetails)
	{
		// initialized class variables
		setSize(150,400);
		setFocusable(true);
		// Create sub-interface communication channel
		fGlobalDetails = globalDetails;
		// start render timer
		tTimer = new Timer(Game.GAMETICKINTERVAL,this);
		tTimer.start();
	}

	/**
	* An event method that is ran for each tick of the rendering timer.
	*
	*@param theCaller an object containing details about the calling timer
	*/
	public void actionPerformed(ActionEvent theCaller)
	{
		this.repaint();
	}

	/**
	* The GUIs rendering method, writes appropriate graphics to an image
	* which is then written to the screen for better performance.
	*
	*@param thePanelRender the previous rendered screen
	*/
	public void paint(Graphics thePanelRender)
	{
		// prepare
		gPanelRender = (Graphics2D) thePanelRender;
		String sMode = fGlobalDetails.getMode();
		// Menu Banner render
		gPanelRender.drawImage(imageStore.getMenuBanner(),400,0,null);
		// PVC Button render
		if (sMode.equals("MENUPVC"))
		{
			gPanelRender.drawImage(imageStore.getPVCBtn(true),409,9,null);
			gPanelRender.drawImage(imageStore.getPVPBtn(false),409,109,null);
			gPanelRender.drawImage(imageStore.getTutorialBtn(false),409,209,null);
			gPanelRender.drawImage(imageStore.getAboutBtn(false),409,309,null);
		}
		// PVP button render
		else if (sMode.equals("MENUPVP"))
		{
			gPanelRender.drawImage(imageStore.getPVCBtn(false),409,9,null);
			gPanelRender.drawImage(imageStore.getPVPBtn(true),409,109,null);
			gPanelRender.drawImage(imageStore.getTutorialBtn(false),409,209,null);
			gPanelRender.drawImage(imageStore.getAboutBtn(false),409,309,null);
		}
		// tutorial button render
		else if (sMode.equals("TUTORIAL"))
		{
			gPanelRender.drawImage(imageStore.getPVCBtn(false),409,9,null);
			gPanelRender.drawImage(imageStore.getPVPBtn(false),409,109,null);
			gPanelRender.drawImage(imageStore.getTutorialBtn(true),409,209,null);
			gPanelRender.drawImage(imageStore.getAboutBtn(false),409,309,null);
		}
		// about button render
		else if (sMode.equals("ABOUT"))
		{
			gPanelRender.drawImage(imageStore.getPVCBtn(false),409,9,null);
			gPanelRender.drawImage(imageStore.getPVPBtn(false),409,109,null);
			gPanelRender.drawImage(imageStore.getTutorialBtn(false),409,209,null);
			gPanelRender.drawImage(imageStore.getAboutBtn(true),409,309,null);
		}
		// the panel render during gameplay
		else if (sMode.equals("GAMEPVC") || sMode.equals("GAMEPVP") || sMode.equals("GAMESTARTPVC") || sMode.equals("GAMESTARTPVP"))
		{
			// Player portraits render
			gPanelRender.drawImage(imageStore.getP2Portrait(fGlobalDetails.getPlayerTurn()),400,0,null);
			gPanelRender.drawImage(imageStore.getP1Portrait(fGlobalDetails.getPlayerTurn()),400,250,null);
			// status bar render
			if (fGlobalDetails.mustTake() == true)
				gPanelRender.drawImage(imageStore.getStatusbar(1+statusBarAni.getCurrentFrame()),400,150,null); // Case: Player Must take bead
			else
				gPanelRender.drawImage(imageStore.getStatusbar(0),400,150,null);
			// score counters representing amount of "kills done to other"
			int iP1Score = fGlobalDetails.getP1BeadsCnt();
			gPanelRender.drawImage(imageStore.getP1Counter(),510,260,null);
			gPanelRender.drawImage(imageStore.getNumber(iP1Score),510,260,null);
			// same process for player 2
			int iP2Score = fGlobalDetails.getP2BeadsCnt();
			gPanelRender.drawImage(imageStore.getP2Counter(),510,10,null);
			gPanelRender.drawImage(imageStore.getNumber(iP2Score),510,10,null);
		}
		// End game banners to show winner
		else if (sMode.equals("WIN1")||sMode.equals("WIN2")||sMode.equals("DRAW"))
		{
			gPanelRender.drawImage(imageStore.getMenuBtn(),409,9,null);
			gPanelRender.drawImage(imageStore.getQuitBtn(),409,109,null);
		}	
	}
}