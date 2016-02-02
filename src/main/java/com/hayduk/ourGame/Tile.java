package com.hayduk.ourGame;

public class Tile {
	
	private Coordinates location = new Coordinates();
	// need image pointer
	private boolean traversable = true;

	public Coordinates getLocation() {
		return location;
	}

	public boolean isTraversable () {
		return traversable;
	}
}
