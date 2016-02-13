package com.hayduk.ourGame;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/***
 * Generic information about the different types of characters available
 */
public class CharacterInfo {

	private static MongoCollection<Document> collection;

	private String characterType;
	private String resourceLocation;

	//
	// Retrieve characterInfo from the collection. If there is no entry, we
	// will just let the null pointer exception bubble back.
	//
	public CharacterInfo(String characterType) {
		Bson filter = Filters.eq("characterType", characterType);
		Document document = collection.find(filter).limit(1).first();
		this.characterType = characterType;
		resourceLocation = document.getString("resourceLocation");
	}

	//
	// Create a brand new tileInfo. The intent is that the caller is
	// probably getting ready to insert this in the collection (possibly
	// after some further modification).
	//
	public CharacterInfo(String characterType, String resourceLocation) {
		this.characterType = characterType;
		this.resourceLocation = resourceLocation;
	}

	public String getCharacterType() {
		return characterType;
	}

	public void setCharacterType(String characterType) {
		this.characterType = characterType;
	}

	public String getResourceLocation() {
		return resourceLocation;
	}

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	public static void preWindowInit() {
		collection = OurGameDatabase.getCollection("characterInfo");
		if (Config.getDropCollectionsOnStartup()) {
			collection.drop();
		}
		if (collection.count() == 0) {
			// Collection is blank - recreate it
			CharacterInfo characterInfo;
			
			characterInfo = new CharacterInfo("Male-Mage", "src/images/Male-Mage.png");
			insert(characterInfo);
			
			collection.createIndex(new Document("characterType", 1));
		}
	}
	
	private static void insert(CharacterInfo characterInfo) {
		Document insertDocument = new Document()
				.append("characterType", characterInfo.getCharacterType())
				.append("resourceLocation", characterInfo.getResourceLocation());
		collection.insertOne(insertDocument);
	}

	@Override
	public String toString() {
		return "CharacterInfo [characterType=" + characterType + ", resourceLocation=" + resourceLocation + "]";
	}
}
