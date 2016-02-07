package com.hayduk.ourGame;

import java.util.Random;

import org.newdawn.slick.SlickException;

/***
 * Helper class responsible for spawning new tiles
 */
public class TileSpawner {

	private static Random random = new Random();

	//
	// Given a neighboringTile, create a new tile at the given coordinates
	public static Tile spawn(Tile neighboringTile, Coordinate newTileLocation) throws SlickException {
		Tile newTile = new Tile(newTileLocation);
		// TODO Auto-generated method stub
		return newTile;
	}
	
	
}
