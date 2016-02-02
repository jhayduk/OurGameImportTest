package com.hayduk.ourGame;

/***
 * The coordinates of an object in the world.
 */
public class Coordinates {
	
	// The "real" location in space
	private double worldX, worldY;
	
	public Coordinates() {
		worldX = 0;
		worldY = 0;
	}

	public Coordinates(double[] coordinates) {
		if (coordinates.length != 2) {
			throw new IllegalArgumentException("coordinates array must a length of exactly 2");
		}
		worldX = coordinates[0];
		worldY = coordinates[1];
	}
	
	public Coordinates(double worldX, double worldY) {
		this.worldX = worldX;
		this.worldY = worldY;
	}
	
	public double getWorldX() {
		return worldX;
	}
	
	public double getWorldY() {
		return worldY;
	}
	
}
