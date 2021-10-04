package puzzle_solver.gui;

import java.awt.event.*;
import javax.swing.*;

import puzzle_solver.solver.*;

public class gui {
	static final int width = 800;
	static final int height = 600;

	static final int lettersFieldWidth = width - width / 8;
	static final int lettersFieldOffsetX = width / 16;
	static final int lettersFieldOffsetY = 20 + width / 16;

	public static void main(String[] args) {
		JFrame f = new JFrame("Button Example");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);

		JLabel widthLabel = new JLabel("Width:");
		widthLabel.setBounds(0, height - 60, 60, 20);
		JTextField widthField = new JTextField();
		widthField.setBounds(70, height - 60, 60, 20);

		JLabel heightLabel = new JLabel("Height:");
		heightLabel.setBounds(140, height - 60, 60, 20);
		JTextField heightField = new JTextField();
		heightField.setBounds(140 + 70, height - 60, 60, 20);

		JLabel lettersFieldLabel = new JLabel("Pentominos:");
		lettersFieldLabel.setBounds(290, height - 60, 100, 20);
		JTextField lettersField = new JTextField();
		lettersField.setBounds(400, height - 60, 300, 20);

		JButton runButton = new JButton("RUN");
		runButton.setBounds(710, height - 60, 80, 20);

		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String letterString = lettersField.getText();
				String widthString = widthField.getText();
				String heightString = heightField.getText();

				lettersField.setText("");
				widthField.setText("");
				heightField.setText("");

				parse_and_run_input(letterString, widthString, heightString);
			}
		});

		f.add(lettersField);
		f.add(lettersFieldLabel);
		f.add(widthLabel);
		f.add(widthField);
		f.add(heightLabel);
		f.add(heightField);
		f.add(runButton);

		f.setSize(800, 600);
		f.setLayout(null);
		f.setVisible(true);
	}

	private static void parse_and_run_input(String lettersString, String widthString, String heightString) {
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
		Solver.create_and_solve(widthInt, heightInt, letters);

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