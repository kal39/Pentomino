package puzzle_solver.algorithm_x;

import puzzle_solver.utils.*;

/*
 * The AlgorithmX class deals with solving the 2d array created by MatrixCreator
 * Requires the ArrayUtils class.
 */

public class AlgorithmX {
	/*
	 * Solves and the exact cover problem using algorithm X If it was not able to
	 * solve it, it will return a array of length 0
	 */

	public static int[] solve(int[][] matrix) {
		// add row numbers to the left of the matrix
		int[][] data = add_element_numbers(matrix);
		return algorithm_x(data);
	}

	private static int[] algorithm_x(int[][] matrix) {
		if (matrix.length == 0)
			return new int[0]; // failure, abandon branch

		// check if the matrix is solved)
		if (matrix_is_solved(matrix))
			return new int[] { matrix[0][0] }; // success!

		int c = column_with_least_ones(matrix);

		if (c == -1)
			return new int[0]; // failure, abandon branch

		// for each row r that has a 1 in column c
		for (int r = 0; r < matrix.length; r++) {
			if (matrix[r][c] == 1) {
				// save selected row number
				int rowNumber = matrix[r][0];

				// create a copy of the matrix so we don't overwrite it
				int[][] tmpMatrix = matrix.clone();

				int[] rowsToRemove = new int[0];
				int[] colsToRemove = new int[0];

				// for each column j that has a 1 in row r
				for (int j = 1; j < tmpMatrix[0].length; j++) {
					if (tmpMatrix[r][j] == 1) {
						// only add the column if it's a new column (we don't want duplicates)
						colsToRemove = ArrayUtils.add_distinct_element(colsToRemove, j);

						// for each row i that has a 1 in column j
						for (int i = 0; i < tmpMatrix.length; i++) {
							if (tmpMatrix[i][j] == 1) {
								// only add the row if it's a new row (we don't want duplicates)
								rowsToRemove = ArrayUtils.add_distinct_element(rowsToRemove, i);
							}
						}
					}
				}

				tmpMatrix = ArrayUtils.remove_row(tmpMatrix, rowsToRemove);

				// check if the matrix is still not empty after removing the columns
				if (tmpMatrix.length > 0)
					tmpMatrix = ArrayUtils.remove_col(tmpMatrix, colsToRemove);

				// recursive call
				int[] newPartialSolution = algorithm_x(tmpMatrix);

				// newPartialSolution will have a non-zero length if it succeeds
				if (newPartialSolution.length != 0)
					return ArrayUtils.add_element(newPartialSolution, rowNumber);

			}
		}

		return new int[0];
	}

	/*
	 * Adds row numbers (0~) to the 0th column of a matrix
	 */

	private static int[][] add_element_numbers(int[][] matrix) {
		int[][] newMatrix = new int[matrix.length][0];

		for (int i = 0; i < matrix.length; i++) {
			newMatrix[i] = ArrayUtils.add_element_to_start(matrix[i], i);
		}

		return newMatrix;
	}

	/*
	 * Checks if a matrix is filled with ones. If it is, it returns all the
	 * partialSolution of the matrix that remain. If not, it returns a array of
	 * length 0
	 */

	private static Boolean matrix_is_solved(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0)
					return false;
			}
		}

		return true;
	}

	/*
	 * Finds the column with the least number of 1s. If it is 0, the branch has to
	 * be abandoned, so it returns -1
	 */

	private static int column_with_least_ones(int[][] matrix) {
		int column = 0;
		int minOnes = -1;

		for (int i = 1; i < matrix[0].length; i++) {
			int ones = 0;

			for (int j = 0; j < matrix.length; j++) {
				if (matrix[j][i] == 1)
					ones++;
			}

			if (ones < minOnes || minOnes == -1) {
				column = i;
				minOnes = ones;
			}
		}

		if (minOnes == 0)
			return -1;

		return column;
	}
}
