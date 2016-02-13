package com.hayduk.ourGame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import com.hayduk.ourGame.Character.facingDirections;

/**
 * This class resposible for handling all of the inputs in the game
 */
public class InputHandler {
	public static void update(GameContainer container, int deltaMs) throws SlickException {
		Input input = container.getInput();
		double walkingDistance = Config.getWalkingSpeedCubitsPerMilliSecond() * deltaMs;
		
		if (input.isKeyDown(Input.KEY_UP)) {
			Player.move(facingDirections.UP, walkingDistance);
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			Player.move(facingDirections.DOWN, walkingDistance);
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			Player.move(facingDirections.LEFT, walkingDistance);
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			Player.move(facingDirections.RIGHT, walkingDistance);
		}
	}
}
