package com.hayduk.ourGame;

import org.bson.Document;
import org.newdawn.slick.SlickException;

import com.hayduk.ourGame.Character.facingDirections;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

/***
 * This collection contains all of the characters in the game
 *
 * Schema:
 * {
 *		"x" : <double>,
 *		"y" : <double>,
 *		"facingDirection" : <enum>
 *		"characterType" : <string>
 * }
 */
public class Characters {

	private static MongoCollection<Document> collection;
	
	public static void preWindowInit () throws SlickException {
		collection = OurGameDatabase.getCollection("characters");
		if (Config.getDropCollectionsOnStartup()) {
			collection.drop();
		}
		if (collection.count() == 0) {
			// Collection is blank - recreate it
			Coordinate screenCenter = Screen.getCenter();
			Character character = new Character(screenCenter, facingDirections.DOWN, "Male-Mage");
			insert(character);
			collection.createIndex(new Document().append("x", 1).append("y", 1));
		}
	}

	/***
	 * Inserts the character into the collection
	 */
	private static void insert(Character character) {
		Document insertDocument = new Document()
				.append("x", character.getLocation().getX())
				.append("y", character.getLocation().getY())
				.append("characterType", character.getCharacterType())
				.append("facingDirection", character.getFacingDirection().toString());
		collection.insertOne(insertDocument);
	}

	/***
	 * Renders all of the visible characters on the screen.
	 *
	 * @throws SlickException
	 */
	public static void render() throws SlickException {		
		// Find all of the characters that will be on the screen
		MongoCursor<Document> cursor = collection.find(Screen.filter()).iterator();
		
		// Render each tile
		while (cursor.hasNext()) {
			Character character = documentToDrawableCharacter(cursor.next());
			character.draw();
		}
	}

	/***
	 * This takes a characters document and extracts only those fields required
	 * for drawing a character
	 */
	private static Character documentToDrawableCharacter(Document document) throws SlickException {
		Character character = new Character();
		character.setLocation(new Coordinate(document.getDouble("x"), document.getDouble("y")));
		character.setCharacterType(document.getString("characterType"));
		character.setFacingDirection(facingDirections.valueOf(document.getString("facingDirection")));
		return (character);
	}
}
