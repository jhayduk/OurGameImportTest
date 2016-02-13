package com.hayduk.ourGame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class OurGame extends BasicGame {

	public OurGame(String title) {
		super(title);
	}

	public static void main(String[] args) throws SlickException {
		TileInfo.preWindowInit();
		CharacterInfo.preWindowInit();
		Screen.preWindowInit();
		WorldMap.preWindowInit();
		Characters.preWindowInit();
		try {
			AppGameContainer app = new AppGameContainer(new OurGame("Our Game"));
			app.setDisplayMode(Screen.getWidthPx(), Screen.getHeightPx(), false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		WorldMap.render();
		Characters.render();
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		WorldMap.update();
	}

	
}
