package puzzle_solver.solver;

import puzzle_solver.algorithm_x.*;
import puzzle_solver.matrix_creator.*;
// import puzzle_solver.utils.*;

public class Solver {
	// change these parameters for testing:
	static final int WIDTH = 12;
	static final int HEIGHT = 5;
	static final char[] PIECES = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F' };

	public static void main(String[] args) {
		create_and_solve(WIDTH, HEIGHT, PIECES);
		// measurePerformance();
	}

	/*
	 * This method actually solves the puzzle
	 */

	public static void create_and_solve(int width, int height, char[] pieces) {
		int[][] matrix = MatrixCreator.create(width, height, pieces);

		int[] solutions = AlgorithmX.solve(matrix);

		for (int i = 0; i < solutions.length; i++) {
			int[][] piece = MatrixCreator.get_pentomino_location_in_board(matrix[solutions[i]], pieces.length, width,
					height);
			MatrixCreator.print_board(piece);
		}
	}

	/*
	 * This method just measures how long each part of the solver takes (it takes an
	 * average of 100 times)
	 */

	public static void measurePerformance() {
		// measure matrix creation time
		long t1 = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			MatrixCreator.create(WIDTH, HEIGHT, PIECES);
		}
		long t2 = System.nanoTime();

		// measure solving time
		long t3 = System.nanoTime();
		int[][] matrix = MatrixCreator.create(WIDTH, HEIGHT, PIECES);

		for (int i = 0; i < 100; i++) {
			AlgorithmX.solve(matrix);
		}
		long t4 = System.nanoTime();

		double ct = (double) ((t2 - t1) / (long) 100) / 1000000000;
		double st = (double) ((t4 - t3) / (long) 100) / 1000000000;

		System.out.println("Matrix creation time (average): " + ct + "s");
		System.out.println("Solving time (average): " + st + "s");
	}

}
