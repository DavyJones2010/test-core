package edu.xmu.test.codekata.gomoku;

public class GomokuPosition extends Position {
	public final static short BLANK = 0;
	public final static short SELF = 1;
	public final static short ENERMY = -1;

	short[][] grid;

	int CHESS_WIDTH;
	int CHESS_HEIGHT;

	public GomokuPosition(int width, int height) {
		CHESS_WIDTH = width;
		CHESS_HEIGHT = height;
		setDefaultState(width, height);
	}
	
	public void setDefaultState(int width, int height) {
		grid = new short[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				grid[i][j] = BLANK;
	}

	public GomokuPosition getNewPosition() {
		GomokuPosition pos = new GomokuPosition(CHESS_WIDTH, CHESS_HEIGHT);
		for (int i = 0; i < CHESS_WIDTH; i++)
			for (int j = 0; j < pos.CHESS_HEIGHT; j++)
				pos.grid[i][j] = grid[i][j];
		return pos;
	}

	public int getWidth() {
		return CHESS_WIDTH;
	}

	public int getHeight() {
		return CHESS_HEIGHT;
	}
}
