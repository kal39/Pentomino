package puzzle_solver.algorithm_x.solver;

import puzzle_solver.algorithm_x.algorithm_x.*;
import puzzle_solver.algorithm_x.matrix_creator.*;
// import puzzle_solver.utils.*;
import puzzle_solver.utils.ArrayUtils;

public class Solver {
	// change these parameters for testing:
	static final int WIDTH = 10;
	static final int HEIGHT = 6;
	static final char[] PIECES = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F' };
	static boolean print = false;

	public static void main(String[] args) {
		// create_and_solve(WIDTH, HEIGHT, PIECES);
		measurePerformance();
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

			if (print)
				MatrixCreator.print_board(piece);
		}

		return solutions2D;
	}

	/*
	 * This method just measures how long each part of the solver takes (it takes an
	 * average of 100 times)
	 */

	public static void measurePerformance() {
		int runCount = 100;

		System.out.println("Measuring execution time");

		// measure matrix creation time
		long t1 = System.nanoTime();
		for (int i = 0; i < runCount; i++) {
			MatrixCreator.create(WIDTH, HEIGHT, PIECES);
		}
		long t2 = System.nanoTime();

		// measure solving time
		long t3 = System.nanoTime();
		int[][] matrix = MatrixCreator.create(WIDTH, HEIGHT, PIECES);

		for (int i = 0; i < runCount; i++) {
			AlgorithmX.solve(matrix);
		}
		long t4 = System.nanoTime();

		double ct = (double) ((t2 - t1) / (long) runCount) / 1000000000;
		double xt = (double) ((t4 - t3) / (long) runCount) / 1000000000;

		System.out.println("\nMatrix creation time (average): " + ct + "s");
		System.out.println("Algorithm X time (average): " + xt + "s");

		// measure total time

		long t5 = System.nanoTime();
		for (int i = 0; i < runCount; i++) {
			AlgorithmX.solve(MatrixCreator.create(WIDTH, HEIGHT, PIECES));
		}
		long t6 = System.nanoTime();

		double tt = (double) ((t6 - t5) / (long) runCount) / 1000000000;

		System.out.println("\nTotal solving time (average): " + tt + "s");

	}

}
