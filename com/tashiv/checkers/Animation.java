package com.tashiv.checkers;

//// Class
/**
 * A class for tracking the animation of a given image set with relation
 * to the timer running the rendering portion of the game. This allows for
 * more easier animations by simply tracking a frame number with regards
 * to time.
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class Animation
{
	//// Instance Variables
	private String fID;					// A ID for tracking the animation
	private int fTimeFrame;				// The length of time for the whole animation in mS
	private int fDelayThreshold;		// A Value representing the time between each frame in mS
	private int fTimePassed;		    // The amount of time since animation started
	private int fCurrentFrame;			// The current image in the animation image set
	private int fTotalFrames;			// Amount of images in image set
	private boolean fActive;	        // is the animation running
	private boolean fLoopAround;		// must it restart at end - for repeating animations {vs one time animations}

	//// Instance Methods
	/**
	* A constructor for initializing a class to a user's specification.
	*
	* @param theID An identifier the animation 
	* @param theTimeFrame The length of time for an animation image set cycle - How long the animation runs for per cycle
	* @param theNrofFrames The number of images in the Animation image set
	* @param loopAround incation of looping nature of animation (eg [true] => loops around) 
	*/
	public Animation(String theID, int theTimeFrame, int theNrofFrames, boolean loopAround)
	{
		// initialize instance variables
		fTimePassed = 0;
		fCurrentFrame = 0;
		// assign formal parameters
		fID = theID;
		fTimeFrame = theTimeFrame;
		fTotalFrames = theNrofFrames;
		fLoopAround = loopAround;
		// calculate delay per frame w.r.t Timer running Rendering the game
		double rDelayThreshold = (1.0/fTotalFrames) * fTimeFrame;
		fDelayThreshold = (int) rDelayThreshold;
		// Activate Animation
		fActive = true;
	}

	/**
	* A copy constructor used to create a clone of the provided animation object
	*
	* @param oldAnimation The animation object to be cloned
	*/
	public Animation(Animation oldAnimation)
	{
		// initialize instance variables
		fID = oldAnimation.getID();
		fTimeFrame = oldAnimation.getTimeFrame();
		fTotalFrames = oldAnimation.getTotalFrames();
		fLoopAround = oldAnimation.getLoopStatus(); 
		fTimePassed = oldAnimation.getTimePassed();
		fCurrentFrame = oldAnimation.getCurrentFrameUntamperd(); // prevents accidental frame iteration 
		// Calculate frame iteration delay
		fDelayThreshold = (int) (1/fTotalFrames) * fTimeFrame;
		// Activate animation
		fActive = oldAnimation.isActive();
	}

	/**
	* An accessor for reading the current animation frame without 
	* automatically iterating it. (Used when cloning an animation only)
	*
	* @return returns the current frame of the animation.
	*/
	public int getCurrentFrameUntamperd()
	{
		return fCurrentFrame;
	}

	/**
	* An accessor for reading the the total amount of frames in the animation.
	*
	* @return fTotalFrames returns the total frames of the animation.
	*/
	public int getTotalFrames()
	{
		return fTotalFrames;
	}

	/**
	* An accessor for reading the the total amount of time passesd in the animation cycle.
	*
	* @return returns how long the animation has been running in the current cycle in mS.
	*/
	public int getTimePassed()
	{
		return fTimePassed;
	}

	/**
	* An accessor for reading the the total amount of time the animation runs for per cycle.
	*
	* @return returns the life cycle time of the animation in mS.
	*/
	public int getTimeFrame()
	{
		return fTimeFrame;
	} 

	/**
	* An accessor for reading if the animation loops
	*
	* @return returns boolean representing if the animation loops
	*/
	public boolean getLoopStatus()
	{
		return fLoopAround;
	}

	/**
	* An accessor for reading the animation's ID
	*
	* @return returns Animation Identifier
	*/
	public String getID()
	{
		return fID;
	}

	/**
	* An accessor for reading the current animation frame while 
	* automatically iterating it. (Used in tracking animation)
	*
	* @return returns the current frame of the animation.
	*/
	public int getCurrentFrame()
	{
		if (fActive == true) 					//case: if the animation is still running
		{
			int currentFrame = fCurrentFrame;	// insures uniterated frame number is returned
			incDelay();
			return currentFrame;
		}
		return 0;
	}

	/**
	* An accessor for checking if an animation is active
	*
	* @return returns boolean representing if the animation is still active
	*/
	public boolean isActive()
	{
		return fActive;
	}

	/*
	* Method for incrementing the time and comparing it to the animation delay
	* for the purpose of iterating animation frames relative to the Timer running
	* the game.
	*/
	private void incDelay()
	{
		// update time passed
		fTimePassed = fTimePassed + Game.GAMETICKINTERVAL;
		// check delay
		if (fTimePassed >= fDelayThreshold) // Case: enough time passed for next animation frame
		{
			fCurrentFrame++;
			fTimePassed = 0;
		}
		// checks for end of animation frames
		if (fCurrentFrame >= fTotalFrames)
		{
			if (fLoopAround == false)			// Case: Animation is finished
				fActive = false;
			else								// Case: Animation Loops, so restart it
				fCurrentFrame = 0;

		}
	}

}