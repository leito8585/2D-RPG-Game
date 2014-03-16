package com.leito.game.gfx;

public class Screen {

	public static final int MAP_WIDTH = 128;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	
	public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];
	public int[] colours = new int[MAP_WIDTH * MAP_WIDTH * 4];
	
	public int xOffset = 0;
	public int yOffset = 0;
	
	public int width;
	public int height;
	
	public SpriteSheet sheet;
	
	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		
	}
	
	public void render(int[] pixels, int offset, int row){
		for(int yTile = yOffset >> 3; yTile <= (yOffset + height) >> 3; yTile++){
			int yMin = yTile * 8 - yOffset;
			int yMax = yMin + 8;
			if(yMin < 0) yMin = 0;
			if(yMax > height) yMax = height;
			
			for(int xTile = xOffset >> 3; xTile <= (xOffset + width) >> 3; xTile++){
				int xMin = xTile * 8 - xOffset;
				int xMax = xMin + 8;
				if(xMin < 0) xMin = 0;
				if(xMax > width) xMax = width;
								
				for(int y = yMin; y < yMax; y++){
					int sheetPixel = ((y + yOffset) & 15) *  sheet.width + ((xMin + xOffset) & 15);
					int tilePixel = offset + xMin + y * row;
					for(int x = xMin; x < xMax; x++){
						pixels[tilePixel++] = sheet.pixels[sheetPixel++];
					}
				}
			}
		}
	}
}
