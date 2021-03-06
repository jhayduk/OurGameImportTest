package com.hayduk.ourGame;

/***
 * The coordinates of an object in the world.
 * 
 * When passing around positions in the game, only use objects of this class.
 * Any conversions are handled in here.
 */
public class Coordinate {
	
	// The "real" location in space in cubits
	protected double x;
	protected double y;
	
	/**
	 * Create a new coordinate at the center of the world
	 */
	public Coordinate() {
		x = 0;
		y = 0;
	}

	/**
	 * Create a new coordinate based on x and y values in the given array
	 * @param coordinates
	 *            An array of two doubles with the x and y values to set the new
	 *            coordinate to
	 */
	public Coordinate(double[] coordinates) {
		if (coordinates.length != 2) {
			throw new IllegalArgumentException("coordinates array must a length of exactly 2");
		}
		x = coordinates[0];
		y = coordinates[1];
	}
	
	/**
	 * Create a new coordinate with the given x and y values
	 * @param x
	 * @param y
	 */
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create a new coordinate as a clone of an existing coordinate
	 * @param origin
	 */
	public Coordinate(Coordinate origin) {
		x = origin.getX();
		y = origin.getY();
	}
	
	/**
	 * Create a coordinate offset from another coordinate
	 * @param origin
	 * @param translation
	 */
	public Coordinate(Coordinate origin, Vector translation) {
		x = origin.getX() + translation.getX();
		y = origin.getY() + translation.getY();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getXScreen() {		
		return (x - Screen.getCenter().getX());
	}

	public double getYScreen() {
		return (y - Screen.getCenter().getY());
	}
	
	public double getXPx() {
//		System.out.println(
//			"getXPx("
//		    + "x : " + x
//			+ ", getXScreen()*pxPerCubit : " + (getXScreen() * Config.getPixelsPerCubit())
//			+ ", Screen.getWidthPx()/2 : " + (Screen.getWidthPx()/2)
//			+ ") -> " + ((Screen.getWidthPx()/2) + (getXScreen() * Config.getPixelsPerCubit())));
		return ((Screen.getWidthPx()/2) + (getXScreen() * Config.getPixelsPerCubit()));
	}

	public double getYPx() {
//		System.out.println(
//				"getYPx("
//			    + "y : " + y
//				+ ", getXScreen()*pxPerCubit : " + (getYScreen() * Config.getPixelsPerCubit())
//				+ ", Screen.getHeightPx()/2 : " + (Screen.getHeightPx()/2)
//				+ ") -> " + ((Screen.getHeightPx()/2) - (getYScreen() * Config.getPixelsPerCubit())));
			return ((Screen.getHeightPx()/2) - (getYScreen() * Config.getPixelsPerCubit()));
	}

	public double getCornerXPx() {
		return (getXPx() -Config.getHalfTileSizePx());
	}

	public double getCornerYPx() {
		return (getYPx() -Config.getHalfTileSizePx());
	}

	/**
	 * Translate (move) this coordinate by the given vector
	 * @param translation
	 */
	public void translate(Vector translation) {
		x += translation.getX();
		y += translation.getY();
	}
	
	@Override
	public String toString() {
		return "Coordinate [x=" + x + ", y=" + y + "]";
	}
}
