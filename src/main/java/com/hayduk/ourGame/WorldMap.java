package com.hayduk.ourGame;

import java.security.InvalidParameterException;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.newdawn.slick.SlickException;

import com.hayduk.ourGame.Character.facingDirections;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

/***
 * This collection holds all of the tiles for the worldMap.
 *
 * Schema: { "x" : <double>, "y" : <double>, "traversable" : <boolean>,
 * "tileType" : <string> }
 */

public class WorldMap {

	private static MongoCollection<Document> collection;

	public static void preWindowInit() throws SlickException {
		collection = OurGameDatabase.getCollection("worldMap");
		if (Config.getDropCollectionsOnStartup()) {
			collection.drop();
		}
		if (collection.count() == 0) {
			// Collection is blank - recreate it
			Coordinate screenCenter = Screen.getCenter();
			for (int xIndex = -2; xIndex <= 2; xIndex++) {
				for (int yIndex = -2; yIndex <= 2; yIndex++) {
					Vector translation = new Vector(xIndex * Config.getCubitsPerTile(),
							yIndex * Config.getCubitsPerTile());
					Coordinate location = new Coordinate(screenCenter.getX(), screenCenter.getY());
					location.translate(translation);
					Tile tile = new Tile(location, "grass", true);
					insert(tile);
				}
			}
			collection.createIndex(new Document().append("x", 1).append("y", 1));
		}
	}

	/***
	 * Renders all of the visible worldMap tiles on the screen.
	 *
	 * @throws SlickException
	 */
	public static void render() throws SlickException {
		// Find all of the tiles that will be on the screen
		MongoCursor<Document> cursor = collection.find(Screen.filter()).iterator();

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
	}

	private static void spawnNeighbors(Tile tile, facingDirections facingDirection) throws SlickException {
		for (Vector vector : Vector.immediateNeighbors.get(facingDirection)) {
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
		Document insertDocument = new Document().append("x", tile.getLocation().getX())
				.append("y", tile.getLocation().getY()).append("tileType", tile.getTileType())
				.append("walkable", tile.isWalkable());
		collection.insertOne(insertDocument);
	}

	/***
	 * This takes a worldMap document and extracts only those fields required
	 * for drawing a tile
	 */
	private static Tile documentToDrawableTile(Document document) throws SlickException {
		Tile tile = new Tile();
		tile.setLocation(new Coordinate(document.getDouble("x"), document.getDouble("y")));
		tile.setTileType(document.getString("tileType"));
		return (tile);
	}

	/***
	 * This takes a worldMap document and all tile fields
	 */
	private static Tile documentToTile(Document document) throws SlickException {
		Tile tile = documentToDrawableTile(document);
		tile.setWalkable(document.getBoolean("walkable"));
		return (tile);
	}

	/**
	 * Given a direction and location we proposing to move to or are moving to,
	 * return a filter that can be used in a search to find the neighbors
	 * immediately affected.
	 */
	private static Bson immediateNeighborFilter(facingDirections facingDirection, Coordinate newPlayerLocation,
			double searchTileWidth) {
		Bson immediateNeighborFilter = null;
		switch (facingDirection) {
		case UP:
			immediateNeighborFilter = Filters.and(
					Filters.gte("x", newPlayerLocation.getX() - (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.lte("x", newPlayerLocation.getX() + (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.gte("y", newPlayerLocation.getY()),
					Filters.lte("y", newPlayerLocation.getY() + (Config.getCubitsPerTile() * searchTileWidth)));
			break;
		case RIGHT:
			immediateNeighborFilter = Filters.and(Filters.gte("x", newPlayerLocation.getX()),
					Filters.lte("x", newPlayerLocation.getX() + (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.gte("y", newPlayerLocation.getY() - (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.lte("y", newPlayerLocation.getY() + (Config.getCubitsPerTile()) * searchTileWidth));
			break;
		case DOWN:
			immediateNeighborFilter = Filters.and(
					Filters.gte("x", newPlayerLocation.getX() - (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.lte("x", newPlayerLocation.getX() + (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.gte("y", newPlayerLocation.getY() - (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.lte("y", newPlayerLocation.getY()));
			break;
		case LEFT:
			immediateNeighborFilter = Filters.and(
					Filters.gte("x", newPlayerLocation.getX() - (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.lte("x", newPlayerLocation.getX()),
					Filters.gte("y", newPlayerLocation.getY() - (Config.getCubitsPerTile() * searchTileWidth)),
					Filters.lte("y", newPlayerLocation.getY() + (Config.getCubitsPerTile() * searchTileWidth)));
			break;
		default:
			throw new InvalidParameterException("Unknown facingDirection : " + facingDirection.toString());
		}
		return immediateNeighborFilter;
	}

	/**
	 * The only time we need to check if we need to spawn new tiles is if the
	 * player just moved, and then we only need to check in the direction the
	 * player just moved.
	 * 
	 * @param facingDirection
	 *            - The direction the player moved in
	 * @param newPlayerLocation
	 *            - The location the player just moved to
	 * @throws SlickException
	 */
	public static void updateFromPlayerMove(facingDirections facingDirection, Coordinate newPlayerLocation)
			throws SlickException {
		Bson immediateNeighborFilter = WorldMap.immediateNeighborFilter(facingDirection, newPlayerLocation, 1);
		MongoCursor<Document> cursor = collection.find(immediateNeighborFilter).iterator();
		while (cursor.hasNext()) {
			Tile tile = documentToTile(cursor.next());
			spawnNeighbors(tile, facingDirection);
		}
	}

	/**
	 * Determones if there would be a collision from a charcater moving to the
	 * given location, and returns true if there are none and it is OK to move
	 * there, or false otherwise.
	 * 
	 * @param newPlayerLocation
	 *            the proposed new location
	 * @return
	 */
	public static boolean clearToMove(facingDirections facingDirection, Coordinate newPlayerLocation) {
		Bson filter = Filters.and(WorldMap.immediateNeighborFilter(facingDirection, newPlayerLocation, 0.8),
				Filters.eq("walkable", false));
		if (collection.count(filter) == 0) {
			return true;
		} else {
			return false;
		}
	}
}
