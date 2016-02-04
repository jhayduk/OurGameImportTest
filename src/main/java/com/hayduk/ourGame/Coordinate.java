package com.hayduk.ourGame;

/***
 * The coordinates of an object in the world.
 * 
 * When passing around positions in the game, only use objects of this class.
 * Any conversions are handled in here.
 */
public class Coordinate {
	
	// The "real" location in space in cubits
	private double x, y;
	
	public Coordinate() {
		x = 0;
		y = 0;
	}

	public Coordinate(double[] coordinates) {
		if (coordinates.length != 2) {
			throw new IllegalArgumentException("coordinates array must a length of exactly 2");
		}
		x = coordinates[0];
		y = coordinates[1];
	}
	
	public Coordinate(double worldX, double worldY) {
		this.x = worldX;
		this.y = worldY;
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
}