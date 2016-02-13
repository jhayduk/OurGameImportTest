package com.hayduk.ourGame;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

/***
 * The game screen
 */
public class Screen {

	private static int widthPx;
	private static int heightPx;
	
	// The current center of the screen
	private static Coordinate center;
	
	// These values are frequently needed so are calculated whenever
	// what they depend on changes
	private static double minX, maxX, minY, maxY;
	private static long numberOfTilesPerScreen;

	public static void preWindowInit() {
		// TODO - Get the saved center from the database
		setCenter(new Coordinate(0.0, 0.0));
		setWidthPx(640);
		setHeightPx(480);
	}
	public static int getWidthPx() {
		return widthPx;
	}
	public static void setWidthPx(int widthPx) {
		Screen.widthPx = widthPx;
		recalculateScreenParameters();
	}
	public static int getHeightPx() {
		return heightPx;
	}
	public static void setHeightPx(int heightPx) {
		Screen.heightPx = heightPx;
		recalculateScreenParameters();
	}
	public static Coordinate getCenter() {
		return center;
	}
	public static void setCenter(Coordinate center) {
		Screen.center = center;
		recalculateScreenParameters();
	}
	public static double getMinX() {
		return minX;
	}
	public static void setMinX(double minX) {
		Screen.minX = minX;
	}
	public static double getMaxX() {
		return maxX;
	}
	public static void setMaxX(double maxX) {
		Screen.maxX = maxX;
	}
	public static double getMinY() {
		return minY;
	}
	public static void setMinY(double minY) {
		Screen.minY = minY;
	}
	public static double getMaxY() {
		return maxY;
	}
	public static void setMaxY(double maxY) {
		Screen.maxY = maxY;
	}
	public static void recalculateScreenParameters() {
		setNumberOfTilesPerScreen(((heightPx/Config.getTileSizePx()) * (widthPx/Config.getTileSizePx())));
		double halfScreenWidth = ((widthPx/2)*Config.getCubitsPerPixel());
		double halfScreenHeight = ((heightPx/2)*Config.getCubitsPerPixel());
		setMinX(center.getX()-halfScreenWidth);
		setMaxX(center.getX()+halfScreenWidth);
		setMinY(center.getY()-halfScreenHeight);
		setMaxY(center.getY()+halfScreenHeight);
	}
	public static long getNumberOfTilesPerScreen() {
		return numberOfTilesPerScreen;
	}
	public static void setNumberOfTilesPerScreen(long numberOfTilesPerScreen) {
		Screen.numberOfTilesPerScreen = numberOfTilesPerScreen;
	}
	
	/***
	 * Creates a Bson filter suitable for use with mongo queries that
	 * specifies the extent of the screen using "x" and "y" fields
	 */
	public static Bson filter() {
	    return (Filters.and(
	    		Filters.gte("x", Screen.getMinX()),
	    		Filters.lte("x", Screen.getMaxX()),
	    		Filters.gte("y", Screen.getMinY()),
	    		Filters.lte("y", Screen.getMaxY())));
	}

}
