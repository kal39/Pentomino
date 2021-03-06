package puzzle_solver.branching;

import puzzle_solver.utils.*;

public class BranchingAlgorithmPruning {
	static int WIDTH = 12;
	static int HEIGHT = 5;
	static char[] PENTOMINOS = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F' };

	public static void main(String[] args) {
		int[][] board = new int[HEIGHT][WIDTH];
		board = solve(PENTOMINOS, WIDTH, HEIGHT);
		ArrayUtils.print(board);
	}

	/*
	 * This method creates and initializes and calls the recursive method
	 */

	public static int[][] solve(char[] shapes, int width, int height) {
		int[][] board = new int[height][width];
		int[][] answer = recursive_branching(board, shapes, 0);

		if (answer.length == 0)
			return answer;

		// just for gui output purposes
		for (int i = 0; i < answer.length; i++) {
			for (int j = 0; j < answer[0].length; j++) {
				answer[i][j]--;
			}
		}

		return answer;
	}

	/*
	 * This recursive method actually solves the puzzle
	 */

	static int[][] recursive_branching(int[][] board, char[] shapes, int n) {
		if (isBoardFull(board))
			return board; // success

		// pruning (checks if the connected remaining spaces are a multiple of 5)
		if (Pruning.isSolvable(board) == false)
			return new int[0][0]; // failure, abandon branch

		int[][][] orientations = PentominoShapes.get_pentomino_variations(shapes[n]);

		// for each orientation of a pentomino
		for (int i = 0; i < orientations.length; i++) {
			int[][][] placements = get_placements(orientations[i], board[0].length, board.length);

			// for each placement of a pentomino at a given orientation
			for (int j = 0; j < placements.length; j++) {
				if (is_pentomino_placable(board, placements[j])) {
					int[][] newBoard = recursive_branching(add_boards(board, placements[j], n + 1), shapes, n + 1);

					// the recursive method returns non-zero if it succeeds
					if (newBoard.length != 0)
						return newBoard;
				}
			}
		}

		return new int[0][0];
	}

	/*
	 * Checks if the board is full
	 */

	static boolean isBoardFull(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 0)
					return false;
			}
		}

		return true;
	}

	private static int[][][] get_placements(int[][] pentomino, int width, int height) {
		int[][][] placements = new int[0][0][0];

		for (int i = 0; i <= width - pentomino[0].length; i++) {
			for (int j = 0; j <= height - pentomino.length; j++) {
				placements = ArrayUtils.add_element(placements,
						place_pentomino_on_empty_board(pentomino, i, j, width, height));
			}
		}

		return placements;
	}

	private static int[][] place_pentomino_on_empty_board(int[][] pentomino, int x, int y, int width, int height) {
		int[][] board = new int[height][width];

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

	static boolean is_pentomino_placable(int[][] board, int[][] pentomino) {
		for (int i = 0; i < pentomino.length; i++) {
			for (int j = 0; j < pentomino[0].length; j++) {
				if (pentomino[i][j] != 0 && board[i][j] != 0)
					return false;
			}
		}

		return true;
	}
}
