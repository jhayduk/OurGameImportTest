package com.hayduk.ourGame;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.newdawn.slick.SlickException;

import com.hayduk.ourGame.Character.facingDirections;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

/**
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
			Character character = new Character(screenCenter, facingDirections.DOWN, "Adam_Pre-Fall", "Adam");
			character.setPlayer(true);
			insert(character);
			collection.createIndex(new Document().append("x", 1).append("y", 1));
			collection.createIndex(new Document().append("name", 1));
		}
	}

	/**
	 * Inserts the character into the collection
	 */
	private static void insert(Character character) {
		Document insertDocument = new Document()
				.append("x", character.getLocation().getX())
				.append("y", character.getLocation().getY())
				.append("characterType", character.getCharacterType())
				.append("facingDirection", character.getFacingDirection().toString())
				.append("player", character.isPlayer())
				.append("name", character.getName());
		collection.insertOne(insertDocument);
	}
	
	/**
	 * Update just the parts of a character that can change when it moves
	 */
	public static void saveAfterMove(Character character) {
		Coordinate newLocation = character.getLocation();
		Bson filter = Filters.eq("name", character.getName());
		Document update = new Document()
				.append("$set", new Document()
						.append("x", newLocation.getX())
						.append("y", newLocation.getY())
						.append("facingDirection", character.getFacingDirection().toString()));
		collection.updateOne(filter, update);
	}

	/**
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

	/**
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

	/**
	 * This takes a characters document and extracts all character fields
	 */
	private static Character documentToCharacter(Document document) throws SlickException {
		Character character = documentToDrawableCharacter(document);
		character.setPlayer(document.getBoolean("player"));
		character.setName(document.getString("name"));
		return (character);
	}

	public static Character getPlayer() throws SlickException {
		Bson filter = Filters.eq("player", true);
		return documentToCharacter(collection.find(filter).limit(1).first());
	}
}
