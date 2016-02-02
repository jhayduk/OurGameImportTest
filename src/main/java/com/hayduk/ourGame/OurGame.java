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

	public static void main(String[] args) {
		//System.setProperty("org.lwjgl.librarypath", "/Users/jhayduk/Workspace-personal/OurGame/build/natives/osx");
		try {
			AppGameContainer app = new AppGameContainer(new OurGame("Our Game"));
			app.setDisplayMode(500, 400, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
	}

	
}
