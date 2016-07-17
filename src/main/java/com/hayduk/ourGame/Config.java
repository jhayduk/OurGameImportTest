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

	// Normally false, if true, collections are dropped on startup
	public static boolean getDropCollectionsOnStartup() {
		return true;
	}
	
	//
	// Tiles are square.
	//
	// in cubits
	public static double getTileSize() { return (2.0); }
	public static double getCubitsPerTile() { return getTileSize(); }
	// in pixels
	public static int getTileSizePx() { return (32); }
	public static int getHalfTileSizePx() { return (getTileSizePx()/2); }

	//
	// The probability that a newly spawned tile will be the same type as
	// the neighbor from which it spawned
	//
	public static double getDefaultTileTypeAffinity() { return 0.95; }
	
	//
	// When walking, the speed a character has
	//
	public static double getWalkingSpeedTilesPerSecond() { return 2.0; }
	public static double getWalkingSpeedCubitsPerMilliSecond() {
		return (getWalkingSpeedTilesPerSecond() * getCubitsPerTile() * 0.001);
	}
	
	//
	// The various game times in the game
	//
	// "normal" time takes 100 real hrs to go 6000 game years
	//
	public static final double NORMAL_GAME_MS_PER_UNIX_MS =  (6000.0 * GameTime.MS_PER_SOLAR_YEAR) / (100.0 * 3600000.0);
	public static final double REAL_TIME_GAME_MS_PER_UNIX_MS = 1.0;
	
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
