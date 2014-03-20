package com.leito.game.level.tiles;

import com.leito.game.gfx.Screen;
import com.leito.game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0);
	public static final Tile GRASS = new BasicTile(2, 2, 0);

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	
	public Tile(int id, boolean solid, boolean isEmitter){
		this.id = (byte) id;
		if(tiles[id] != null) throw new RuntimeException("Duplizieren Kachel-ID auf " + id);
		this.solid = solid;
		this.emitter = isEmitter;
		this.tiles[id] = this;
	}
	
	public byte getId(){
		return id;
	}
	
	public boolean isSolid(){
		return solid;
	}
	public boolean isEmitter(){
		return emitter;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y);
}
