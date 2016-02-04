package com.hayduk.ourGame;

import org.newdawn.slick.SlickException;

public class WorldMap {
	
	public static void render() throws SlickException {
		Coordinate screenCenter = Screen.getCenter();
		
		// Find all of the tiles that will be on the screen
		Coordinate location = new Coordinate(screenCenter.getX(), screenCenter.getY());
		Tile tile = new Tile(location);
		
		// Render each tile
		tile.draw();
	}

	public static void update() {
		
	}
}
