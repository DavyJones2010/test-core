package edu.xmu.test.codekata.gomoku;

import java.util.Random;

import five.player.GameConfig;
import five.player.Player;
import five.player.Point;

public class NaivePlayer implements Player {
	public final static short BLANK = 0;
	public final static short SELF = 1;
	public final static short ENERMY = -1;

	short[][] memory;

	@Override
	public Point step(Point point) {
		Point decision = new Point();
		int width = GameConfig.getWidth();
		int height = GameConfig.getHeight();
		if (null == memory) {
			memory = new short[width][height];
		}
		if (point == null) {
			decision = new Point(width / 2, height / 2);
		} else {
			memory[point.x][point.y] = ENERMY;
			decision = next(width, height, point.x, point.y);
		}
		memory[decision.x][decision.y] = SELF;

		return decision;
	}

	Random seed = new Random();

	Point next(int width, int height, int x, int y) {
		int newX = x;
		int newY = y;
		while (newX == x) {
			newX = seed.nextInt(width);
		}
		while (newY == y) {
			newY = seed.nextInt(height);
		}
		return new Point(newX, newY);
	}
}
