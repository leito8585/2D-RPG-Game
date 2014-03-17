package com.leito.game.gfx;

public class Screen {

	public static final int MAP_WIDTH = 128;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

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
	public void render(int xPos, int yPos, int tile) {
		xPos -= xOffset;
		yPos -= yOffset;

		int xTile = tile % 16;
		int yTile = tile / 16;
		int tileOffset = (xTile << 4) + (yTile << 4) * sheet.width;
		for (int y = 0; y < 16; y++) {
			if (y + yPos < 0 || y + yPos >= height)
				continue;
			int ySheet = y;
			for (int x = 0; x < 16; x++) {
				if (x + xPos < 0 || x + xPos >= width)
					continue;
				int xSheet = x;
				pixels[(x + xPos) + (y + yPos) * width] = sheet.pixels[xSheet
						+ ySheet * sheet.width + tileOffset];
			}
		}

	}
}
