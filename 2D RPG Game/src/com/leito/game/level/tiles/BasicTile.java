package com.leito.game.level.tiles;

import com.leito.game.gfx.Screen;
import com.leito.game.level.Level;

public class BasicTile extends Tile{

	protected int tileId;

	public BasicTile(int id, int x, int y, int levelColour) {
		super(id, false, false, levelColour);
		this.tileId = x + y * 16;
		
	}

	@Override
	public void render(Screen screen, Level level, int x, int y) {
		screen.render16Pixel(x, y, tileId, 0x00, 1);
	}

	@Override
	public void update() {
	}

	
}
