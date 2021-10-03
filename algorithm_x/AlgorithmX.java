package algorithm_x;

/*
If A is empty, the problem is solved; terminate successfully.
Otherwise choose a column, c (deterministically).
Choose a row, r, such that A[r, c] = 1 (non-deterministically).
Include r in the partial solution.
For each j such that A[r, j] = 1,
delete column j from matrix A;
for each i such that A[i, j] = 1,
delete row i from matrix A.
Repeat this algorithm recursively on the reduced matrix A.
*/

import java.util.Arrays;
import utils.ArrayUtils;

public class AlgorithmX {
	public static void main(String[] args) {
		int[][] test = { { 1, 0, 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 1, 1, 0, 1 },
				{ 0, 0, 1, 0, 1, 1, 0 }, { 0, 1, 1, 0, 0, 1, 1 }, { 0, 1, 0, 0, 0, 0, 1 } };

		int[] result = solve(test);

		System.out.print("Choose these rows: ");
		ArrayUtils.print_1d_array(result);
	}

	public static int[] solve(int[][] matrix) {
		int[][] data = add_row_numbers(matrix);

		XResult result = algorithm_x(data);

		// result.rows = ArrayUtils.add_element_to_1d_array(result.rows, 60);

		return result.rows;
	}

	public static XResult algorithm_x(int[][] matrix) {
		XResult result = new XResult();

		System.out.println("Current matrix:");
		ArrayUtils.print_2d_array(matrix);

		int[] remainingRows = check_if_matrix_is_filled(matrix);

		if (remainingRows.length > 0) {
			for (int i = 0; i < remainingRows.length; i++) {
				result.add_row(remainingRows[i]);
			}

			return new XResult(true, result.rows);
		}

		if (matrix.length == 0) {
			System.out.println("DISCARDING BRANCH\n");
			return new XResult(false);
		}

		int c = column_with_least_ones(matrix);

		if (c == -1)
			return new XResult(false);

		System.out.println("Looking at c = " + c);

		for (int r = 0; r < matrix.length; r++) {
			if (matrix[r][c] == 1) {
				int[][] tmpMatrix = matrix.clone();

				System.out.println("Looking at r = " + r + " (" + tmpMatrix[r][0] + ")");

				result.add_row(tmpMatrix[r][0]);

				int[] rowsToRemove = new int[0];
				int[] colsToRemove = new int[0];

				for (int j = 1; j < tmpMatrix[0].length; j++) {
					if (tmpMatrix[r][j] == 1) {
						// System.out.println("Going to remove column " + j);
						colsToRemove = ArrayUtils.add_distinct_element_to_1d_array(colsToRemove, j);

						for (int i = 0; i < tmpMatrix.length; i++) {
							if (tmpMatrix[i][j] == 1) {
								// System.out.println(" Going to remove row " + i);
								rowsToRemove = ArrayUtils.add_distinct_element_to_1d_array(rowsToRemove, i);
							}
						}
					}
				}

				Arrays.sort(rowsToRemove);
				Arrays.sort(colsToRemove);

				System.out.println("Removing cols:");
				ArrayUtils.print_1d_array(colsToRemove);
				System.out.println("Removing rows:");
				ArrayUtils.print_1d_array(rowsToRemove);

				for (int j = 0; j < rowsToRemove.length; j++) {
					tmpMatrix = ArrayUtils.remove_row(tmpMatrix, rowsToRemove[j] - j);
				}

				if (tmpMatrix.length > 0) {
					for (int j = 0; j < colsToRemove.length; j++) {
						tmpMatrix = ArrayUtils.remove_col(tmpMatrix, colsToRemove[j] - j);
					}
				}

				XResult newResult = algorithm_x(tmpMatrix);

				if (newResult.result == true) {
					result.result = true;
					result.add_row(newResult.rows);
					return result;
				}
			}
		}

		return new XResult(false);
	}

	private static int[][] add_row_numbers(int[][] matrix) {
		int[][] newMatrix = new int[matrix.length][0];

		for (int i = 0; i < matrix.length; i++) {
			newMatrix[i] = ArrayUtils.add_element_to_start_of_1d_array(matrix[i], i);
		}

		return newMatrix;
	}

	private static int[] check_if_matrix_is_filled(int[][] matrix) {
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
