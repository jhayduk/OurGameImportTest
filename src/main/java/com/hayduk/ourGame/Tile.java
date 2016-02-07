package com.hayduk.ourGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile {
	
	private Coordinate location = new Coordinate(0,0);
	private String tileType;
	private boolean walkable = true;
	
	// The likelihood that a tile spawned as a neighbor to this tile
	// will be of the same type
	private double tileTypeAffinity = Config.getTileAffinity();
	
	public Tile() {
		
	}

	public Tile(Coordinate location) throws SlickException {
		this.location = location;
		this.tileType = "grass";
	}
	
	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}

	public boolean isWalkable () {
		return walkable;
	}
	
	public void setWalkable(boolean traversable) {
		this.walkable = traversable;
	}

	public String getTileType() {
		return(tileType);
	}
	
	public void setTileType(String tileType) {
		this.tileType = tileType;
	}

	public double getTileTypeAffinity() {
		return tileTypeAffinity;
	}

	public void setTileTypeAffinity(int tileTypeAffinity) {
		this.tileTypeAffinity = tileTypeAffinity;
	}

	@Override
	public String toString() {
		return "Tile [location=" + location + ", tileType=" + tileType + ", walkable=" + walkable
				+ ", tileTypeAffinity=" + tileTypeAffinity + "]";
	}

	public void draw() throws SlickException {
		Image image = TileImage.get(tileType);
		image.draw((float)location.getXPx()-Config.getHalfTileSizePx(),
				(float)location.getYPx()-Config.getHalfTileSizePx());
	}
}
