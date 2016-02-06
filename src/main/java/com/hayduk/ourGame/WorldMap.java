package com.hayduk.ourGame;

import org.bson.Document;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

//
// Schema for the worldMap collection:
// {
//		"x" : <double>,
//		"y" : <double>,
//		"traversable" : <boolean>,
//		"tileTye" : <string>
// }
//
public class WorldMap {
	
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private static MongoCollection<Document> collection;

	public static void init () throws SlickException {
		mongoClient = new MongoClient( "localhost" , 27017 );
		database = mongoClient.getDatabase("ourGame");
		collection = database.getCollection("worldMap");
		//-v TODO Take this out!
		collection.drop();
		//-^ TODO Take this out!
		collection.createIndex(new Document().append("x", 1).append("y", 1));

		//
		// A special case, but if the collection is empty, spawn one tile
		// at the origin
		//
		if (collection.find().limit(1).first() == null) {
			Coordinate screenCenter = Screen.getCenter();

			Tile tile = new Tile(new Coordinate(screenCenter.getX(), screenCenter.getY()));
			insert(tile);
		}
	}

	public static void render() throws SlickException {		
		// Find all of the tiles that will be on the screen
		Document query = new Document();
		MongoCursor<Document> cursor = collection.find(query).iterator();
		
		// Render each tile
		while (cursor.hasNext()) {
			Tile tile = documentToTile(cursor.next());
			tile.draw();
		}
	}

	//
	// For WorldMap, update() needs to spawn any new tiles that are required.
	//
	public static void update() {
		
	}
	
	private static void insert(Tile tile) {
		Document insertDocument = new Document()
				.append("x", tile.getLocation().getX())
				.append("y", tile.getLocation().getX())
				.append("traversable", tile.isTraversable())
				.append("tileType", tile.getType());
		collection.insertOne(insertDocument);
	}
	
	private static Tile documentToTile(Document document) throws SlickException {
		Tile tile = new Tile();
		tile.setLocation(new Coordinate(document.getDouble("x"), document.getDouble("y")));
		tile.setImage(new Image(document.getString("tileType")));
		tile.setTraversable(document.getBoolean("traversable"));
		return (tile);
	}
}
