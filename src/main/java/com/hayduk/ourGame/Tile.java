package com.hayduk.ourGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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

	@Override
	public String toString() {
		return "Tile [location=" + location + ", tileType=" + tileType + ", walkable=" + walkable + "]";
	}

	public void draw() throws SlickException {
		Image image = TileImage.get(tileType);
		image.draw((float)location.getXPx()-Config.getHalfTileSizePx(),
				(float)location.getYPx()-Config.getHalfTileSizePx());
	}
}
