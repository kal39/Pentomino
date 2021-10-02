package matrix_creator;

import java.lang.Character;

/*
 * The TetrominoShapes class deals with generating all possible orientations for any given tetromino.
 * Requires the ArrayUtils class.
 */

public class TetrominoShapes {

	/*
	 * For testing purposes:
	 */

	public static void main(String[] args) {
		char[] allLetters = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N', 'F' };

		// print_tetromino(get_tetromino('L'));
		// print_all_tetrominos(get_tetromino_variations('L'));
		print_all_tetrominos(get_all_tetromino_variations(allLetters));
	}

	/*
	 * Returns all possible orientations for all tetrominos in an array
	 */

	public static int[][][] get_all_tetromino_variations(char[] pieces) {
		int[][][] configs = new int[0][0][0];

		for (int i = 0; i < pieces.length; i++) {
			configs = ArrayUtils.add_3dArrays(configs, get_tetromino_variations(pieces[i]));
		}

		return configs;
	}

	/*
	 * Returns all possible orientations for a single tetrominos
	 */

	public static int[][][] get_tetromino_variations(char pieceName) {
		int[][] tetromino = get_tetromino(pieceName);

		int[][][] configs = new int[0][0][0];

		for (int i = 0; i < 4; i++) {
			configs = add_tetromino(configs, tetromino);
			configs = add_tetromino(configs, ArrayUtils.flip2dArray(tetromino));

			tetromino = ArrayUtils.rotate2dArray(tetromino);
		}

		return configs;
	}

	/*
	 * Returns a 2d array containing the shape of a tetromino
	 */

	public static int[][] get_tetromino(char pieceName) {
		pieceName = Character.toUpperCase(pieceName);

		switch (pieceName) {
			case 'X':
				return shapes[0];
			case 'I':
				return shapes[1];
			case 'Z':
				return shapes[2];
			case 'T':
				return shapes[3];
			case 'U':
				return shapes[4];
			case 'V':
				return shapes[5];
			case 'W':
				return shapes[6];
			case 'Y':
				return shapes[7];
			case 'L':
				return shapes[8];
			case 'P':
				return shapes[9];
			case 'N':
				return shapes[10];
			case 'F':
				return shapes[11];
		}

		System.out.println("ERROR: could not find matching piece");
		return new int[1][1];
	}

	/*
	 * Adds a tetromino to an array after checking if it's a new configuration
	 */

	private static int[][][] add_tetromino(int[][][] array, int[][] tetromino) {
		if (ArrayUtils.containsIdentical2dArray(array, tetromino)) {
			return array;
		} else {
			return ArrayUtils.add_layer_to_3dArray(array, tetromino);
		}
	}

	private static void print_all_tetrominos(int[][][] tetrominos) {
		for (int i = 0; i < tetrominos.length; i++) {
			print_tetromino(tetrominos[i]);
			System.out.println();
		}
	}

	private static void print_tetromino(int[][] tetromino) {
		for (int i = 0; i < tetromino.length; i++) {
			for (int j = 0; j < tetromino[0].length; j++) {
				if (tetromino[i][j] == 1)
					System.out.print("██");
				else
					System.out.print("  ");
			}

			System.out.println();
		}
	}

	/*
	 * Tetromino shapes:
	 */

	private static int[][][] shapes = { //
			{ { 0, 1, 0 }, { 1, 1, 1 }, { 0, 1, 0 } }, // X
			{ { 1 }, { 1 }, { 1 }, { 1 }, { 1 } }, // I
			{ { 0, 1, 1 }, { 0, 1, 0 }, { 1, 1, 0 } }, // Z
			{ { 1, 1, 1 }, { 0, 1, 0 }, { 0, 1, 0 } }, // T
			{ { 1, 1 }, { 1, 0 }, { 1, 1 } }, // U
			{ { 1, 1, 1 }, { 1, 0, 0 }, { 1, 0, 0 } }, // V
			{ { 0, 0, 1 }, { 0, 1, 1 }, { 1, 1, 0 } }, // W
			{ { 1, 0 }, { 1, 1 }, { 1, 0 }, { 1, 0 } }, // Y
			{ { 1, 0 }, { 1, 0 }, { 1, 0 }, { 1, 1 } }, // L
			{ { 1, 1 }, { 1, 1 }, { 1, 0 } }, // P
			{ { 0, 1 }, { 0, 1 }, { 1, 1 }, { 1, 0 } }, // N
			{ { 0, 1, 1 }, { 1, 1, 0 }, { 0, 1, 0 } } // F
	};
}
