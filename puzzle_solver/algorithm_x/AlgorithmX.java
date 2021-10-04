package puzzle_solver.algorithm_x;

import puzzle_solver.utils.*;

/*
 * The AlgorithmX class deals with solving the 2d array created by MatrixCreator
 * Requires the ArrayUtils class.
 */

public class AlgorithmX {

	/*
	 * For testing purposes:
	 */

	public static void main(String[] args) {
		int[][] test = { { 1, 0, 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 1, 1, 0, 1 },
				{ 0, 0, 1, 0, 1, 1, 0 }, { 0, 1, 1, 0, 0, 1, 1 }, { 0, 1, 0, 0, 0, 0, 1 } };

		int[] result = solve(test);

		System.out.print("Choose these rows: ");
		ArrayUtils.print(result);
	}

	/*
	 * Solves and the exact cover problem using algorithm X If it was not able to
	 * solve it, it will return a array of length 0
	 */

	public static int[] solve(int[][] matrix) {
		// add row numbers to the left of the matrix
		int[][] data = add_element_numbers(matrix);
		return algorithm_x(data);
	}

	public static int[] algorithm_x(int[][] matrix) {
		int[] rows = new int[0];

		// check if the matrix is solved)
		int[] remainingRows = check_if_solved(matrix);

		if (remainingRows.length > 0) {
			// add the remaining rows to the answer
			rows = ArrayUtils.add(rows, remainingRows);
			return rows; // success!
		}

		if (matrix.length == 0)
			return new int[0]; // failure, abandon branch

		int c = column_with_least_ones(matrix);

		if (c == -1)
			return new int[0]; // failure, abandon branch

		// for each row r that has a 1 in column c
		for (int r = 0; r < matrix.length; r++) {
			if (matrix[r][c] == 1) {
				// create a copy of the matrix so we don't overwrite it
				int[][] tmpMatrix = matrix.clone();

				// add row to answer
				rows = ArrayUtils.add_element(rows, tmpMatrix[r][0]);

				int[] rowsToRemove = new int[0];
				int[] colsToRemove = new int[0];

				// for each column j that has a 1 in row r
				for (int j = 1; j < tmpMatrix[0].length; j++) {
					if (tmpMatrix[r][j] == 1) {
						// only add the column if it's a new row (we don't want duplicates)
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

				// recursive call, it will return an array containing all answers if it succeeds
				int[] newRows = algorithm_x(tmpMatrix);

				// newRows will have a non-zero length if it succeeds
				if (newRows.length != 0) {
					rows = ArrayUtils.add(rows, newRows);
					return rows;
				}

				// remove row r we added earlier
				rows = ArrayUtils.remove_last_element(rows);
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
	 * Checks if a matrix is filled with ones. If it is, it returns all the rows of
	 * the matrix that remain. If not, it returns a array of length 0
	 */

	private static int[] check_if_solved(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0)
					return new int[0];
			}
		}

		int[] remainingRows = new int[matrix.length];

		for (int i = 0; i < matrix.length; i++) {
			remainingRows[i] = matrix[i][0];
		}

		return remainingRows;
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
