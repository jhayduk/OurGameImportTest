package com.hayduk.ourGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/***
 * A single map tile in the game.
 */
public class Tile {
	
	private Coordinate location = new Coordinate(0,0);
	private String tileType;
	private boolean walkable;
	
	public Tile() {
	}

	public Tile(Coordinate location, String tileType, boolean walkable) throws SlickException {
		this.location = location;
		this.tileType = tileType;
		this.walkable = walkable;
	}
	
	public Tile(Coordinate location, String tileType) throws SlickException {
		this.location = location;
		this.tileType = tileType;
		TileInfo tileInfo = new TileInfo(tileType);
		this.walkable = tileInfo.isWalkable();
	}
	
	//
	// Clone a tile to a given location
	//
	public Tile(Coordinate location, Tile tileToClone) {
		this.location = location;
		this.tileType = tileToClone.getTileType();
		this.walkable = tileToClone.isWalkable();
	}
	
	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}

	public String getTileType() {
		return(tileType);
	}
	
	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public void draw() throws SlickException {
		Image image = TileImage.get(tileType);
		image.draw((float)location.getCornerXPx(), (float)location.getCornerYPx());
	}

	@Override
	public String toString() {
		return "Tile [location=" + location + ", tileType=" + tileType + ", walkable=" + walkable + "]";
	}

}
