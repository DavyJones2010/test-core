package edu.xmu.test.codekata.gomoku;

import java.util.Enumeration;
import java.util.Vector;

public class GameSearch {

	public Vector alphaBeta(int depth, Position p, boolean player) {
		Vector v = alphaBetaHelper(depth, p, player, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY);
		return v;
	}

	protected Vector alphaBetaHelper(int depth, Position p, boolean player, float alpha, float beta) {
		if (reachedMaxDepth(p, depth)) {
			Vector v = new Vector(2);
			float value = positionEvaluation(p, player);
			v.addElement(new Float(value));
			v.addElement(null);
			return v;
		}
		Vector best = new Vector();
		Position[] moves = possibleMoves(p, player);
		for (int i = 0; i < moves.length; i++) {
			Vector v2 = alphaBetaHelper(depth + 1, moves[i], !player, -beta, -alpha);
			// if (v2 == null || v2.size() < 1) continue;
			float value = -((Float) v2.elementAt(0)).floatValue();
			if (value > beta) {
				// if(GameSearch.DEBUG) System.out.println(" ! ! ! value="+value+", beta="+beta);
				beta = value;
				best = new Vector();
				best.addElement(moves[i]);
				Enumeration enum2 = v2.elements();
				enum2.nextElement(); // skip previous value
				while (enum2.hasMoreElements()) {
					Object o = enum2.nextElement();
					if (o != null)
						best.addElement(o);
				}
			}
			/**
			 * Use the alpha-beta cutoff test to abort search if we
			 * found a move that proves that the previous move in the
			 * move chain was dubious
			 */
			if (beta >= alpha) {
				break;
			}
		}
		Vector v3 = new Vector();
		v3.addElement(new Float(beta));
		Enumeration enum2 = best.elements();
		while (enum2.hasMoreElements()) {
			v3.addElement(enum2.nextElement());
		}
		return v3;
	}

	public float positionEvaluation(Position p, boolean player) {
		int[][] myConnects = connectN(p, player, 5);
		int[][] enemyConnects = connectN(p, !player, 5);

		// if there is a connect 5 or two open connect 4, then return positive infinity of negative infinity
		if (myConnects[3][0] > 0 || myConnects[2][2] > 0)
			return Float.POSITIVE_INFINITY;
		if (enemyConnects[3][0] > 0 || enemyConnects[2][2] > 0)
			return Float.NEGATIVE_INFINITY;

		int ret = 0;
		GomokuPosition pos = (GomokuPosition) p;

		int[] score = { 10, 100, 1000 };

		short myChessman;
		short enemyChessman;
		if (player) {
			myChessman = GomokuPosition.ENERMY;
			enemyChessman = GomokuPosition.SELF;
		} else {
			myChessman = GomokuPosition.SELF;
			enemyChessman = GomokuPosition.ENERMY;
		}

		for (int i = 0; i < pos.grid.length; i++) {
			for (int j = 0; j < pos.grid[0].length; j++) {
				if (pos.grid[i][j] == myChessman) {
					if (distanceToBoundary(pos, i, j) == 0)
						ret += 1;
					else if (distanceToBoundary(pos, i, j) == 1)
						ret += 2;
					else if (distanceToBoundary(pos, i, j) == 2)
						ret += 4;
					else
						ret += 8;
				} else if (pos.grid[i][j] == enemyChessman) {
					if (distanceToBoundary(pos, i, j) == 0)
						ret -= 1;
					else if (distanceToBoundary(pos, i, j) == 1)
						ret -= 2;
					else if (distanceToBoundary(pos, i, j) == 2)
						ret -= 4;
					else
						ret -= 8;
				}
			}
		}

		ret += myConnects[0][1] * score[0];
		ret += myConnects[0][2] * (int) (score[1] * 0.9);
		ret += myConnects[1][1] * score[1];
		ret += myConnects[1][2] * (int) (score[2] * 0.9);
		ret += myConnects[2][1] * score[2];

		ret -= enemyConnects[0][1] * score[0];
		ret -= enemyConnects[0][2] * (int) (score[1] * 0.9);
		ret -= enemyConnects[1][1] * score[1];
		ret -= enemyConnects[1][2] * (int) (score[2] * 0.9);
		ret -= enemyConnects[2][1] * score[2];

		return ret;
	}

	private int distanceToBoundary(GomokuPosition board, int x, int y) {
		int xToUp = x;
		int xToDown = board.grid.length - 1 - x;
		int yToLeft = y;
		int yToRight = board.grid[0].length - 1 - y;

		xToUp = Math.min(xToUp, xToDown);
		xToUp = Math.min(xToUp, yToLeft);
		xToUp = Math.min(xToUp, yToRight);
		return xToUp;
	}

	public Position[] possibleMoves(Position p, boolean player) {
		GomokuPosition pos = (GomokuPosition) p;
		int count = 0;
		for (int i = 0; i < pos.grid.length; i++)
			for (int j = 0; j < pos.grid[0].length; j++)
				if (pos.grid[i][j] == GomokuPosition.BLANK && isInSize(p, i, j))
					count++;

		Position[] ret = new Position[count];
		count = 0;
		for (int i = 0; i < pos.grid.length; i++) {
			for (int j = 0; j < pos.grid[0].length; j++) {
				// next move must be in 3 grids away from current grids
				if (pos.grid[i][j] == GomokuPosition.BLANK && isInSize(p, i, j)) {
					GomokuPosition pos2 = new GomokuPosition(p.getWidth(), p.getHeight());
					pos2 = pos.getNewPosition();
					if (player)
						pos2.grid[i][j] = GomokuPosition.ENERMY;
					else
						pos2.grid[i][j] = GomokuPosition.SELF;
					ret[count++] = pos2;
				}
			}
		}
		return ret;
	}

	private boolean isInSize(Position p, int x, int y) {
		GomokuPosition pos = (GomokuPosition) p;
		int minX = 0;
		int maxX = 0;
		int minY = 0;
		int maxY = 0;

		boolean firstChessman = true;
		for (int i = 0; i < pos.grid.length; i++) {
			for (int j = 0; j < pos.grid[0].length; j++) {
				if (pos.grid[i][j] != GomokuPosition.BLANK) {
					if (firstChessman) {
						maxX = minX = i;
						maxY = minY = j;
						firstChessman = false;
					} else {
						// if(size[0] > i) size[0] = i;
						if (minX > i)
							minX = i;
						if (maxX < i)
							maxX = i;
						if (minY > j)
							minY = j;
						if (maxX < j)
							maxX = j;
					}
				}
			}
		}

		if (x >= minX - 3 && x <= maxX + 3 && y >= minY - 3 && y <= maxY + 3)
			return true;
		else
			return false;
	}

	public boolean reachedMaxDepth(Position p, int depth) {
		int maxDepth = 4;
		if (depth >= maxDepth)
			return true;
		return false;
	}

	private int[][] connectN(Position p, boolean player, int number) {
		GomokuPosition pos = (GomokuPosition) p;
		short b;
		if (player)
			b = GomokuPosition.ENERMY;
		else
			b = GomokuPosition.SELF;

		int count[][] = new int[number - 1][3];
		for (int i = 0; i < count.length; i++)
			for (int j = 0; j < count[0].length; j++)
				count[i][j] = 0;

		for (int n = 2; n <= number; n++) {
			for (int i = 0; i < pos.grid.length; i++) {
				for (int j = 0; j < pos.grid[0].length; j++) {
					if (i + n - 1 < pos.grid.length) {
						if (downSame(p, i, j, n - 1, b)) {
							while (true) {
								if (i + n < pos.grid.length)
									if (pos.grid[i + n][j] == b)
										break;
								if (i - 1 >= 0)
									if (pos.grid[i - 1][j] == b)
										break;

								count[n - 2][0]++;

								if (i - 1 >= 0 && i + n >= pos.grid.length) {
									if (pos.grid[i - 1][j] == GomokuPosition.BLANK)
										count[n - 2][1]++;
								} else if (i - 1 < 0 && i + n < pos.grid.length) {
									if (pos.grid[i + n][j] == GomokuPosition.BLANK)
										count[n - 2][1]++;
								} else if (i - 1 >= 0 && i + n < pos.grid.length) {
									if (pos.grid[i - 1][j] == GomokuPosition.BLANK && pos.grid[i + n][j] != GomokuPosition.BLANK)
										count[n - 2][1]++;
									if (pos.grid[i + n][j] == GomokuPosition.BLANK && pos.grid[i - 1][j] != GomokuPosition.BLANK)
										count[n - 2][1]++;

									if (pos.grid[i - 1][j] == GomokuPosition.BLANK && pos.grid[i + n][j] == GomokuPosition.BLANK)
										count[n - 2][2]++;
								}
								break;
							}
						}

						if (j + n - 1 < pos.grid[0].length) {
							if (rightSame(p, i, j, n - 1, b)) {
								while (true) {
									if (j + n < pos.grid[0].length)
										if (pos.grid[i][j + n] == b)
											break;
									if (j - 1 >= 0)
										if (pos.grid[i][j - 1] == b)
											break;
									count[n - 2][0]++;

									if (j - 1 >= 0 && j + n >= pos.grid[0].length) {
										if (pos.grid[i][j - 1] == GomokuPosition.BLANK)
											count[n - 2][1]++;
									} else if (j - 1 < 0 && j + n < pos.grid[0].length) {
										if (pos.grid[i][j + n] == GomokuPosition.BLANK)
											count[n - 2][1]++;
									} else if (j - 1 >= 0 && j + n < pos.grid[0].length) {
										if (pos.grid[i][j - 1] == GomokuPosition.BLANK && pos.grid[i][j + n] != GomokuPosition.BLANK)
											count[n - 2][1]++;
										if (pos.grid[i][j + n] == GomokuPosition.BLANK && pos.grid[i][j - 1] != GomokuPosition.BLANK)
											count[n - 2][1]++;

										if (pos.grid[i][j - 1] == GomokuPosition.BLANK && pos.grid[i][j + n] == GomokuPosition.BLANK)
											count[n - 2][2]++;
									}
									break;
								}
							}
						}

						if (i + n - 1 < pos.grid.length && j + n - 1 < pos.grid[0].length) {
							if (rightDownSame(p, i, j, n - 1, b)) {
								while (true) {
									if (i + n < pos.grid.length && j + n < pos.grid[0].length)
										if (pos.grid[i + n][j + n] == b)
											break;
									if (i - 1 >= 0 && j - 1 >= 0)
										if (pos.grid[i - 1][j - 1] == b)
											break;
									count[n - 2][0]++;

									if ((i - 1 >= 0 && j - 1 >= 0) && (i + n >= pos.grid.length || j + n >= pos.grid[0].length)) {
										if (pos.grid[i - 1][j - 1] == GomokuPosition.BLANK)
											count[n - 2][1]++;
									} else if ((i - 1 < 0 || j - 1 < 0) && (i + n < pos.grid.length && j + n < pos.grid[0].length)) {
										if (pos.grid[i + n][j + n] == GomokuPosition.BLANK)
											count[n - 2][1]++;
									} else if ((i - 1 >= 0 && j - 1 >= 0) && (i + n < pos.grid.length && j + n < pos.grid[0].length)) {
										if (pos.grid[i - 1][j - 1] == GomokuPosition.BLANK && pos.grid[i + n][j + n] != GomokuPosition.BLANK)
											count[n - 2][1]++;
										if (pos.grid[i + n][j + n] == GomokuPosition.BLANK && pos.grid[i - 1][j - 1] != GomokuPosition.BLANK)
											count[n - 2][1]++;
										if (pos.grid[i - 1][j - 1] == GomokuPosition.BLANK && pos.grid[i + n][j + n] == GomokuPosition.BLANK)
											count[n - 2][2]++;
									}
									break;
								}
							}
						}

						if (i - n + 1 >= 0 && j + n - 1 < pos.grid[0].length) {
							if (rightUpSame(p, i, j, n - 1, b)) {
								while (true) {
									if (i - n >= 0 && j + n < pos.grid[0].length)
										if (pos.grid[i - n][j + n] == b)
											break;
									if (i + 1 < pos.grid.length && j - 1 >= 0)
										if (pos.grid[i + 1][j - 1] == b)
											break;
									count[n - 2][0]++;

									if ((i - n >= 0 && j + n < pos.grid[0].length) && (i + 1 >= pos.grid.length || j - 1 < 0)) {
										if (pos.grid[i - n][j + n] == GomokuPosition.BLANK)
											count[n - 2][1]++;
									} else if ((i - n < 0 || j + n > pos.grid[0].length) && (i + 1 < pos.grid.length && j - 1 >= 0)) {
										if (pos.grid[i + 1][j - 1] == GomokuPosition.BLANK)
											count[n - 2][1]++;
									} else if ((i - n >= 0 && j + n < pos.grid[0].length) && (i + 1 < pos.grid.length && j - 1 >= 0)) {
										if (pos.grid[i - n][j + n] == GomokuPosition.BLANK && pos.grid[i + 1][j - 1] != GomokuPosition.BLANK)
											count[n - 2][1]++;
										if (pos.grid[i + 1][j - 1] == GomokuPosition.BLANK && pos.grid[i - n][j + n] != GomokuPosition.BLANK)
											count[n - 2][1]++;
										if (pos.grid[i - n][j + n] == GomokuPosition.BLANK && pos.grid[i + 1][j - 1] == GomokuPosition.BLANK)
											count[n - 2][2]++;
									}
									break;
								}
							}
						}
					}
				}
			}
		}

		return count;
	}

	private boolean downSame(Position p, int i, int j, int n, short b) {
		GomokuPosition pos = (GomokuPosition) p;
		for (int k = 0; k <= n; k++)
			if (pos.grid[i + k][j] != b)
				return false;
		return true;
	}

	private boolean rightSame(Position p, int i, int j, int n, short b) {
		GomokuPosition pos = (GomokuPosition) p;
		for (int k = 0; k <= n; k++)
			if (pos.grid[i][j + k] != b)
				return false;
		return true;
	}

	private boolean rightDownSame(Position p, int i, int j, int n, short b) {
		GomokuPosition pos = (GomokuPosition) p;
		for (int k = 0; k <= n; k++)
			if (pos.grid[i + k][j + k] != b)
				return false;
		return true;
	}

	private boolean rightUpSame(Position p, int i, int j, int n, short b) {
		GomokuPosition pos = (GomokuPosition) p;
		for (int k = 0; k <= n; k++)
			if (pos.grid[i - k][j + k] != b)
				return false;
		return true;
	}
}
