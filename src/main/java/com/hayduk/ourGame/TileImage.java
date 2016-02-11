package com.hayduk.ourGame;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/***
 * This class is responsible for managing the images for the different
 * tile types.
 */
public class TileImage {
	private static HashMap<String, Image> imageCache = new HashMap<String, Image>();
	
	/***
	 * Returns the image associated with the named tileType
	 * @throws SlickException 
	 */
	public static Image get(String tileType) throws SlickException {
		Image image;
		image = imageCache.get(tileType);
		if (image == null) {
			TileInfo tileInfo = new TileInfo(tileType);
			String imageFilename = tileInfo.getResourceLocation();
			System.out.println("Loading " + imageFilename + "...");
			image = new Image(imageFilename);
			imageCache.put(tileType, image);
		}
		return (image);
	}
}
