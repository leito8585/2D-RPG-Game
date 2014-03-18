package com.leito.game.entities;

import com.leito.game.gfx.Screen;
import com.leito.game.level.Level;

public abstract class Entity {

	public int x, y;
	protected Level level;
	
	public Entity(Level level) {
		init(level);
	}
	
	public final void init(Level level){
		this.level = level;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
}
