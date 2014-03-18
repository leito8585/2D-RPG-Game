package com.leito.game.level;

import java.util.ArrayList;
import java.util.List;

import com.leito.game.entities.Entity;
import com.leito.game.entities.Player;
import com.leito.game.gfx.Screen;
import com.leito.game.level.tiles.Tile;

public class Level {

	private byte[] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Level(int width, int height) {
	 	tiles = new byte[width * height];
	 	this.width = width;
	 	this.height = height;
	 	this.generateLavel();
	}

	private void generateLavel() {
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(x * y % 10 < 5){
					tiles[x + y * width] = Tile.GRASS.getId();
				} else {
					tiles[x + y * width] = Tile.STONE.getId();
				}
			}
		}
	}
	
	public void update(){
		for(Entity entity : entities){
			entity.update();
		}
	}
	
	public void renderTiles(Screen screen, int xOffset, int yOffset){
		if(xOffset < 0) xOffset = 0;
		if(xOffset > ((width << 4) - screen.width))  xOffset = ((width << 4) - screen.width);
		if(yOffset < 0) yOffset = 0;
		if(yOffset > ((height << 4) - screen.height))  yOffset = ((height << 4) - screen.height);
		
		screen.setOffset(xOffset, yOffset);
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				getTile(x, y).render(screen, this, x << 4, y << 4);
			}
		}
	}
	
	public void renderEntities(Screen screen){
		for(Entity entity : entities){
			entity.render(screen);;
		}
	}

	private Tile getTile(int x, int y) {
		if(x < 0 || x > width || y < 0 || y > height) return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
}
