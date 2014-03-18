package com.leito.game.gfx;

public class Screen {

	public static final int MAP_WIDTH = 128;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	
	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	public int[] pixels;

	public int xOffset = 0;
	public int yOffset = 0;

	public int width;
	public int height;

	public SpriteSheet sheet;

	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;

		pixels = new int[width * height];
	}

	// alte render
	@Deprecated
	public void render(int[] pixels, int offset, int row) {
		for (int yTile = yOffset >> 3; yTile <= (yOffset + height) >> 3; yTile++) {
			int yMin = yTile * 8 - yOffset;
			int yMax = yMin + 8;
			if (yMin < 0)
				yMin = 0;
			if (yMax > height)
				yMax = height;

			for (int xTile = xOffset >> 3; xTile <= (xOffset + width) >> 3; xTile++) {
				int xMin = xTile * 8 - xOffset;
				int xMax = xMin + 8;
				if (xMin < 0)
					xMin = 0;
				if (xMax > width)
					xMax = width;

				for (int y = yMin; y < yMax; y++) {
					int sheetPixel = ((y + yOffset) & 15) * sheet.width
							+ ((xMin + xOffset) & 15);
					int tilePixel = offset + xMin + y * row;
					for (int x = xMin; x < xMax; x++) {
						pixels[tilePixel++] = sheet.pixels[sheetPixel++];
					}
				}
			}
		}
	}


	// neue render
	public void render(int xPos, int yPos, int tile, int mirrorDir) {
		xPos -= xOffset;
		yPos -= yOffset;
		
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

		int xTile = tile % 16;
		int yTile = tile / 16;
		int tileOffset = (xTile << 4) + (yTile << 4) * sheet.width;
		for (int y = 0; y < 16; y++) {
			int ySheet = y;
			if(mirrorY) ySheet = 15 - y;
			if (y + yPos < 0 || y + yPos >= height)
				continue;
			for (int x = 0; x < 16; x++) {
				int xSheet = x;
				if(mirrorX) xSheet = 15 - x;
				if (x + xPos < 0 || x + xPos >= width)
					continue;
				
				int col = sheet.pixels[xSheet + ySheet * sheet.width + tileOffset];
				if (col != 0xffff15ff) pixels[(x + xPos) + (y + yPos) * width] = col;
			}
		}

	}
	
	public void render(int x, int y, int tile) {
		render(x, y, tile, 0x00);
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
