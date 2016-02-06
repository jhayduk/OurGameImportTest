package com.hayduk.ourGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile {
	
	private Coordinate location = new Coordinate(0,0);
	private Image image;
	private boolean traversable = true;

	public Tile() {
		
	}

	public Tile(Coordinate location) throws SlickException {
		this.location = location;
		image = new Image("/Users/jhayduk/Workspace-personal/OurGame/src/images/grass.png");
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(Coordinate location) {
		this.location = location;
	}

	public boolean isTraversable () {
		return traversable;
	}
	
	public void setTraversable(boolean traversable) {
		this.traversable = traversable;
	}

	public String getType() {
		return("/Users/jhayduk/Workspace-personal/OurGame/src/images/grass.png");
	}

	public void draw() {
		image.draw((float)location.getXPx()-Config.getTileHalfWidthPx(),
				(float)location.getYPx()-Config.getTileHalfWidthPx());
	}
}
