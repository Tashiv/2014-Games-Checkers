package com.tashiv.checkers;

//// Class
/**
 * A class for storing a 2D coordinate made up of an
 * X and Y coordinate, which can then be accessed through
 * the appropriate accessors.
 *
 * @author Tashiv Sewpersad 
 * 13 / 09 / 2014
 */
public class Location
{
	//// Instance Variables
	private int fXCoordinate;
	private int fYCoordinate;

	//// Instance Methods
	/**
	* A constructor used to initialize the instance variables,
	* with a provided X and Y Coordinate.
	*
	* @param iXPoint An X coordinate of a 2D plane.
	* @param iYPoint An Y coordinate of a 2D plane.
	*/
	public Location(int iXPoint, int iYPoint)
	{
		fYCoordinate = iYPoint;
		fXCoordinate = iXPoint;
	}

	/**
	* A Accessor for reading the stored X-Coordinate.
	*
	* @return The stored X-Coordinate
	*/
	public int getX()
	{
		return fXCoordinate;
	}

	/**
	* A Accessor for reading the stored Y-Coordinate.
	*
	* @return The stored Y-Coordinate
	*/
	public int getY()
	{
		return fYCoordinate;
	}

	/**
	* A Mutator for setting the X-Coordinate.
	*
	* @param xCoordinate The desired X-Coordinate that will be stored
	*/
	public void setX(int xCoordinate)
	{
		fXCoordinate = xCoordinate;
	}

	/**
	* A Mutator for setting the Y-Coordinate.
	*
	* @param yCoordinate The desired Y-Coordinate that will be stored
	*/
	public void setY(int yCoordinate)
	{
		fYCoordinate = yCoordinate;
	}
}