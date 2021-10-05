package puzzle_solver.gui;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
// import java.util.Random;

import puzzle_solver.algorithm_x.solver.*;

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
		JFrame f = new JFrame("Button Example");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);

		JLabel widthLabel = new JLabel("Width:");
		widthLabel.setBounds(10, height - 60, 60, 20);
		JTextField widthField = new JTextField("12");
		widthField.setBounds(70, height - 60, 60, 20);

		JLabel heightLabel = new JLabel("Height:");
		heightLabel.setBounds(140, height - 60, 60, 20);
		JTextField heightField = new JTextField("5");
		heightField.setBounds(140 + 70, height - 60, 60, 20);

		JLabel lettersFieldLabel = new JLabel("Pentominos:");
		lettersFieldLabel.setBounds(290, height - 60, 100, 20);
		JTextField lettersField = new JTextField("X I Z T U V W Y L P N F");
		lettersField.setBounds(400, height - 60, 300, 20);

		JButton runButton = new JButton("RUN");
		runButton.setBounds(710, height - 60, 80, 20);

		Drawing canvas = new Drawing();
		canvas.setSize(width, height - 65);
		canvas.height = height - 65;
		canvas.width = width;

		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String letterString = lettersField.getText();
				String widthString = widthField.getText();
				String heightString = heightField.getText();

				f.remove(canvas);
				canvas.pieces = parse_and_run_input(letterString, widthString, heightString);
				f.add(canvas);
			}
		});

		f.add(lettersField);
		f.add(lettersFieldLabel);
		f.add(widthLabel);
		f.add(widthField);
		f.add(heightLabel);
		f.add(heightField);
		f.add(runButton);

		f.add(canvas);

		f.setSize(800, 600);
		f.setLayout(null);
		f.setVisible(true);
		f.getRootPane().setDefaultButton(runButton);
	}

	private static int[][][] parse_and_run_input(String lettersString, String widthString, String heightString) {
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

	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width, height);

		if (pieces.length != 0) {
			// Random r = new Random();

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
}