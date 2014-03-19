package com.leito.game.entities;

import com.leito.game.InputHandler;
import com.leito.game.gfx.Screen;
import com.leito.game.level.Level;


public class Player extends Mob {

	private InputHandler input;
	private int anim = 0;
	
	public Player(Level level, int x, int y, InputHandler input) {
		super(level, "Leito", x, y, 1);
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
		
		if (anim < 7500) {
			anim++;
		}
		else{
			anim = 0;
		}
	}

	@Override
	public void render(Screen screen) {
		int xTile = 4;
		int yTile = 7;
		int mirrorDir = 0x00;
				
		if (movingDir == 0) {
			xTile = 0;
			yTile = 7;
			if (isMoving) {
				if (anim % 20 > 10) {
					xTile = 0;
					yTile = 9;
				} else {
					xTile = 0;
					yTile = 11;
				}
			}
		}
		if (movingDir == 1) {
			xTile = 4;
			yTile = 7;
			if (isMoving) {
				if (anim % 20 > 10) {
					xTile = 4;
					yTile = 9;
				} else {
					xTile = 4;
					yTile = 11;
				}
			}
		}
		if (movingDir == 2) {
			xTile = 2;
			yTile = 7;
			if (isMoving) {
				if (anim % 20 > 10) {
					xTile = 2;
					yTile = 9;
				} else {
					xTile = 2;
					yTile = 11;
				}
			}
			mirrorDir = 0x01;
		}
		if (movingDir == 3) {
			xTile = 2;
			yTile = 7;
			if (isMoving) {
				if (anim % 20 > 10) {
					xTile = 2;
					yTile = 9;
				} else {
					xTile = 2;
					yTile = 11;
				}
			}
		}
		
		int modifier = 16 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 - 8;
		
		screen.render16Pixel(xOffset + (modifier * mirrorDir), yOffset, xTile + yTile * 16, mirrorDir, scale);
		screen.render16Pixel(xOffset + modifier - (modifier * mirrorDir), yOffset, (xTile + 1) + yTile * 16, mirrorDir, scale);
		
		screen.render16Pixel(xOffset+ (modifier * mirrorDir), yOffset + modifier, xTile + (yTile + 1) * 16, mirrorDir, scale);
		screen.render16Pixel(xOffset + modifier - (modifier * mirrorDir), yOffset + modifier, (xTile + 1) + (yTile + 1) * 16, mirrorDir, scale);
		
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
	}
}
