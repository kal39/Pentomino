package pentomino_solver;

import algorithm_x.*;
import matrix_creator.*;
import utils.*;

public class Solver {
	// static final int width = 10;
	// static final int height = 6;
	// static final char[] pieces = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L',
	// 'P', 'N', 'F' };

	static final int width = 5;
	static final int height = 3;
	static final char[] pieces = { 'U', 'P', 'V' };

	// static final int width = 5;
	// static final int height = 2;
	// static final char[] pieces = { 'L', 'L' };

	// static final int width = 5;
	// static final int height = 3;
	// static final char[] pieces = { 'U', 'U', 'X' };

	// static final int width = 5;
	// static final int height = 2;
	// static final char[] pieces = { 'P', 'P', };

	// static final int width = 6;
	// static final int height = 5;
	// static final char[] pieces = { 'T', 'I', 'Z', 'Y', 'W', 'L' };

	public static void main(String[] args) {

		int[][] matrix = MatrixCreator.create(width, height, pieces);

		// for (int i = 0; i < matrix.length; i++) {
		// MatrixCreator.print_board(
		// MatrixCreator.get_pentomino_location_in_board(matrix[i], pieces.length,
		// width, height));
		// System.out.println();
		// }

		int[] solutions = AlgorithmX.solve(matrix);

		System.out.print("Use these pieces: ");
		ArrayUtils.print_1d_array(solutions);
		System.out.println();

		for (int i = 0; i < solutions.length; i++) {
			int[][] piece = MatrixCreator.get_pentomino_location_in_board(matrix[solutions[i]], pieces.length, width,
					height);
			MatrixCreator.print_board(piece);
		}
	}

}
