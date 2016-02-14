package com.hayduk.ourGame;

import java.util.EnumMap;

import com.hayduk.ourGame.Character.facingDirections;

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
	
	private static final Vector[] IMMEDIATE_UP_NEIGHBORS = {
		new Vector(-Config.getTileSize(), Config.getTileSize()),
		new Vector(0, Config.getTileSize()),
		new Vector(Config.getTileSize(), Config.getTileSize()),
	};
	
	private static final Vector[] NEXT_UP_NEIGHBORS = {
			new Vector(-Config.getTileSize(), Config.getTileSize()*2),
			new Vector(0, Config.getTileSize()*2),
			new Vector(Config.getTileSize(), Config.getTileSize()*2),
		};
		
	private static final Vector[] IMMEDIATE_RIGHT_NEIGHBORS = {
			new Vector(Config.getTileSize(), Config.getTileSize()),
			new Vector(Config.getTileSize(), 0),
			new Vector(Config.getTileSize(), -Config.getTileSize()),
		};
		
	private static final Vector[] NEXT_RIGHT_NEIGHBORS = {
			new Vector(Config.getTileSize()*2, Config.getTileSize()),
			new Vector(Config.getTileSize()*2, 0),
			new Vector(Config.getTileSize()*2, -Config.getTileSize()),
		};
		
	private static final Vector[] IMMEDIATE_DOWN_NEIGHBORS = {
			new Vector(-Config.getTileSize(), -Config.getTileSize()),
			new Vector(0, -Config.getTileSize()),
			new Vector(Config.getTileSize(), -Config.getTileSize()),
		};
		
	private static final Vector[] NEXT_DOWN_NEIGHBORS = {
			new Vector(-Config.getTileSize(), -Config.getTileSize()*2),
			new Vector(0, -Config.getTileSize()*2),
			new Vector(Config.getTileSize(), -Config.getTileSize()*2),
		};
		
	private static final Vector[] IMMEDIATE_LEFT_NEIGHBORS = {
			new Vector(-Config.getTileSize(), Config.getTileSize()),
			new Vector(-Config.getTileSize(), 0),
			new Vector(-Config.getTileSize(), -Config.getTileSize()),
		};
		
	private static final Vector[] NEXT_LEFT_NEIGHBORS = {
			new Vector(-Config.getTileSize()*2, Config.getTileSize()),
			new Vector(-Config.getTileSize()*2, 0),
			new Vector(-Config.getTileSize()*2, -Config.getTileSize()),
		};
		
	public static EnumMap<facingDirections, Vector[]> immediateNeighbors = new EnumMap<facingDirections, Vector[]>(facingDirections.class);
	public static EnumMap<facingDirections, Vector[]> nextNeighbors = new EnumMap<facingDirections, Vector[]>(facingDirections.class);

	static {
		immediateNeighbors.put(facingDirections.UP, IMMEDIATE_UP_NEIGHBORS);
		immediateNeighbors.put(facingDirections.RIGHT, IMMEDIATE_RIGHT_NEIGHBORS);
		immediateNeighbors.put(facingDirections.DOWN, IMMEDIATE_DOWN_NEIGHBORS);
		immediateNeighbors.put(facingDirections.LEFT, IMMEDIATE_LEFT_NEIGHBORS);
		nextNeighbors.put(facingDirections.UP, NEXT_UP_NEIGHBORS);
		nextNeighbors.put(facingDirections.RIGHT, NEXT_RIGHT_NEIGHBORS);
		nextNeighbors.put(facingDirections.DOWN, NEXT_DOWN_NEIGHBORS);
		nextNeighbors.put(facingDirections.LEFT, NEXT_LEFT_NEIGHBORS);
	}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

}
