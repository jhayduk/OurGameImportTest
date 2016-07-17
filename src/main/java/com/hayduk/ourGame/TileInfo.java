package com.hayduk.ourGame;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/***
 * This class is responsible for all of the general information regarding
 * the different types of tiles.   
 */
public class TileInfo {

	private static MongoCollection<Document> collection;

	private String tileType;
	private String resourceLocation;
	private boolean walkable;
	private double tileTypeAffinity;
	private ArrayList<String> neighboringTileTypes;

	//
	// Retrieve tileInfo from the collection. If there is no entry, we
	// will just let the null pointer exception bubble back.
	//
	@SuppressWarnings("unchecked")
	public TileInfo(String tileType) {
		Bson filter = Filters.eq("tileType", tileType);
		Document document = collection.find(filter).limit(1).first();
		this.tileType = tileType;
		resourceLocation = document.getString("resourceLocation");
		walkable = document.getBoolean("walkable");
		tileTypeAffinity = document.getDouble("tileTypeAffinity");
		neighboringTileTypes = (ArrayList<String>)document.get("neighboringTileTypes");
	}

	//
	// Create a brand new tileInfo. The intent is that the caller is
	// probably getting ready to insert this in the collection (possibly
	// after some further modification).
	//
	private TileInfo(String tileType, String resourceLocation, boolean walkable) {
		this.tileType = tileType;
		this.resourceLocation = resourceLocation;
		this.walkable = walkable;
		this.tileTypeAffinity = Config.getDefaultTileTypeAffinity();
	}
	
	public String getTileType() {
		return tileType;
	}
	
	public String getResourceLocation() {
		return resourceLocation;
	}
	
	public boolean isWalkable() {
		return walkable;
	}

	public double getTileTypeAffinity() {
		return tileTypeAffinity;
	}

	public void setTileTypeAffinity(double tileTypeAffinity) {
		this.tileTypeAffinity = tileTypeAffinity;
	}

	public ArrayList<String> getNeighboringTileTypes() {
		return neighboringTileTypes;
	}

	public void setNeighboringTileTypes(ArrayList<String> neighboringTileTypes) {
		this.neighboringTileTypes = neighboringTileTypes;
	}

	public static void preWindowInit() {
		collection = OurGameDatabase.getCollection("tileInfo");
		if (Config.getDropCollectionsOnStartup()) {
			collection.drop();
		}
		if (collection.count() == 0) {
			// Collection is blank - recreate it
			TileInfo tileInfo;
			ArrayList<String> neighboringTileTypes;
			
			tileInfo = new TileInfo("grass", "src/images/grass.png", true);
			neighboringTileTypes = new ArrayList<String>();
			neighboringTileTypes.add("sand");
			neighboringTileTypes.add("stone");
			tileInfo.setNeighboringTileTypes(neighboringTileTypes);
			insert(tileInfo);
			
			tileInfo = new TileInfo("ocean", "src/images/ocean.png", false);
			neighboringTileTypes = new ArrayList<String>();
			neighboringTileTypes.add("sand");
			tileInfo.setNeighboringTileTypes(neighboringTileTypes);
			tileInfo.setTileTypeAffinity(0.999);
			insert(tileInfo);
			
			tileInfo = new TileInfo("sand", "src/images/sand.png", true);
			neighboringTileTypes = new ArrayList<String>();
			neighboringTileTypes.add("ocean");
			neighboringTileTypes.add("grass");
			tileInfo.setNeighboringTileTypes(neighboringTileTypes);
			insert(tileInfo);
			
			tileInfo = new TileInfo("stone", "src/images/stone.png", false);
			neighboringTileTypes = new ArrayList<String>();
			neighboringTileTypes.add("grass");
			tileInfo.setNeighboringTileTypes(neighboringTileTypes);
			insert(tileInfo);
			
			tileInfo = new TileInfo("tomato", "src/images/tomato32.png", false);
			neighboringTileTypes = new ArrayList<String>();
			tileInfo.setNeighboringTileTypes(neighboringTileTypes);
			insert(tileInfo);
			
			tileInfo = new TileInfo("tomato (ripe)", "src/images/tomato32ripe.png", false);
			neighboringTileTypes = new ArrayList<String>();
			tileInfo.setNeighboringTileTypes(neighboringTileTypes);
			insert(tileInfo);
			
			collection.createIndex(new Document("tileType", 1));
		}
	}
	
	private static void insert(TileInfo tileInfo) {
		Document insertDocument = new Document()
				.append("tileType", tileInfo.getTileType())
				.append("resourceLocation", tileInfo.getResourceLocation())
				.append("walkable", tileInfo.isWalkable())
				.append("tileTypeAffinity", tileInfo.getTileTypeAffinity())
				.append("neighboringTileTypes", tileInfo.neighboringTileTypes);
		collection.insertOne(insertDocument);
	}

	@Override
	public String toString() {
		return "TileInfo [tileType=" + tileType + ", resourceLocation=" + resourceLocation + ", walkable=" + walkable
				+ ", tileTypeAffinity=" + tileTypeAffinity + ", neighboringTileTypes=" + neighboringTileTypes + "]";
	}

}
