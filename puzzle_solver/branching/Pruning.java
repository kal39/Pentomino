package puzzle_solver.branching;

public class Pruning {
	/*
	 * Checks if the number connected remaining spaces is a multiple of 5
	 */

	public static Boolean isSolvable(int[][] board) {
		int[][] newBoard = copy_array(board);

		int prevMinusOnes = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (newBoard[i][j] == 0) {
					newBoard = empty_cells(newBoard, j, i);
					int totalMinusOnes = count_minus_ones(newBoard);
					int minusOnes = totalMinusOnes - prevMinusOnes;

					if (minusOnes % 5 != 0) {
						return false;
					}

					prevMinusOnes = totalMinusOnes;
				}
			}
		}

		return true;
	}

	static int count_minus_ones(int board[][]) {
		int c = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == -1)
					c++;
			}
		}
		return c;
	}

	/*
	 * This recursive method takes a 2d array filled with '0's and '1's and returns
	 * a 2d array where all empty ('0') cells connected with the initial cell at (x,
	 * y) are converted into '-1's
	 */

	static int[][] empty_cells(int[][] board, int x, int y) {
		int[][] newBoard = board.clone();
		newBoard[y][x] = -1;

		if (x - 1 >= 0) {
			if (board[y][x - 1] == 0) {
				board = empty_cells(board, x - 1, y);
			}
		}
		if (y - 1 >= 0) {
			if (board[y - 1][x] == 0) {
				board = empty_cells(board, x, y - 1);
			}
		}
		if (x + 1 < board[0].length) {
			if (board[y][x + 1] == 0) {
				board = empty_cells(board, x + 1, y);
			}
		}
		if (y + 1 < board.length) {
			if (board[y + 1][x] == 0) {
				board = empty_cells(board, x, y + 1);
			}
		}

		return newBoard;
	}

	/*
	 * Just to make sure arrays are actually deep-copied instead of doing something
	 * else
	 */

	static int[][] copy_array(int[][] in) {
		int[][] out = new int[in.length][in[0].length];

		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				out[i][j] = in[i][j];
			}
		}
		return out;
	}
}
