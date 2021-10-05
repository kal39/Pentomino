package puzzle_solver.basic;

/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import java.util.Random;

/**
 * This class includes the methods to support the search of a solution.
 */

public class Search {
	// Static UI class to display the board
	// public static UI ui = new UI(width, height, 50);

	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
	public static void main(String[] args) {
		char[] input = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P', 'N' };
		search(12, 5, input);
	}

	/**
	 * Helper function which starts a basic search algorithm
	 */
	public static int[][] search(int width, int height, char[] input) {
		// Initialize an empty board
		int[][] field = new int[height][width];

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				// -1 in the state matrix corresponds to empty square
				// Any positive number identifies the ID of the pentomino
				field[i][j] = -1;
			}
		}
		// Start the basic search
		return basicSearch(field, height, width, input);
	}

	/**
	 * Get as input the character representation of a pentomino and translate it
	 * into its corresponding numerical value (ID)
	 * 
	 * @param character a character representating a pentomino
	 * @return the corresponding ID (numerical value)
	 */
	private static int characterToID(char character) {
		int pentID = -1;
		if (character == 'X') {
			pentID = 0;
		} else if (character == 'I') {
			pentID = 1;
		} else if (character == 'Z') {
			pentID = 2;
		} else if (character == 'T') {
			pentID = 3;
		} else if (character == 'U') {
			pentID = 4;
		} else if (character == 'V') {
			pentID = 5;
		} else if (character == 'W') {
			pentID = 6;
		} else if (character == 'Y') {
			pentID = 7;
		} else if (character == 'L') {
			pentID = 8;
		} else if (character == 'P') {
			pentID = 9;
		} else if (character == 'N') {
			pentID = 10;
		} else if (character == 'F') {
			pentID = 11;
		}
		return pentID;
	}

	/**
	 * Basic implementation of a search algorithm. It is not a bruto force
	 * algorithms (it does not check all the posssible combinations) but randomly
	 * takes possible combinations and positions to find a possible solution. The
	 * solution is not necessarily the most efficient one This algorithm can be very
	 * time-consuming
	 * 
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
	private static int[][] basicSearch(int[][] field, int width, int height, char input[]) {
		Random random = new Random();
		boolean solutionFound = false;

		while (!solutionFound) {
			solutionFound = true;

			// Empty board again to find a solution
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					field[i][j] = -1;
				}
			}

			// Put all pentominoes with random rotation/flipping on a random position on the
			// board
			for (int i = 0; i < input.length; i++) {

				// Choose a pentomino and randomly rotate/flip it
				int pentID = characterToID(input[i]);
				int mutation = random.nextInt(PentominoDatabase.data[pentID].length);
				int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

				// Randomly generate a position to put the pentomino on the board
				int x;
				int y;
				if (width < pieceToPlace.length) {
					// this particular rotation of the piece is too long for the field
					x = -1;
				} else if (width == pieceToPlace.length) {
					// this particular rotation of the piece fits perfectly into the width of the
					// field
					x = 0;
				} else {
					// there are multiple possibilities where to place the piece without leaving the
					// field
					x = random.nextInt(width - pieceToPlace.length + 1);
				}

				if (height < pieceToPlace[0].length) {
					// this particular rotation of the piece is too high for the field
					y = -1;
				} else if (height == pieceToPlace[0].length) {
					// this particular rotation of the piece fits perfectly into the height of the
					// field
					y = 0;
				} else {
					// there are multiple possibilities where to place the piece without leaving the
					// field
					y = random.nextInt(height - pieceToPlace[0].length + 1);
				}

				// If there is a possibility to place the piece on the field, do it
				if (x >= 0 && y >= 0) {
					addPiece(field, pieceToPlace, pentID, x, y);
				}
			}

			solutionFound = true;

			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[0].length; j++) {
					if (field[i][j] == -1)
						solutionFound = false;
				}
			}

			if (solutionFound) {
				// display the field
				// ui.setState(field);
				// System.out.println("Solution found");
				return field;
			}
		}

		return new int[0][0];
	}

	/**
	 * Adds a pentomino to the position on the field (overriding current board at
	 * that position)
	 * 
	 * @param field   a matrix representing the board to be fulfilled with
	 *                pentominoes
	 * @param piece   a matrix representing the pentomino to be placed in the board
	 * @param pieceID ID of the relevant pentomino
	 * @param x       x position of the pentomino
	 * @param y       y position of the pentomino
	 */
	public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y) {
		for (int i = 0; i < piece.length; i++) // loop over x position of pentomino
		{
			for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
			{
				if (piece[i][j] == 1) {
					// Add the ID of the pentomino to the board if the pentomino occupies this
					// square
					field[x + i][y + j] = pieceID;
				}
			}
		}
	}

}