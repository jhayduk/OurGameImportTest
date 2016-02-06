package com.hayduk.ourGame;

/***
 * Global config settings for the entire application
 * 
 * Notes: 
 * - World units are cubits. 
 * - Any unit that does not specify its type, is considered to be in cubits 
 * - All work with coordinates should be in cubits except where needed to be
 * translated and then only where that translation is necessary
 * - All items in the world are referenced by their center
 */
public class Config {

	//
	// Tiles are square. The width is the length of one side.
	//
	// in cubits
	public static double getTileWidth() { return (2.0); }
	// in pixels
	public static int getTileWidthPx() { return (32); }
	public static int getTileHalfWidthPx() { return (getTileWidthPx()/2); }
	
	//
	// Calculated values
	//
	public static double getPixelsPerCubit() { 
		return (getTileWidthPx()/getTileWidth());
	}
	public static double getCubitsPerPixel() {
		return (1/getPixelsPerCubit());
	}
}
