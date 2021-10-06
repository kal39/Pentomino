package puzzle_solver.branching;

import puzzle_solver.utils.*;

public class branching_algorithm {
	static int WIDTH = 4;
	static int HEIGHT = 5;
	static char[] LETTERS = { 'T', 'Y', 'L', 'P' };
	static int NODES = 0;

	public static void main(String[] args) {
		int[][] board = new int[HEIGHT][WIDTH];

		long t1 = System.nanoTime();

		board = solve(board, LETTERS, 0);

		long t2 = System.nanoTime();

		double t = (double) ((t2 - t1) / (long) 100) / 1000000000;

		ArrayUtils.print(board);

		System.out.println("NODES: " + NODES + ", time: " + t + " s");

	}

	static int[][] solve(int[][] board, char[] shapes, int n) {
		NODES++;

		if (isBoardFull(board))
			return board;

		int[][][] orientations = PentominoShapes.get_pentomino_variations(shapes[n]);

		for (int i = 0; i < orientations.length; i++) {

			int[][][] placements = get_placements(orientations[i]);

			for (int j = 0; j < placements.length; j++) {
				if (isPentominoPlacable(board, placements[j])) {
					int[][] newBoard = solve(add_boards(board, placements[j], n + 1), shapes, n + 1);
					if (newBoard.length != 0)
						return newBoard;
				}
			}
		}

		return new int[0][0];
	}

	static boolean isBoardFull(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 0)
					return false;
			}
		}

		return true;
	}

	private static int[][][] get_placements(int[][] pentomino) {
		int[][][] placements = new int[0][0][0];

		for (int i = 0; i <= WIDTH - pentomino[0].length; i++) {
			for (int j = 0; j <= HEIGHT - pentomino.length; j++) {
				placements = ArrayUtils.add_element(placements, place_pentomino_on_empty_board(pentomino, i, j));
			}
		}

		return placements;
	}

	private static int[][] place_pentomino_on_empty_board(int[][] pentomino, int x, int y) {
		int[][] board = new int[HEIGHT][WIDTH];

		for (int i = 0; i < pentomino.length; i++) {
			for (int j = 0; j < pentomino[0].length; j++) {
				board[i + y][j + x] = pentomino[i][j];
			}
		}

		return board;
	}

	private static int[][] add_boards(int[][] board, int[][] pentomino, int n) {
		int[][] newBoard = new int[board.length][board[0].length];

		for (int i = 0; i < pentomino.length; i++) {
			for (int j = 0; j < pentomino[0].length; j++) {
				newBoard[i][j] = board[i][j] + pentomino[i][j] * n;
			}
		}

		return newBoard;
	}

	static boolean isPentominoPlacable(int[][] board, int[][] pentomino) {
		for (int i = 0; i < pentomino.length; i++) {
			for (int j = 0; j < pentomino[0].length; j++) {
				if (pentomino[i][j] != 0 && board[i][j] != 0)
					return false;
			}
		}

		return true;
	}
}
