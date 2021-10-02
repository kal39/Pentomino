package matrix_creator;

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
		printBoards = false;

		// boardWidth = 5;
		// boardHeight = 5;

		// int[][] board = place_pentomino(1, 0, PentominoShapes.get_pentomino('X'));
		// print_board(board);

		// int[][][] boards = get_placements_2d(PentominoShapes.get_pentomino('X'));

		// for (int i = 0; i < boards.length; i++) {
		// print_board(boards[i]);
		// }

		char[] allLetters = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F' };
		int data[][] = create(3, 3, allLetters);
		ArrayUtils.print_2d_array(data);

		for (int i = 0; i < data.length; i++) {
			print_board(get_pentomino_location_in_board(data[i], allLetters.length));
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

		for (int i = 0; i < pieces.length; i++) {
			int[][][] configurations = PentominoShapes.get_pentomino_variations(pieces[i]);

			for (int j = 0; j < configurations.length; j++) {
				int[][] placements = get_placements(configurations[j]);

				for (int k = 0; k < placements.length; k++) {

					int[] row = new int[pieces.length];
					row[i] = 1;

					row = ArrayUtils.add_1d_arrays(row, placements[k]);

					data = ArrayUtils.add_row_to_2d_array(data, row);
				}
			}
		}

		return data;
	}

	/*
	 * Gets pentomino location in the board
	 */

	public static int[][] get_pentomino_location_in_board(int[] data, int letterCount) {
		int[][] board = new int[boardHeight][boardWidth];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = data[letterCount + i + j * board[0].length];
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
			placements = ArrayUtils.add_row_to_2d_array(placements, ArrayUtils.convert_2d_to_1d(placements3d[i]));
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
				placements = ArrayUtils.add_layer_to_3d_array(placements, place_pentomino(j, i, pentomino));
			}
		}

		if (printBoards) {
			for (int i = 0; i < placements.length; i++) {
				print_board(placements[i]);
				System.out.println();
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

	private static void print_board(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 1)
					System.out.print("██");
				else
					System.out.print("░░");
			}

			System.out.println();
		}
	}
}