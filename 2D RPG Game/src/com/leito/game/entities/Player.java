package com.leito.game.entities;

import com.leito.game.InputHandler;
import com.leito.game.gfx.Font;
import com.leito.game.gfx.Screen;
import com.leito.game.gfx.SpriteSheet;
import com.leito.game.level.Level;

public class Player extends Mob {

	private InputHandler input;
	

	protected boolean isSwimming = false;
	private int updateCount = 0;

	public Player(SpriteSheet sheet, Level level, int x, int y, InputHandler input, String name) {
		super(sheet, level, name, x, y, 1);
		this.input = input;
	}

	@Override
	public void update() {
		int xa = 0;
		int ya = 0;

		if (input.up.isPressed()) {
			ya--;
		}
		if (input.down.isPressed()) {
			ya++;
		}
		if (input.left.isPressed()) {
			xa--;
		}
		if (input.right.isPressed()) {
			xa++;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else {
			isMoving = false;
		}

		if (level.getTile(this.x >> 4, this.y >> 4).getId() == 9) {
			isSwimming = true;
		}
		if (isSwimming && level.getTile(this.x >> 4, this.y >> 4).getId() != 9) {
			isSwimming = false;
		}
		updateCount++;
	}

	@Override
	public void render(Screen screen) {
		int xTile = 2;
		int yTile = 7;
		int mirrorDir = 0x00;

		if (movingDir == 0) {
			xTile = 0;
			yTile = 0;
			if (isMoving) {
				if (updateCount % 20 > 10) {
					xTile = 0;
					yTile = 2;
				} else {
					xTile = 0;
					yTile = 4;
				}
			}
		}
		if (movingDir == 1) {
			xTile = 2;
			yTile = 0;
			if (isMoving) {
				if (updateCount % 20 > 10) {
					xTile = 2;
					yTile = 2;
				} else {
					xTile = 2;
					yTile = 4;
				}
			}
		}
		if (movingDir == 2) {
			xTile = 1;
			yTile = 0;
			if (isMoving) {
				if (updateCount % 20 > 10) {
					xTile = 1;
					yTile = 2;
				} else {
					xTile = 1;
					yTile = 4;
				}
			}
			mirrorDir = 0x01;
		}
		if (movingDir == 3) {
			xTile = 1;
			yTile = 0;
			if (isMoving) {
				if (updateCount % 20 > 10) {
					xTile = 1;
					yTile = 2;
				} else {
					xTile = 1;
					yTile = 4;
				}
			}
		}
		
		int modifier = 16 * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 8;
		
		if(isSwimming){
			yOffset += 8;
			if(updateCount % 60 < 15){
				screen.render16Pixel(sheet, xOffset, yOffset + 3, 3 + 0 * 16, 0x00, 1);
				screen.render16Pixel(sheet, xOffset + 8 , yOffset + 3, 3 + 0 * 16, 0x01, 1);
			}else if (15 <= updateCount % 60 && updateCount % 60 < 30){
				yOffset -= 1;
				screen.render16Pixel(sheet, xOffset, yOffset + 3, 4 + 0 * 16, 0x00, 1);
				screen.render16Pixel(sheet, xOffset + 8, yOffset + 3, 4 + 0 * 16, 0x01, 1);
			}else if (30 <= updateCount % 60 && updateCount % 60 < 45){
				screen.render16Pixel(sheet, xOffset, yOffset + 3, 5 + 0 * 16, 0x00, 1);
				screen.render16Pixel(sheet, xOffset + 8, yOffset + 3, 5 + 0 * 16, 0x01, 1);
			}else{
				yOffset -= 1;
				screen.render16Pixel(sheet, xOffset, yOffset + 3, 4 + 0 * 16, 0x00, 1);
				screen.render16Pixel(sheet, xOffset + 8, yOffset + 3, 4 + 0 * 16, 0x01, 1);
			}
			Font.render(getName(), screen, x + 7, y - 18, 1);	
			
		}

		screen.render16Pixel(sheet, xOffset + 4, yOffset, xTile + yTile * 16,
				mirrorDir, scale);
		// screen.render16Pixel(xOffset + (modifier * mirrorDir), yOffset, xTile
		// + yTile * 16, mirrorDir, scale);
		// screen.render16Pixel(xOffset + modifier - (modifier * mirrorDir),
		// yOffset, (xTile + 1) + yTile * 16, mirrorDir, scale);

		if (!isSwimming) {
			screen.render16Pixel(sheet, xOffset + 4, yOffset + modifier, xTile
					+ (yTile + 1) * 16, mirrorDir, scale);
			// screen.render16Pixel(xOffset+ (modifier * mirrorDir), yOffset +
			// modifier, xTile + (yTile + 1) * 16, mirrorDir, scale);
			// screen.render16Pixel(xOffset + modifier - (modifier * mirrorDir),
			// yOffset + modifier, (xTile + 1) + (yTile + 1) * 16, mirrorDir,
			// scale);
			Font.render(getName(), screen, x + 7, y - 25, 1);	
		}
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax = 8;
		int yMin = 3;
		int yMax = 8;
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSwimming() {
		return isSwimming;
	}
}
