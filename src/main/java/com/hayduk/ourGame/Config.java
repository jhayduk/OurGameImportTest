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
	// Tiles are square.
	//
	// in cubits
	public static double getTileSize() { return (2.0); }
	// in pixels
	public static int getTileSizePx() { return (32); }
	public static int getHalfTileSizePx() { return (getTileSizePx()/2); }
	
	//
	// Calculated values
	//
	public static double getPixelsPerCubit() { 
		return (getTileSizePx()/getTileSize());
	}
	public static double getCubitsPerPixel() {
		return (1/getPixelsPerCubit());
	}
}
