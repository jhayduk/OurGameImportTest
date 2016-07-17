package com.hayduk.ourGame;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

/**
 * Time in the game is very similar to unix time and is stored as milliseconds
 * since the epoch. However, the epoch in the game is since the beginning of the
 * game itself. While a long has enough resolution for well over 6000 years, we
 * use a double so that we don't have to worry about integer rounding. The ratio
 * of game time to real time is somewhat fluid during the game and can speed up
 * or slow down as needed.
 */
public class GameTime {

	public static final double MS_PER_SOLAR_YEAR = 31556925216.0;
	public static final double MONTHS_PER_YEAR = 12.0;
	public static final double MS_PER_MONTH = MS_PER_SOLAR_YEAR / MONTHS_PER_YEAR;
	public static final double DAYS_PER_MONTH = 30.0;
	public static final double MS_PER_DAY = MS_PER_MONTH / DAYS_PER_MONTH;
	
	private static Font awtFont;
	private static TrueTypeFont font;
	private static double gameTime;
	private static double currentGameMsPerUnixMs;

	public static void postWindowInit() {
		awtFont = new Font("SansSerif", Font.BOLD, 14);
		font = new TrueTypeFont(awtFont, false);
		gameTime = (5 * MS_PER_DAY); // the start of day 6
		currentGameMsPerUnixMs = Config.REAL_TIME_GAME_MS_PER_UNIX_MS;
	}
	
	public static double get() {
		return gameTime;
	}
	
	public static int year() {
		return (int)((gameTime / MS_PER_SOLAR_YEAR) + 1);
	}
	
	public static int monthInYear() {
		return (int)(((gameTime - ((year()-1) * MS_PER_SOLAR_YEAR)) / MS_PER_MONTH) + 1);
	}
	
	public static int month() {
		return (int)((gameTime / MS_PER_MONTH)+1);
	}
	
	public static int dayInMonth() {
		return (int)(((gameTime - ((month()-1) * MS_PER_MONTH)) / MS_PER_DAY) + 1);
	}

	public static void render() {
		font.drawString(10, 32, String.format("%1$02d.%2$02d.%3$04d", monthInYear(), dayInMonth(), year()));
//		font.drawString(10, 32, monthInYear() + "/" + dayInMonth() + "/" + year());
	}

	public static void update(int deltaMs) {
		gameTime += deltaMs * currentGameMsPerUnixMs;
	}

}
