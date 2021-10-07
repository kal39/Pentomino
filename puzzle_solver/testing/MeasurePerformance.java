package puzzle_solver.testing;

import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import puzzle_solver.algorithm_x.AlgoXSolver;
import puzzle_solver.branching.BranchingAlgorithmPruning;
import puzzle_solver.basic.Search;

public class MeasurePerformance {
	static final int RUNS = 10;

	// 12 x 5
	// static final int WIDTH = 12;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L',
	// 'P', 'N', 'F' };

	// 11 x 5
	// static final int WIDTH = 11;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L',
	// 'P', 'N' };

	// 10 x 5
	// static final int WIDTH = 10;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L',
	// 'P' };

	// 9 x 5
	// static final int WIDTH = 9;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'L', 'F', 'Z', 'U', 'X', 'P', 'Y', 'N', 'W' };

	// 8 x 5
	// static final int WIDTH = 8;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'L', 'F', 'Z', 'U', 'X', 'P', 'Y', 'N' };

	// 7 x 5
	// static final int WIDTH = 7;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'L', 'F', 'Z', 'U', 'X', 'P', 'Y' };

	// 6 x 5
	// static final int WIDTH = 6;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'T', 'W', 'I', 'Z', 'Y', 'L' };

	// // 5 x 5
	// static final int WIDTH = 5;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'F', 'T', 'V', 'Z', 'I' };

	// 4 x 5
	// static final int WIDTH = 4;
	// static final int HEIGHT = 5;
	// static final char[] PIECES = { 'Y', 'L', 'T', 'P' };

	// 3 x 5
	static final int WIDTH = 3;
	static final int HEIGHT = 5;
	static final char[] PIECES = { 'U', 'V', 'P' };

	public static void main(String[] args) {
		measurePerformance();
	}

	static void measurePerformance() {
		System.out.println("Measuring performance for pieces:");

		for (int i = 0; i < PIECES.length; i++) {
			System.out.print(PIECES[i] + ", ");
		}
		System.out.println();

		System.out.println("in a " + WIDTH + "x" + HEIGHT + " board\n");

		char[][] letters = randomize_order(PIECES, RUNS);

		// basic

		System.out.println("Measuring basic algorithm...");

		long basicStart = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			Search.search(WIDTH, HEIGHT, letters[i]);
		}

		long basicEnd = System.nanoTime();

		double basicTime = (double) ((basicEnd - basicStart) / (long) RUNS) / 1000000;

		// branching

		System.out.println("Measuring branching algorithm...");

		long branchingStart = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			BranchingAlgorithmPruning.solve(letters[i], WIDTH, HEIGHT);
		}

		long branchingEnd = System.nanoTime();

		double branchingTime = (double) ((branchingEnd - branchingStart) / (long) RUNS) / 1000000;

		// algorithm X

		System.out.println("Measuring Algorithm X...");

		long algoXStart = System.nanoTime();
		for (int i = 0; i < RUNS; i++) {
			AlgoXSolver.create_and_solve(WIDTH, HEIGHT, letters[i]);
		}
		long algoXEnd = System.nanoTime();

		double algoXTime = (double) ((algoXEnd - algoXStart) / (long) RUNS) / 1000000;

		System.out.println("\nRESULTS:");
		System.out.println("Basic algorithm average: " + basicTime + "ms");
		System.out.println("Branching algorithm average: " + branchingTime + "ms");
		System.out.println("Algorithm X average: " + algoXTime + "ms");

	}

	static char[][] randomize_order(char[] in, int size) {
		char[][] out = new char[size][0];

		out[0] = shuffleArray(in);

		for (int i = 1; i < size; i++) {
			out[i] = shuffleArray(out[i - 1].clone());
		}

		return out;
	}

	static char[] shuffleArray(char[] array) {
		Random rnd = ThreadLocalRandom.current();
		for (int i = array.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			char a = array[index];
			array[index] = array[i];
			array[i] = a;
		}

		return array;
	}

}
