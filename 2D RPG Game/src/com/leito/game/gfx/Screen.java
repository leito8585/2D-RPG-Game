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

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];
	}

	// alte render
	@Deprecated
	public void render(SpriteSheet sheet, int[] pixels, int offset, int row) {
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
	public void render16Pixel(SpriteSheet sheet, int xPos, int yPos, int tile, int mirrorDir, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;
		
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

		int scaleMap = scale - 1;
		int xTile = tile % 16;
		int yTile = tile / 16;
		int tileOffset = (xTile << 4) + (yTile << 4) * sheet.width;
		
		for (int y = 0; y < 16; y++) {
			int ySheet = y;
			if(mirrorY) ySheet = 15 - y;
			
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 4) / 2);
			
			for (int x = 0; x < 16; x++) {
				int xSheet = x;
				if(mirrorX) xSheet = 15 - x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 4) / 2);
				
				int col = sheet.pixels[xSheet + ySheet * sheet.width + tileOffset];
				if (col != 0xffff15ff) {
					for(int yScale = 0; yScale < scale; yScale++){
						if (yPixel + yScale < 0 || yPixel + yScale >= height)
							continue;
						for(int xScale = 0; xScale < scale; xScale++){
							if (xPixel + xScale < 0 || xPixel + xScale >= width)
								continue;
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}
				}
			}
		}

	}
	
	public void render8Pixel(SpriteSheet sheet, int xPos, int yPos, int tile, int mirrorDir, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;
		
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

		int scaleMap = scale - 1;
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;
		
		for (int y = 0; y < 8; y++) {
			int ySheet = y;
			if(mirrorY) ySheet = 7 - y;
			
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2);
			
			for (int x = 0; x < 8; x++) {
				int xSheet = x;
				if(mirrorX) xSheet = 7 - x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);
				
				int col = sheet.pixels[xSheet + ySheet * sheet.width + tileOffset];
				if (col != 0xffff15ff) {
					for(int yScale = 0; yScale < scale; yScale++){
						if (yPixel + yScale < 0 || yPixel + yScale >= height)
							continue;
						for(int xScale = 0; xScale < scale; xScale++){
							if (xPixel + xScale < 0 || xPixel + xScale >= width)
								continue;
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}
				}
			}
		}

	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
