package com.leito.game.entities;

import com.leito.game.InputHandler;
import com.leito.game.gfx.Screen;
import com.leito.game.level.Level;

public class Player extends Mob {

	private InputHandler input;

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
	}

	@Override
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 7;
		
		int modifier = 16 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 - 8;
		
		screen.render(xOffset, yOffset, xTile + yTile * 16);
		screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 16);
		
		screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 16);
		screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 16);
		
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
	}
}
