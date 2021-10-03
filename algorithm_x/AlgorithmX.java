package algorithm_x;

import java.util.Arrays;
import utils.*;

public class AlgorithmX {
	public static void main(String[] args) {
		int[][] test = { { 1, 0, 0, 1, 0, 0, 1 }, { 1, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 1, 1, 0, 1 },
				{ 0, 0, 1, 0, 1, 1, 0 }, { 0, 1, 1, 0, 0, 1, 1 }, { 0, 1, 0, 0, 0, 0, 1 } };

		int[] result = solve(test);

		System.out.print("Choose these rows: ");
		ArrayUtils.print(result);
	}

	public static int[] solve(int[][] matrix) {
		int[][] data = add_element_numbers(matrix);
		return algorithm_x(data);
	}

	public static int[] algorithm_x(int[][] matrix) {
		int[] rows = new int[0];

		int[] remainingRows = check_if_matrix_is_filled(matrix);

		if (remainingRows.length > 0) {
			rows = ArrayUtils.add(rows, remainingRows);
			return rows;
		}

		if (matrix.length == 0)
			return new int[0];

		int c = column_with_least_ones(matrix);

		if (c == -1)
			return new int[0];

		for (int r = 0; r < matrix.length; r++) {
			if (matrix[r][c] == 1) {
				int[][] tmpMatrix = matrix.clone();

				rows = ArrayUtils.add_element(rows, tmpMatrix[r][0]);

				int[] rowsToRemove = new int[0];
				int[] colsToRemove = new int[0];

				for (int j = 1; j < tmpMatrix[0].length; j++) {
					if (tmpMatrix[r][j] == 1) {
						colsToRemove = ArrayUtils.add_distinct_element(colsToRemove, j);

						for (int i = 0; i < tmpMatrix.length; i++) {
							if (tmpMatrix[i][j] == 1) {
								rowsToRemove = ArrayUtils.add_distinct_element(rowsToRemove, i);
							}
						}
					}
				}

				Arrays.sort(rowsToRemove);
				Arrays.sort(colsToRemove);

				for (int j = 0; j < rowsToRemove.length; j++) {
					tmpMatrix = ArrayUtils.remove_row(tmpMatrix, rowsToRemove[j] - j);
				}

				if (tmpMatrix.length > 0) {
					for (int j = 0; j < colsToRemove.length; j++) {
						tmpMatrix = ArrayUtils.remove_col(tmpMatrix, colsToRemove[j] - j);
					}
				}

				int[] newRows = algorithm_x(tmpMatrix);

				if (newRows.length != 0) {
					rows = ArrayUtils.add(rows, newRows);
					return rows;
				}

				rows = ArrayUtils.remove_last_element(rows);
			}
		}

		return new int[0];
	}

	private static int[][] add_element_numbers(int[][] matrix) {
		int[][] newMatrix = new int[matrix.length][0];

		for (int i = 0; i < matrix.length; i++) {
			newMatrix[i] = ArrayUtils.add_element_to_start(matrix[i], i);
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
