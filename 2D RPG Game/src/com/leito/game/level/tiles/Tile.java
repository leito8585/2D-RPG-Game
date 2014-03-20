package com.leito.game.level.tiles;

import com.leito.game.gfx.Screen;
import com.leito.game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, 0xFF000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 3, 0xFF555555);
	public static final Tile GRASS = new BasicTile(2, 8, 1, 0xFF00FF00);
	public static final Tile GROUND = new BasicTile(3, 0, 3, 0xFF914300);
	public static final Tile WALL = new BasicSolidTile(4, 1, 4, 0xFF303030);
	public static final Tile WATER = new AnimatedTile(5, new int[][] {{5, 3},{6, 3},{7, 3},{6, 3}},  0xFF0000FF, 1000);
	public static final Tile VORTEX = new AnimatedTile(6, new int[][] {{5, 4},{6, 4},{7, 4},{8, 4}},  0xFF00005E, 100);

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColour;
	
	public Tile(int id, boolean solid, boolean isEmitter, int levelColour){
		this.id = (byte) id;
		if(tiles[id] != null) throw new RuntimeException("Duplizieren Kachel-ID auf " + id);
		this.solid = solid;
		this.emitter = isEmitter;
		this.levelColour = levelColour;
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
	
	public int getLevelColour(){
		return levelColour;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen, Level level, int x, int y);
}
