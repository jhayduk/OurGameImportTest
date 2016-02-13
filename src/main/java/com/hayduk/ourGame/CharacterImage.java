package com.hayduk.ourGame;

import java.security.InvalidParameterException;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/***
 * This class is responsible for managing the images for the different
 * character types.
 */
public class CharacterImage {

	private static HashMap<String, SpriteSheet> spriteSheetCache = new HashMap<String, SpriteSheet>();
	
	/***
	 * Returns the image associated with the named tileType
	 * @throws SlickException 
	 */
	public static Image get(String characterType, Coordinate location, Character.facingDirections facingDirection) throws SlickException {
		int spriteXIndex;
		int spriteYIndex;
		switch (facingDirection) {
			case DOWN:
				spriteYIndex = 0;
				spriteXIndex = spriteIndex(location.getY());
				break;
			case LEFT:
				spriteYIndex = 1;
				spriteXIndex = spriteIndex(location.getX());
				break;
			case RIGHT:
				spriteYIndex = 2;
				spriteXIndex = spriteIndex(location.getX());
				break;
			case UP:
				spriteYIndex = 3;
				spriteXIndex = spriteIndex(location.getY());
				break;
			default:
				throw new InvalidParameterException("Unknown facingDirection: " + facingDirection);
		}
		
		SpriteSheet spriteSheet = spriteSheetCache.get(characterType);
		if (spriteSheet == null) {
			CharacterInfo characterInfo = new CharacterInfo(characterType);
			String imageFilename = characterInfo.getResourceLocation();
			System.out.println("Loading " + imageFilename + "...");
			spriteSheet = new SpriteSheet(imageFilename, Config.getTileSizePx(), Config.getTileSizePx());
			spriteSheetCache.put(characterType, spriteSheet);
		}
		Image image = spriteSheet.getSprite(spriteXIndex, spriteYIndex);
		return (image);
	}

	// Given a single coordinate value calculate the sprite index
	// in the direction given.
	private static int spriteIndex(double v) {
		int index;
		if (v >= 0) {
			index = (int)((((v+1.0) % 2.0) / 2.0) * 3.0);
		} else {
			index = 2-(int)((((Math.abs(v)+1.0) % 2.0) / 2.0) * 3.0);				
		}
		return index;

	}
}
