package com.hayduk.ourGame;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class OurGameDatabase {
	
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	
	public static MongoDatabase getDatabase() {
		if (database == null) {
			mongoClient = new MongoClient("localhost" , 27017 );
			database = mongoClient.getDatabase("ourGame");			
		}
		return database;
	}
	
	public static MongoCollection<Document> getCollection(String collectionName) {
		return getDatabase().getCollection(collectionName);
	}
}
