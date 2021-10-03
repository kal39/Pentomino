package pentomino_solver;

import algorithm_x.*;
import matrix_creator.*;
import utils.*;

public class Solver {
	// change these parameters for testing:
	static final int width = 10;
	static final int height = 6;
	static final char[] pieces = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F' };

	public static void main(String[] args) {
		int[][] matrix = MatrixCreator.create(width, height, pieces);

		int[] solutions = AlgorithmX.solve(matrix);

		System.out.print("Use these pieces: ");
		ArrayUtils.print(solutions);
		System.out.println();

		for (int i = 0; i < solutions.length; i++) {
			int[][] piece = MatrixCreator.get_pentomino_location_in_board(matrix[solutions[i]], pieces.length, width,
					height);
			MatrixCreator.print_board(piece);
		}
	}

}
