package puzzle_solver.branching;

import puzzle_solver.utils.*;

public class branching_algorithm_pruning {
	static int WIDTH = 12;
	static int HEIGHT = 5;
	static char[] LETTERS = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F' };
	static int NODES = 0;

	public static void main(String[] args) {
		int[][] board = new int[HEIGHT][WIDTH];

		long t1 = System.nanoTime();

		board = solve(board, LETTERS);

		long t2 = System.nanoTime();

		double t = (double) (t2 - t1) / 1000000000;

		ArrayUtils.print(board);

		System.out.println("NODES: " + NODES + ", time: " + t + " s");
	}

	public static int[][] solve(int[][] board, char[] shapes) {
		int[][] answer = recursive_branching(board, shapes, 0);

		if (answer.length == 0)
			return answer;

		for (int i = 0; i < answer.length; i++) {
			for (int j = 0; j < answer[0].length; j++) {
				answer[i][j]--;
			}
		}

		return answer;
	}

	static int[][] recursive_branching(int[][] board, char[] shapes, int n) {
		NODES++;

		if (isBoardFull(board))
			return board;

		if (isSolvable(board) == false)
			return new int[0][0];

		int[][][] orientations = PentominoShapes.get_pentomino_variations(shapes[n]);

		for (int i = 0; i < orientations.length; i++) {

			int[][][] placements = get_placements(orientations[i]);

			for (int j = 0; j < placements.length; j++) {
				if (isPentominoPlacable(board, placements[j])) {
					int[][] newBoard = recursive_branching(add_boards(board, placements[j], n + 1), shapes, n + 1);
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

	static Boolean isSolvable(int[][] board) {
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
