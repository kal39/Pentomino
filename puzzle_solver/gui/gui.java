package puzzle_solver.gui;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
// import java.util.Random;

import puzzle_solver.algorithm_x.solver.*;
import puzzle_solver.basic.*;
import puzzle_solver.branching.*;

public class gui {
	static final int width = 800;
	static final int height = 600;

	static final int lettersFieldWidth = width - width / 8;
	static final int lettersFieldOffsetX = width / 16;
	static final int lettersFieldOffsetY = 20 + width / 16;

	public static void main(String[] args) {
		run_gui();
	}

	public static void run_gui() {
		JFrame f = new JFrame("Pentomino Puzzle Solver");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);

		JLabel widthLabel = new JLabel("Width:");
		widthLabel.setBounds(10, height - 60, 60, 20);
		JTextField widthField = new JTextField("12");
		widthField.setBounds(60, height - 60, 40, 20);

		JLabel heightLabel = new JLabel("Height:");
		heightLabel.setBounds(110, height - 60, 60, 20);
		JTextField heightField = new JTextField("5");
		heightField.setBounds(110 + 60, height - 60, 40, 20);

		JLabel lettersFieldLabel = new JLabel("Pentominos:");
		lettersFieldLabel.setBounds(220, height - 60, 100, 20);
		JTextField lettersField = new JTextField("X I Z T U V W Y L P N F");
		lettersField.setBounds(320, height - 60, 200, 20);

		String[] algorithms = { "Basic", "Branching", "Algorithm X" };
		JComboBox algorithmSelector = new JComboBox(algorithms);
		algorithmSelector.setSelectedIndex(2);
		algorithmSelector.setBounds(530, height - 60, 140, 20);

		JLabel timeLabel = new JLabel("");
		timeLabel.setBounds(10, height - 90, 500, 20);

		JButton runButton = new JButton("RUN");
		runButton.setBounds(680, height - 60, 110, 20);

		Drawing canvas = new Drawing();
		canvas.setSize(width, height - 95);
		canvas.height = height - 95;
		canvas.width = width;

		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String letterString = lettersField.getText();
				String widthString = widthField.getText();
				String heightString = heightField.getText();

				f.remove(canvas);

				long t1 = System.nanoTime();

				switch (algorithmSelector.getSelectedIndex()) {
					case 0:
						System.out.println("Running basic algorithm");
						canvas.pieces[0] = parse_and_run_basic(letterString, widthString, heightString);
						break;
					case 1:
						System.out.println("Running branching algorithm");
						canvas.pieces[0] = parse_and_run_branching(letterString, widthString, heightString);
						break;
					case 2:
						System.out.println("Running algorithm X");
						canvas.pieces = parse_and_run_algo_x(letterString, widthString, heightString);
						break;
				}

				long t2 = System.nanoTime();

				double time = (double) (t2 - t1) / 1000000000;

				canvas.resultType = algorithmSelector.getSelectedIndex();

				f.add(canvas);
				timeLabel.setText(time + "s");
			}
		});

		f.add(lettersField);
		f.add(lettersFieldLabel);
		f.add(widthLabel);
		f.add(widthField);
		f.add(heightLabel);
		f.add(heightField);
		f.add(runButton);
		f.add(algorithmSelector);
		f.add(timeLabel);

		f.add(canvas);

		f.setSize(800, 600);
		f.setLayout(null);
		f.setVisible(true);
		f.getRootPane().setDefaultButton(runButton);
	}

	private static int[][] parse_and_run_basic(String lettersString, String widthString, String heightString) {
		char[] letters = new char[0];

		for (int i = 0; i < lettersString.length(); i++) {
			char l = java.lang.Character.toUpperCase(lettersString.charAt(i));
			if (l == 'X' || l == 'I' || l == 'Z' || l == 'T' || l == 'U' || l == 'V' || l == 'W' || l == 'Y' || l == 'L'
					|| l == 'P' || l == 'N' || l == 'F')
				letters = add_char(letters, l);
		}

		int widthInt = Integer.parseInt(widthString);
		int heightInt = Integer.parseInt(heightString);

		return Search.search(widthInt, heightInt, letters);
	}

	private static int[][] parse_and_run_branching(String lettersString, String widthString, String heightString) {
		char[] letters = new char[0];

		for (int i = 0; i < lettersString.length(); i++) {
			char l = java.lang.Character.toUpperCase(lettersString.charAt(i));
			if (l == 'X' || l == 'I' || l == 'Z' || l == 'T' || l == 'U' || l == 'V' || l == 'W' || l == 'Y' || l == 'L'
					|| l == 'P' || l == 'N' || l == 'F')
				letters = add_char(letters, l);
		}

		int widthInt = Integer.parseInt(widthString);
		int heightInt = Integer.parseInt(heightString);

		int[][] board = new int[heightInt][widthInt];

		return branching_algorithm_pruning.solve(board, letters);
	}

	private static int[][][] parse_and_run_algo_x(String lettersString, String widthString, String heightString) {
		char[] letters = new char[0];

		for (int i = 0; i < lettersString.length(); i++) {
			char l = java.lang.Character.toUpperCase(lettersString.charAt(i));
			if (l == 'X' || l == 'I' || l == 'Z' || l == 'T' || l == 'U' || l == 'V' || l == 'W' || l == 'Y' || l == 'L'
					|| l == 'P' || l == 'N' || l == 'F')
				letters = add_char(letters, l);
		}

		int widthInt = Integer.parseInt(widthString);
		int heightInt = Integer.parseInt(heightString);

		// if (!(width > 0 && height > 0 && letters.length > 0))
		return Solver.create_and_solve(widthInt, heightInt, letters);

	}

	public static char[] add_char(char[] array, char element) {
		char[] newArray = new char[array.length + 1];

		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		newArray[newArray.length - 1] = element;

		return newArray;
	}
}

class Drawing extends Canvas {
	public int[][][] pieces = new int[0][0][0];
	public int width;
	public int height;
	public int resultType;

	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		if (pieces.length != 0) {
			switch (resultType) {
				case 0:
					paint_basic(g);
					break;
				case 1:
					paint_basic(g);
					break;
				case 2:
					paint_algo_x(g);
					break;
			}

		}

	}

	private void paint_basic(Graphics g) {
		int colSize = (width - 50) / pieces[0][0].length;
		int rowSize = (height - 50) / pieces[0].length;

		if (colSize < rowSize)
			rowSize = colSize;

		if (rowSize < colSize)
			colSize = rowSize;

		int xOffset = (width - colSize * pieces[0][0].length) / 2;
		int yOffset = (height - rowSize * pieces[0].length) / 2;

		for (int j = 0; j < pieces[0].length; j++) {
			for (int k = 0; k < pieces[0][0].length; k++) {
				if (pieces[0][j][k] != -1) {
					Color color = Color.getHSBColor((float) pieces[0][j][k] * 1.0f / 12.0f, 1.0f, 0.5f);

					g.setColor(color);
					g.fillRect(xOffset + colSize * k, yOffset + rowSize * j, rowSize, colSize);
					g.setColor(Color.black);
					g.drawRect(xOffset + colSize * k, yOffset + rowSize * j, rowSize, colSize);

				}
			}

		}
	}

	private void paint_algo_x(Graphics g) {

		int colSize = (width - 50) / pieces[0][0].length;
		int rowSize = (height - 50) / pieces[0].length;

		if (colSize < rowSize)
			rowSize = colSize;

		if (rowSize < colSize)
			colSize = rowSize;

		int xOffset = (width - colSize * pieces[0][0].length) / 2;
		int yOffset = (height - rowSize * pieces[0].length) / 2;

		for (int i = 0; i < pieces.length; i++) {
			float colorMask = 1.0f / (float) pieces.length * i;

			Color color = Color.getHSBColor(colorMask, 0.5f + colorMask * 0.5f, 0.5f + colorMask * 0.5f);

			// so we don't get similar color close to each other
			int tmpI = i;
			if (i % 2 == 1)
				tmpI = pieces.length - i - ((pieces.length % 2 == 1) ? 1 : 0);

			for (int j = 0; j < pieces[0].length; j++) {
				for (int k = 0; k < pieces[0][0].length; k++) {
					if (pieces[tmpI][j][k] == 1) {
						g.setColor(color);
						g.fillRect(xOffset + colSize * k, yOffset + rowSize * j, rowSize, colSize);
						g.setColor(Color.black);
						g.drawRect(xOffset + colSize * k, yOffset + rowSize * j, rowSize, colSize);

					}
				}
			}
		}
	}
}