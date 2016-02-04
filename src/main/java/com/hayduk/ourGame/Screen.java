package com.hayduk.ourGame;

/***
 * The game screen
 */
public class Screen {

	private static int widthPx = 640;
	private static int heightPx = 480;
	
	// The current center of the screen
	// TODO - Get the saved center from the database
	private static Coordinate center = new Coordinate(0.0, 0.0);

	public static int getWidthPx() {
		return widthPx;
	}
	public static void setWidthPx(int widthPx) {
		Screen.widthPx = widthPx;
	}
	public static int getHeightPx() {
		return heightPx;
	}
	public static void setHeightPx(int heightPx) {
		Screen.heightPx = heightPx;
	}
	public static Coordinate getCenter() {
		return center;
	}
	public static void setCenter(Coordinate center) {
		Screen.center = center;
	}

}
