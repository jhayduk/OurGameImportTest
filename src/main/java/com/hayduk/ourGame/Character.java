package com.hayduk.ourGame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/***
 * A single character in the game. One character is marked as the
 * player.
 */
public class Character {
	
	public enum facingDirections {
		DOWN, LEFT, RIGHT, UP 
	}

	private Coordinate location;
	private facingDirections facingDirection;
	private String characterType;
	private boolean player;
	
	public Character() {
	}

	public Character(Coordinate location, facingDirections facingDirection, String characterType) {
		this.location = location;
		this.facingDirection = facingDirection;
		this.characterType = characterType;
		this.player = false;
	}

	public Coordinate getLocation() {
		return location;
	}
	public void setLocation(Coordinate location) {
		this.location = location;
	}
	public facingDirections getFacingDirection() {
		return facingDirection;
	}
	public void setFacingDirection(facingDirections facingDirection) {
		this.facingDirection = facingDirection;
	}
	public String getCharacterType() {
		return characterType;
	}
	public void setCharacterType(String characterType) {
		this.characterType = characterType;
	}
	public boolean isPlayer() {
		return player;
	}
	public void setPlayer(boolean player) {
		this.player = player;
	}
	
	public void draw() throws SlickException {
		Image image = CharacterImage.get(characterType, location, facingDirection);
		image.draw((float)location.getCornerXPx(), (float)location.getCornerYPx());
	}

	@Override
	public String toString() {
		return "Character [location=" + location + ", facingDirection=" + facingDirection + ", characterType="
				+ characterType + ", player=" + player + "]";
	}
}
