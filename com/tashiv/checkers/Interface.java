package com.tashiv.checkers;

// imports
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
//// Class

/**
 * A class that handles both sub-interfaces and is in charge of the main app window. It also is incharge
 * of collecting user mouse input.
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class Interface extends JFrame
{
	//// instance variables
	private Game coreWindow;				// Core Window Sub-Interface
	private SidePanel sideWindow;			// Side Window Sub-Interface
	private GlobalDetails fGlobalDetails;	// used to allow sub interfaces to communicate

	/**
 	* A constructor for initializing the main application interface and is utilised be a
 	* driver program.
 	*/
	public Interface()
	{
		// interface settings
		super("Checkers - Created by Tashiv Sewpersad");		
		setSize(555,427);
		setDefaultCloseOperation(3);
		setResizable(false);
		setLocation(0,0);
		setFocusable(true);
		// Sub-interface Communications setup
		fGlobalDetails = new GlobalDetails();
		coreWindow = new Game(fGlobalDetails);
		add(coreWindow,BorderLayout.CENTER);
		sideWindow = new SidePanel(fGlobalDetails);
		add(sideWindow,BorderLayout.CENTER);
		validate(); // Ensure windows are working
		// Setup input
		MouseEventHandle mouseHandle = new MouseEventHandle(); 
		addMouseListener(mouseHandle);
		KeyEventHandle keyhandle = new KeyEventHandle();
		addKeyListener(keyhandle);
		// activtate window
		setVisible(true);
	}

	/*
	* A class that overrides standard mouse input for collecting mouse input.
	* All user input starts here. 
	*/
	private class MouseEventHandle implements MouseListener
	{
		/**
		* A method that activates when a mouse button is clicked
		*
		* @param theEvent contains hardware mouse related information and is used to find (x,y) of click.
		*/
		public void mouseClicked(MouseEvent theEvent)
		{
			Debugger.report("HardwareEvent: MouseCLick");
		}

		/**
		* A method that activates when a mouse button is Pressed In
		*
		* @param theEvent contains hardware mouse related information and is used to find (x,y) of click.
		*/
		public void mousePressed(MouseEvent theEvent)
		{
			Debugger.report("HardwareEvent: MousePressed");
		}

		/**
		* A method that activates when a mouse button is released and is used
		* to track the user's mouse input which is then interpreted by 
		* either the core window or side window.
		*
		* @param theEvent contains hardware mouse related information and is used to find (x,y) of click.
		*/
		public void mouseReleased(MouseEvent theEvent)
		{
			// Appears to be most reliable mouse event...
			// core window input
			if ((theEvent.getX() <= 400) && (theEvent.getY() <= 425))
			{
				Location placeClicked = new Location(theEvent.getX(),theEvent.getY()-25);
				coreWindow.clickSpace(placeClicked); // sends click to core window for interpretation									
			}
			// side window input
			if ((theEvent.getX() > 400) && (theEvent.getY() > 0))
			{
				int yClick = theEvent.getY() - 25; // Strange Y-axis shift conpensation
				// At end of game play
				if ((fGlobalDetails.getMode().indexOf("WIN") > -1) || (fGlobalDetails.getMode().equals("DRAW")))
				{
					if ((yClick >= 9) && (yClick <= 89))
					{
						coreWindow.setMode("MENUPVC");				// MENU button
					}
					else if ((yClick >= 109) && (yClick <= 189))    // QUIT button
					{
						System.exit(0);
					}
				}
				// During menu time
				else if (fGlobalDetails.getMode().indexOf("GAME") == -1)
				{
					if ((yClick >= 9) && (yClick <= 89)) 			// Player Vs Player Mode
					{
						coreWindow.setMode("MENUPVC");
					}
					else if ((yClick >= 109) && (yClick <= 189)) 	// Player vs AI mode
					{
						coreWindow.setMode("MENUPVP");
					}
					else if ((yClick >= 209) && (yClick <= 289))	// The help window
					{
						coreWindow.setMode("TUTORIAL");
					}
					else if ((yClick >= 309) && (yClick <= 389))	// the creator info window
					{
						coreWindow.setMode("ABOUT");
					}
				}
			}
			else
			{
				Debugger.report("Click out of bounds.");
			}
		}

		/**
		* A method that activates when a mouse cursor enters this main interface
		*
		* @param theEvent contains hardware mouse related information and is used to find (x,y) of click.
		*/
		public void mouseEntered(MouseEvent theEvent)
		{
			Debugger.report("HardwareEvent: MouseEntered");
		}

		/**
		* A method that activates when a mouse cursor exits this main interface
		*
		* @param theEvent contains hardware mouse related information and is used to find (x,y) of click.
		*/
		public void mouseExited(MouseEvent theEvent)
		{
			Debugger.report("HardwareEvent: MouseExited");
		}

	}

	/*
	* A class that overrides standard keyboard input for collecting key press input.
	* This handles two special kinds of user input, namely the Quit Immidiately option
	* and the exit to menu immediately option which aims to improve user flow.
	*/
	private class KeyEventHandle implements KeyListener
	{
		/**
		* A method that activates when a keyboard key is pressed in
		*
		* @param theEvent contains hardware related information and is used to find what key was pressed
		*/
		public void keyPressed(KeyEvent e) 
		{ 
			Debugger.report("HardwareEvent: KeyPressed");
		}
		
		/**
		* A method that activates when a keyboard key is released
		*
		* @param theEvent contains hardware related information and is used to find what key was pressed
		*/
		public void keyReleased(KeyEvent e)
		{ 
			Debugger.report("HardwareEvent: KeyReleased");
			char cKey = e.getKeyChar();
			if (cKey == 'q')
				System.exit(0);
			else if (cKey == 'm')
				coreWindow.setMode("MENUPVC");	

		}

		/**
		* A method that activates when a keyboard key is typeds
		*
		* @param theEvent contains hardware related information and is used to find what key was pressed
		*/
		public void keyTyped(KeyEvent e)
		{ 
			Debugger.report("HardwareEvent: MouseTyoed");
		}
	}
}