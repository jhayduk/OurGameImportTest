package com.hayduk.ourGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile {
	
	private Coordinate location = new Coordinate();
	private Image image;
	private boolean traversable = true;

	public Tile(Coordinate location) throws SlickException {
		this.location = location;
		image = new Image("/Users/jhayduk/Workspace-personal/OurGame/src/images/grass.png");
	}
	
	public Coordinate getLocation() {
		return location;
	}

	public boolean isTraversable () {
		return traversable;
	}

	public void draw() {
		image.draw((float)location.getXPx()-Config.getTileHalfWidthPx(),
				(float)location.getYPx()-Config.getTileHalfWidthPx());
	}
}
