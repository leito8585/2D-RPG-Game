package com.leito.game.level.tiles;

import com.leito.game.gfx.SpriteSheet;

public class BasicSolidTile extends BasicTile{

	public BasicSolidTile(SpriteSheet sheet, int id, int x, int y, int levelColour) {
		super(sheet, id, x, y, levelColour);
		this.solid = true;
	}

}
