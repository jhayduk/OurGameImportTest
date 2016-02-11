package com.hayduk.ourGame;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.newdawn.slick.SlickException;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

//
// Schema for the worldMap collection:
// {
//		"x" : <double>,
//		"y" : <double>,
//		"traversable" : <boolean>,
//		"tileType" : <string>
// }
//
public class WorldMap {
	
	private static MongoCollection<Document> collection;
	
	public static void preWindowInit () throws SlickException {
		collection = OurGameDatabase.getCollection("worldMap");
		if (Config.getDropCollectionsOnStartup()) {
			collection.drop();
		}
		if (collection.count() == 0) {
			// Collection is blank - recreate it
			Coordinate screenCenter = Screen.getCenter();
			Tile tile = new Tile(new Coordinate(screenCenter.getX(), screenCenter.getY()), "grass", true);
			insert(tile);
			collection.createIndex(new Document().append("x", 1).append("y", 1));
		}
	}

	public static void render() throws SlickException {		
		// Find all of the tiles that will be on the screen
		Bson filter = Filters.and(
				Filters.gte("x", Screen.getMinX()),
				Filters.lte("x", Screen.getMaxX()),
				Filters.gte("y", Screen.getMinY()),
				Filters.lte("y", Screen.getMaxY()));
		MongoCursor<Document> cursor = collection.find(filter).iterator();
		
		// Render each tile
		while (cursor.hasNext()) {
			Tile tile = documentToDrawableTile(cursor.next());
			tile.draw();
		}
	}

	//
	// For WorldMap, update() needs to spawn any new tiles that are required.
	//
	public static void update() throws SlickException {
		Bson filter = Filters.and(
				Filters.gte("x", Screen.getMinX()),
				Filters.lte("x", Screen.getMaxX()),
				Filters.gte("y", Screen.getMinY()),
				Filters.lte("y", Screen.getMaxY()));
		long count = collection.count(filter);
		if (count < Screen.getNumberOfTilesPerScreen()) {
			// TODO - This can be improved
			// In general, we should only have to spawn new tiles along
			// the edge as the player moves to new areas on the map.
			// A special case is if the map is just being drawn for the
			// first time.
			// For now, though, we visit each of the currently matching
			// tiles, and then check and spawn a neighbor for each one
			// in all directions. For the new map case, this will take
			// a few iterations to get the whole map done and will always
			// result in extra spawns just off of the screen.
			MongoCursor<Document> cursor = collection.find(filter).iterator();
			while (cursor.hasNext()) {
				Tile tile = documentToTile(cursor.next());
				spawnNeighbors(tile);
			}
		}
	}
	
	private static void spawnNeighbors(Tile tile) throws SlickException {
		for (Vector vector : Vector.IMMEDIATE_NEIGHBOR_VECTORS) {
			Coordinate newTileLocation = new Coordinate(tile.getLocation(), vector);
			if (!locationHasTile(newTileLocation)) {
				Tile newTile = TileSpawner.spawn(tile, newTileLocation);
				insert(newTile);
			}
		}
	}

	// Checks if a location on the map has a tile centered on it
	private static boolean locationHasTile(Coordinate location) {
		Bson filter = Filters.and(Filters.eq("x", location.getX()), Filters.eq("y", location.getY()));
		return (collection.count(filter) != 0);
	}

	private static void insert(Tile tile) {
		Document insertDocument = new Document()
				.append("x", tile.getLocation().getX())
				.append("y", tile.getLocation().getY())
				.append("tileType", tile.getTileType())
				.append("walkable", tile.isWalkable());
		collection.insertOne(insertDocument);
	}

	//
	// This only extracts fields required for drawing a tile
	//
	private static Tile documentToDrawableTile(Document document) throws SlickException {
		Tile tile = new Tile();
		tile.setLocation(new Coordinate(document.getDouble("x"), document.getDouble("y")));
		tile.setTileType(document.getString("tileType"));
		return (tile);
	}

	//
	// This extracts all of the fields
	//
	private static Tile documentToTile(Document document) throws SlickException {
		Tile tile = documentToDrawableTile(document);
		tile.setWalkable(document.getBoolean("walkable"));
		return (tile);
	}
}
