package puzzle_solver.utils;

/*
 * The PentominoShapes class deals with generating all possible orientations for any given pentomino.
 * Requires the ArrayUtils class.
 */

public class PentominoShapes {

	/*
	 * For testing purposes:
	 */

	public static void main(String[] args) {
		// char[] allLetters = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N',
		// 'F' };

		// print_pentomino(get_pentomino('L'));
		// print_all_pentominos(get_pentomino_variations('L'));
	}

	/*
	 * Returns all possible orientations for a single pentominos
	 */

	public static int[][][] get_pentomino_variations(char pieceName) {
		int[][] pentomino = get_pentomino(pieceName);

		int[][][] configs = new int[0][0][0];

		for (int i = 0; i < 4; i++) {
			// check for duplicates and add non-flipped pentomino
			configs = add_pentomino(configs, pentomino);

			// check for duplicates and add flipped pentomino
			configs = add_pentomino(configs, ArrayUtils.flip(pentomino));

			// rotate pentomino
			pentomino = ArrayUtils.rotate(pentomino);
		}

		return configs;
	}

	/*
	 * Returns a 2d array containing the shape of a pentomino
	 */

	public static int[][] get_pentomino(char pieceName) {
		pieceName = java.lang.Character.toUpperCase(pieceName);

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
	 * Adds a pentomino to an array after checking if it's a new configuration
	 */

	private static int[][][] add_pentomino(int[][][] array, int[][] pentomino) {
		// checks for duplication before adding pentomino
		if (ArrayUtils.contains_identical_array(array, pentomino)) {
			return array;
		} else {
			return ArrayUtils.add_element(array, pentomino);
		}
	}

	private static void print_all_pentominos(int[][][] pentominos) {
		for (int i = 0; i < pentominos.length; i++) {
			print_pentomino(pentominos[i]);
			System.out.println();
		}
	}

	public static void print_pentomino(int[][] pentomino) {
		for (int i = 0; i < pentomino.length; i++) {
			for (int j = 0; j < pentomino[0].length; j++) {
				if (pentomino[i][j] == 1)
					System.out.print("██");
				else
					System.out.print("  ");
			}

			System.out.println();
		}
	}

	/*
	 * Pentomino shapes:
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
