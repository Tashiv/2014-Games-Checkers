package com.tashiv.checkers;

/**
 * A class that manages the animations of the core window of the game
 * which includes take-location animations, move-location animations
 * and the taking of a bead animation.
 *
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class CoreAnimationManager
{
	private Animation takeLocation = new Animation("TakeLocator", 1000, 4, true);   // take-location animation tracker
	private Animation moveLocation = new Animation("MoveLocator", 1000, 4, true);	// move-location animation tracker
	private Animation fAboutAnimation = new Animation("MoveLocator", 200, 4, true); // about menu animation
	private static final int MAXANIMATIONS = 10;									// Maximum amount of take-bead animations
	private Animation[] fAnimations = new Animation[MAXANIMATIONS];					// Array of take-bead animations
	private int fAniCount;															// Tracks amount of animations in the the array

	/**
	* A constructor for initiaizing the instance variables of the class
	*/
	public CoreAnimationManager()
	{
		fAniCount = -1;
	}

	/**
	* A method for adding a new Take-bead animation tracker to the class.
	*
	* @param theID an Identifier for the animation in form "x,y,playerID".
	*/
	public void addTakeAnimation(String theID)
	{
		if (fAniCount < MAXANIMATIONS) 									// Case: Still place for more animations 
		{
			fAniCount++;												
			fAnimations[fAniCount] = new Animation(theID,200,4,false);	// my own default values are used here
		}
		else
		{
			Debugger.report("Animations: out of space");
		}
	}

	/**
	* A accessor for reading the current frame number for the about screen animation
	*
	* @return returns an integer representing the current frame for the about menu animation.
	*/
	public int getAboutFrame()
	{
		return fAboutAnimation.getCurrentFrame();
	}

	/**
	* A accessor for seeing how many take-bead animations are being animation.
	*
	* @return returns the number of animations being tracked.
	*/
	public int getAnimationCount()
	{
		return fAniCount;
	}

	/**
	* A method for producing a string array of animation IDs in form "x,y,playerID".
	* for rendering purposes.
	*
	* @return returns a string array containing animation IDs.
	*/
	public String[] getTakeAniFrames()
	{
		if (fAniCount > -1) // Case: There is atleast 1 animation
		{
			String[] theAnimations = new String[fAniCount+1]; // since we return all animations being tracker
			// get all animation IDs
			for (int i = 0; i <= fAniCount; i++)
			{
				theAnimations[i] = fAnimations[i].getID() + "," + fAnimations[i].getCurrentFrame();
			}
			// clean-up completed animations
			for (int j = fAniCount; j > -1; j--)
			{
				if (fAnimations[j].isActive() == false)
				{
					deleteAnimation(j); // removes the finished animation
				}
			}
			return theAnimations;
		}
		else
			return null; // Case: no animations, null works better here vs. ""
	}

	/**
	* A method for deleting a take-bead animation tracker at a supplied position.
	*
	* @param thePosition The index of the take-bead Animation tracker (in the array of them)
	*/
	private void deleteAnimation(int thePosition)
	{
		// delete done by first principles
		for (int i = thePosition; i < fAniCount; i++)
		{
			fAnimations[i] = new Animation(fAnimations[i+1]);
		}
		fAniCount--;
	}

	/**
	* An accessor for getting the current frame of the take-location animation tracker
	* which is used for rendering purposes
	*
	* @return returns the current frame number of the take-locator animation.
	*/
	public int getTakeLocatorFrame()
	{
		return takeLocation.getCurrentFrame();
	}

	/**
	* An accessor for getting the current frame of the move-location animation tracker
	* which is used for rendering purposes
	*
	* @return returns the current frame number of the move-locator animation.
	*/
	public int getMoveLocatorFrame()
	{
		return moveLocation.getCurrentFrame();
	}

}