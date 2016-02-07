package com.hayduk.ourGame;

/***
 * A Vector is very similar to a Coordinate except that it is meant to
 * denote a two dimensional vector with its origin at 0. A Coordinate
 * can be translated by giving it a vector.
 */
public class Vector extends Coordinate {

	public static final Vector[] IMMEDIATE_NEIGHBOR_VECTORS = {
		new Vector(0, Config.getTileSize()), // top
		new Vector(Config.getTileSize(), Config.getTileSize()), // top right
		new Vector(Config.getTileSize(), 0), // right
		new Vector(Config.getTileSize(), -Config.getTileSize()), // bottom right
		new Vector(0, -Config.getTileSize()), // bottom
		new Vector(-Config.getTileSize(), -Config.getTileSize()), // bottom left
		new Vector(-Config.getTileSize(), 0), // left
		new Vector(-Config.getTileSize(), Config.getTileSize()), // top left
	};
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

}
