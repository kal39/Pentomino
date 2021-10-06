package puzzle_solver.utils;

import java.util.Arrays;

public class ArrayUtils {
	public static int[][] flip(int[][] in) {
		int out[][] = new int[in.length][in[0].length];

		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				out[i][in[0].length - j - 1] = in[i][j];
			}
		}

		return out;
	}

	public static int[][] rotate(int[][] in) {
		int[][] out = new int[in[0].length][in.length];

		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				out[j][out[0].length - i - 1] = in[i][j];
			}
		}

		return out;
	}

	public static boolean contains_identical_array(int[][][] A, int[][] B) {
		for (int i = 0; i < A.length; i++) {
			if (is_identical(A[i], B))
				return true;
		}

		return false;
	}

	public static boolean is_identical(int[][] A, int[][] B) {
		if (A.length != B.length || A[0].length != A[0].length)
			return false;

		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				if (A[i][j] != B[i][j])
					return false;
			}
		}

		return true;
	}

	public static void print(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}

	public static void print(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static int[] add_distinct_element(int[] array, int element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == element)
				return array;
		}

		return add_element(array, element);
	}

	public static int[] add_element_to_start(int[] array, int element) {
		int[] newArray = new int[array.length + 1];

		newArray[0] = element;

		for (int i = 0; i < array.length; i++) {
			newArray[i + 1] = array[i];
		}
		return newArray;
	}

	public static int[] remove_last_element(int[] array) {
		int[] newArray = new int[array.length - 1];

		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static int[] add_element(int[] array, int element) {
		int[] newArray = new int[array.length + 1];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		newArray[newArray.length - 1] = element;

		return newArray;
	}

	public static int[][] add_element(int[][] array, int[] row) {
		int[][] newArray = new int[array.length + 1][];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		newArray[newArray.length - 1] = row;

		return newArray;
	}

	public static int[][][] add_element(int[][][] array, int[][] layer) {
		int[][][] newArray = new int[array.length + 1][][];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		newArray[newArray.length - 1] = layer;

		return newArray;
	}

	public static int[][][] add(int[][][] A, int[][][] B) {
		int[][][] C = new int[A.length + B.length][][];

		for (int i = 0; i < A.length; i++) {
			C[i] = A[i];
		}
		for (int i = 0; i < B.length; i++) {
			C[i + A.length] = B[i];
		}

		return C;
	}

	public static int[][] add(int[][] A, int[][] B) {
		int[][] C = new int[A.length + B.length][];

		for (int i = 0; i < A.length; i++) {
			C[i] = A[i];
		}
		for (int i = 0; i < B.length; i++) {
			C[i + A.length] = B[i];
		}

		return C;
	}

	public static int[] add(int[] A, int[] B) {
		int[] C = new int[A.length + B.length];

		for (int i = 0; i < A.length; i++) {
			C[i] = A[i];
		}
		for (int i = 0; i < B.length; i++) {
			C[i + A.length] = B[i];
		}

		return C;
	}

	public static int[] convert(int[][] in) {
		// int[] out = new int[in.length * in[0].length];
		int[] out = new int[0];

		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				out = add_element(out, in[i][j]);
			}
		}

		return out;
	}

	public static int[][] remove_row(int in[][], int row) {
		int[][] out = new int[in.length - 1][];

		for (int i = 0; i < row; i++) {
			out[i] = in[i];
		}
		for (int i = row; i < out.length; i++) {
			out[i] = in[i + 1];
		}

		return out;
	}

	public static int[][] remove_row(int in[][], int rows[]) {
		Arrays.sort(rows);

		for (int i = 0; i < rows.length; i++) {
			in = ArrayUtils.remove_row(in, rows[i] - i);
		}

		return in;
	}

	public static int[][] remove_col(int in[][], int column) {
		int[][] out = new int[in.length][];

		for (int i = 0; i < out.length; i++) {
			out[i] = new int[in[0].length - 1];

			for (int j = 0; j < column; j++) {
				out[i][j] = in[i][j];
			}
			for (int j = column; j < out[0].length; j++) {
				out[i][j] = in[i][j + 1];
			}
		}

		return out;
	}

	public static int[][] remove_col(int in[][], int columns[]) {
		Arrays.sort(columns);

		for (int i = 0; i < columns.length; i++) {
			in = ArrayUtils.remove_col(in, columns[i] - i);
		}

		return in;
	}
}
