package edu.xmu.test.codekata.gomoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

import five.engine.Board;
import five.engine.GameEngine;
import five.engine.Gui;
import five.engine.NamedPlayer;
import five.engine.PlayerWrapper;
import five.player.GameConfig;
import five.player.Player;
import five.player.Point;

public class SampleMain {

	static class AIPlayer implements Player {
		public final static short BLANK = 0;
		public final static short SELF = 1;
		public final static short ENERMY = -1;

		GomokuPosition memory;

		@Override
		public Point step(Point point) {
			Point decision = new Point();
			if (null == memory) {
				int width = GameConfig.getWidth();
				int height = GameConfig.getHeight();
				memory = new GomokuPosition(width, height);
			}
			if (point == null) {
				decision = think();
			} else {
				memory.grid[point.x][point.y] = ENERMY;
				decision = think();
			}
			memory.grid[decision.x][decision.y] = SELF;

			return decision;
		}

		private Point think() {
			Vector v = new GameSearch().alphaBeta(0, memory, false);
			GomokuPosition newMemory = (GomokuPosition) v.elementAt(1);
			return findDiff(memory, newMemory);
		}

		private Point findDiff(GomokuPosition oldMemory, GomokuPosition newMemory) {
			for (int i = 0; i < oldMemory.getWidth(); i++) {
				for (int j = 0; j < oldMemory.getHeight(); j++) {
					if (oldMemory.grid[i][j] != newMemory.grid[i][j]) {
						return new Point(i, j);
					}
				}
			}
			throw new RuntimeException("Cannot find diff");
		}
	}

	static class FivePlayer implements Player {
		private static final int SELF = 1;
		private static final int ENEMY = -1;

		Board memory = new Board();
		private int maxDepth = 4;

		@Override
		public Point step(Point point) {
			Point decision = new Point();
			int width = GameConfig.getWidth();
			int height = GameConfig.getHeight();
			if (point == null) {
				decision.x = width / 2;
				decision.y = height / 2;
			} else {
				memory.setValue(point, ENEMY);
				decision = think();
			}
			memory.setValue(decision, SELF);
			return decision;
		}

		private Point think() {
			return best();
		}

		private volatile int depth = 0;

		private Point best() {
			List<Point> hotspots = getHotSpots();
			Point best = hotspots.get(0);
			int bestValue = Integer.MIN_VALUE;
			for (Point p : hotspots) {
				int x = best(p);
				if (x > bestValue) {
					bestValue = x;
					best = p;
				}
			}
			return best;
		}

		private int best(Point p) {
			depth++;
			memory.setValue(p, SELF);
			try {
				if (memory.win(p)) {
					return Integer.MAX_VALUE;
				} else if (depth < maxDepth) {
					int max = Integer.MAX_VALUE;
					List<Point> hotspots = getHotSpots();
					for (Point next : hotspots) {
						int w = worst(next);
						if (w < max) {
							max = w;
						}
					}
					return max;
				} else {
					return memory.maxDepth(p);
				}
			} finally {
				memory.setValue(p, 0);
				depth--;
			}
		}

		private int worst(Point p) {
			depth++;
			memory.setValue(p, ENEMY);
			try {
				if (memory.win(p)) {
					return Integer.MIN_VALUE;
				} else if (depth < maxDepth) {
					int max = Integer.MIN_VALUE;
					List<Point> hotspots = getHotSpots();
					for (Point next : hotspots) {
						int w = best(next);
						if (w > max) {
							max = w;
						}
					}
					return max;
				} else {
					return -memory.maxDepth(p);
				}
			} finally {
				memory.setValue(p, 0);
				depth--;
			}

		}

		private List<Point> getHotSpots() {
			int width = GameConfig.getWidth();
			int height = GameConfig.getHeight();
			List<Point> hotspots = new ArrayList<Point>();
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (isHotSpot(x, y)) {
						hotspots.add(new Point(x, y));
					}
				}
			}
			return hotspots;
		}

		boolean isHotSpot(int x, int y) {
			return memory.valid(x, y) && memory.available(x, y) && (//
					isFilled(x - 1, y - 1) || isFilled(x, y - 1) || isFilled(x + 1, y - 1)//
							|| isFilled(x - 1, y) || isFilled(x, y) || isFilled(x + 1, y)//
							|| isFilled(x - 1, y + 1) || isFilled(x, y + 1) || isFilled(x + 1, y + 1)//
					);
		}

		private final boolean isFilled(int x, int y) {
			return memory.valid(x, y) && (!memory.available(x, y));
		}

		class PointX {
			Point point;
			int weight;
		}
	}

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		Gui gui = new Gui();
		ge.addListener(gui);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.doLayout();
		ge.join(new PlayerWrapper(new AIPlayer(), "AI"));
		ge.join(new PlayerWrapper(gui.getInteracitvePlayer(), "Human"));
		NamedPlayer winner = ge.call();
		System.out.println("Game Over! Winer is: " + winner);
		ge.destroy();
	}
}
