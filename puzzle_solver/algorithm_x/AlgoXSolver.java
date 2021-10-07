package puzzle_solver.algorithm_x;

import puzzle_solver.utils.ArrayUtils;

public class AlgoXSolver {
	// change these parameters for testing:
	static final int WIDTH = 10;
	static final int HEIGHT = 6;
	static final char[] PENTOMINOS = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F' };

	static boolean PRINT = false;

	public static void main(String[] args) {
		PRINT = true;
		create_and_solve(WIDTH, HEIGHT, PENTOMINOS);
	}

	/*
	 * This method actually solves the puzzle
	 */

	public static int[][][] create_and_solve(int width, int height, char[] pieces) {
		if (width * height != pieces.length * 5)
			return new int[0][0][0];

		int[][] matrix = MatrixCreator.create(width, height, pieces);

		int[] solutions = AlgorithmX.solve(matrix);

		int[][][] solutions2D = new int[0][0][0];

		for (int i = 0; i < solutions.length; i++) {
			int[][] piece = MatrixCreator.get_pentomino_location_in_board(matrix[solutions[i]], pieces.length, width,
					height);
			solutions2D = ArrayUtils.add_element(solutions2D, piece);

			if (PRINT)
				MatrixCreator.print_board(piece);
		}

		return solutions2D;
	}
}