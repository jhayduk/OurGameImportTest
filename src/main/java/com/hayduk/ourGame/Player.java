package com.hayduk.ourGame;

import org.newdawn.slick.SlickException;

import com.hayduk.ourGame.Character.facingDirections;

/**
 * This is the player's current character
 */
public class Player {
	private static Character player = null;
	
	protected Player() {
	}
	
	public static Character get() throws SlickException {
		player = Characters.getPlayer();
		return player;
	}

	public static void move(facingDirections facingDirection, double distance) throws SlickException {
		player = Player.get();
		player.setFacingDirection(facingDirection);
		Vector translationVector;
		switch (facingDirection) {
		case UP:
			translationVector = new Vector(0,distance);
			break;
		case DOWN:
			translationVector = new Vector(0,-distance);
			break;
		case LEFT:
			translationVector = new Vector(-distance,0);
			break;
		case RIGHT:
			translationVector = new Vector(distance,0);
			break;
		default:
			// Don't move if we don't understand the direction
			System.err.println("Unknown move direction: " + facingDirection);
			translationVector = new Vector(0,0);
			break;
		}
		Coordinate newPlayerLocation = player.getLocation();
		newPlayerLocation.translate(translationVector);
		player.setLocation(newPlayerLocation);
		Characters.saveAfterMove(player);
		Screen.setCenter(newPlayerLocation);
		WorldMap.updateFromPlayerMove(facingDirection, newPlayerLocation);
	}
}