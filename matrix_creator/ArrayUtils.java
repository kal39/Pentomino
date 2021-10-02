package matrix_creator;

public class ArrayUtils {
	public static int[][] flip_2d_array(int[][] in) {
		int out[][] = new int[in.length][in[0].length];

		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				out[i][in[0].length - j - 1] = in[i][j];
			}
		}

		return out;
	}

	public static int[][] rotate_2d_array(int[][] in) {
		int[][] out = new int[in[0].length][in.length];

		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				out[j][out[0].length - i - 1] = in[i][j];
			}
		}

		return out;
	}

	public static boolean contains_identical_2d_array(int[][][] A, int[][] B) {
		for (int i = 0; i < A.length; i++) {
			if (is_identical_2d_array(A[i], B))
				return true;
		}

		return false;
	}

	public static boolean is_identical_2d_array(int[][] A, int[][] B) {
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

	public static void print_2d_array(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}

			System.out.println();
		}
	}

	public static int[][] add_row_to_2d_array(int[][] array, int[] row) {
		int[][] newArray = new int[array.length + 1][];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}

		newArray[newArray.length - 1] = row;

		return newArray;
	}

	public static int[][][] add_layer_to_3d_array(int[][][] array, int[][] layer) {
		int[][][] newArray = new int[array.length + 1][][];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}

		newArray[newArray.length - 1] = layer;

		return newArray;
	}

	public static int[][][] add_3d_arrays(int[][][] A, int[][][] B) {
		int[][][] C = new int[A.length + B.length][][];

		for (int i = 0; i < A.length; i++) {
			C[i] = A[i];
		}

		for (int i = 0; i < B.length; i++) {
			C[i + A.length] = B[i];
		}

		return C;
	}

	public static int[][] add_2d_arrays(int[][] A, int[][] B) {
		int[][] C = new int[A.length + B.length][];

		for (int i = 0; i < A.length; i++) {
			C[i] = A[i];
		}

		for (int i = 0; i < B.length; i++) {
			C[i + A.length] = B[i];
		}

		return C;
	}

	public static int[] add_1d_arrays(int[] A, int[] B) {
		int[] C = new int[A.length + B.length];

		for (int i = 0; i < A.length; i++) {
			C[i] = A[i];
		}

		for (int i = 0; i < B.length; i++) {
			C[i + A.length] = B[i];
		}

		return C;
	}

	public static int[] convert_2d_to_1d(int[][] in) {
		int[] out = new int[in.length * in[0].length];

		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[0].length; j++) {
				out[i + j * in[0].length] = in[i][j];
			}
		}

		return out;
	}
}
