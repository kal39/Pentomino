package algorithm_x;

import utils.*;

public class XResult {
	public boolean result;
	public int[] rows;

	public XResult(Boolean inResult, int[] inRows) {
		result = inResult;
		rows = inRows;
	}

	public XResult(Boolean inResult) {
		result = inResult;
		rows = new int[0];
	}

	public XResult() {
		result = false;
		rows = new int[0];
	}

	public void add_row(int row) {
		System.out.print("Adding row " + row + " to ");
		ArrayUtils.print_1d_array(rows);
		rows = ArrayUtils.add_element_to_1d_array(rows, row);
	}

	public void add_row(int[] row) {
		for (int i = 0; i < row.length; i++) {
			rows = ArrayUtils.add_element_to_1d_array(rows, row[i]);
		}
	}
}
