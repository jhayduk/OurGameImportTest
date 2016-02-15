package com.hayduk.ourGame;

import java.util.ArrayList;
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
		// First we need to get the typical characteristics for the neighboring tile
		TileInfo neighboringTileInfo = new TileInfo(neighboringTile.getTileType());
		Tile newTile;
		if (random.nextDouble() <= neighboringTileInfo.getTileTypeAffinity()) {
			newTile = new Tile(newTileLocation, neighboringTile);
		} else {
			ArrayList<String> neighboringTileTypes = neighboringTileInfo.getNeighboringTileTypes();
			int numChoices = neighboringTileTypes.size();
			newTile = new Tile(newTileLocation, neighboringTileTypes.get(random.nextInt(numChoices)));
		}
		return newTile;
	}
	
	
}
