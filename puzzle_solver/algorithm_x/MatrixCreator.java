package puzzle_solver.algorithm_x;

import puzzle_solver.utils.*;

/*
 * The MatrixCreator class deals with generating a 2d array that can be processed by the main solving algorithm
 * Requires the PentominoShapes and ArrayUtils class.
 */

public class MatrixCreator {
	public static boolean printBoards = false;
	private static int boardWidth;
	private static int boardHeight;

	/*
	 * For testing purposes:
	 */

	public static void main(String[] args) {
		printBoards = true;

		char[] letters = { 'U', 'P', 'V' };
		int data[][] = create(5, 3, letters);
		ArrayUtils.print(data);

		for (int i = 0; i < data.length; i++) {
			print_board(get_pentomino_location_in_board(data[i], letters.length, boardWidth, boardHeight));
			System.out.println();
		}

	}

	/*
	 * Creates the matrix to be used by the solving algorithm
	 */

	public static int[][] create(int width, int height, char[] pieces) {
		boardWidth = width;
		boardHeight = height;

		int[][] data = new int[0][0];

		// looping through each pentomino piece
		for (int i = 0; i < pieces.length; i++) {
			int[][][] configurations = PentominoShapes.get_pentomino_variations(pieces[i]);

			// looping through each pentomino orientations
			for (int j = 0; j < configurations.length; j++) {
				int[][] placements = get_placements(configurations[j]);

				// looping through each pentomino placement
				for (int k = 0; k < placements.length; k++) {
					// first part of the row: the id of the pentomino
					int[] row = new int[pieces.length];
					row[i] = 1;

					// adding the placement data to the row
					row = ArrayUtils.add(row, placements[k]);

					data = ArrayUtils.add_element(data, row);
				}
			}
		}

		return data;
	}

	/*
	 * Gets pentomino location in the board
	 */

	public static int[][] get_pentomino_location_in_board(int[] data, int letterCount, int width, int height) {
		int[][] board = new int[height][width];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = data[letterCount + j + i * width];
			}
		}

		return board;
	}

	/*
	 * Gets all the placements for a single pentomino shape in a 1d array
	 */

	private static int[][] get_placements(int[][] pentomino) {
		int[][][] placements3d = get_placements_2d(pentomino);
		int[][] placements = new int[0][0];

		for (int i = 0; i < placements3d.length; i++) {
			// convert the 2d array received from the get_placements_2d() method to a 1d
			// array
			placements = ArrayUtils.add_element(placements, ArrayUtils.convert(placements3d[i]));
		}

		return placements;
	}

	/*
	 * Gets all the placements for a single pentomino shape in a 2d array
	 */

	private static int[][][] get_placements_2d(int[][] pentomino) {
		int[][][] placements = new int[0][0][0];

		for (int i = 0; i < boardHeight - pentomino.length + 1; i++) {
			for (int j = 0; j < boardWidth - pentomino[0].length + 1; j++) {
				int[][] board = place_pentomino(j, i, pentomino);

				// check if the placement is valid (using the pruning method from the branching
				// algorithm)
				if (isSolvable(board))
					placements = ArrayUtils.add_element(placements, board);
			}
		}

		return placements;
	}

	/*
	 * Places a pentomino in a board
	 */

	private static int[][] place_pentomino(int x, int y, int[][] pentomino) {
		int[][] board = new int[boardHeight][boardWidth];

		for (int i = 0; i < pentomino.length; i++) {
			for (int j = 0; j < pentomino[0].length; j++) {
				board[i + y][j + x] = pentomino[i][j];
			}
		}

		return board;
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

	public static void print_board(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 1)
					System.out.print("██");
				else
					System.out.print("░░");
			}

			System.out.println();
		}

		System.out.println();
	}
}