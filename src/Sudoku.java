
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.Border;

public class Sudoku extends JPanel {
	// Name-constants for the game properties
	public static final int GRID_SIZE = 9; // Size of the board
	public static final int SUBGRID_SIZE = 3; // Size of the sub-grid

	// Name-constants for UI control (sizes, colors and fonts)
	public static final int CELL_SIZE = 60; // Cell width/height in pixels
	public static final int CANVAS_WIDTH = CELL_SIZE * GRID_SIZE;
	public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE; // Board width/height in pixels
	public static final Color OPEN_CELL_BGCOLOR = new Color(217, 217, 217);
	public static final Color CLOSED_CELL_BGCOLOR = new Color(255, 255, 255);
	public static final Color CORRECT_BGCOLOR = Color.GREEN;
	public static final Color FILLED_CELL_BGCOLOR = Color.CYAN;
	public static final Color FILLING_CELL_BGCOLOR = Color.YELLOW;
	public static final Color WRONG_INPUT_BGCOLOR = Color.RED;
	public static final Color CLOSED_CELL_TEXT = Color.BLACK;
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

	private final JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];

	// Record the location of the cell selected by user
	private int rowSelected = -1;
	private int colSelected = -1;

	//Listeners
	ActionListener inputListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			findSelectedCell((JTextField) e.getSource());
			isSelectedCellInputValid();
			getProgress();
			isGameOver();
		}
	};
	KeyListener keyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getKeyChar() == KeyEvent.VK_ENTER)
				return;
			findSelectedCell((JTextField) e.getSource());
			isTyping();
			getProgress();
		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}
	};
	Test test = new Test();
	JLabel progressText = new JLabel();
	private int progressNum;

	public Sudoku() {

		this.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE)); // 9x9 GridLayout

		// Construct 9x9 JTextFields and add to the content-pane
		for (int row = 0; row < GRID_SIZE; ++row) {
			for (int col = 0; col < GRID_SIZE; ++col) {
				tfCells[row][col] = new JTextField();      // Allocate element of array
				this.add(tfCells[row][col]);               // ContentPane adds JTextField
				if (test.getMasks()[row][col]) {
					tfCells[row][col].setText("");         // set to empty string
					tfCells[row][col].setEditable(true);
					tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);

					tfCells[row][col].addActionListener(inputListener);
					tfCells[row][col].addKeyListener(keyListener);

				} else {
					tfCells[row][col].setText(test.getPuzzle()[row][col] + "");
					tfCells[row][col].setEditable(false);
					tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
					tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
				}
				// Beautify all the cells
				tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
				tfCells[row][col].setFont(FONT_NUMBERS);
			}
		}

		// Set the size of the content-pane and pack all the components under this container.
		this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		//Border Start
		Border gridBorder = BorderFactory.createLineBorder(Color.BLACK, 5);
		Border topAndLeftBorder = BorderFactory.createMatteBorder(3, 3, 1, 1, Color.BLACK);
		Border topBorder = BorderFactory.createMatteBorder(3, 1, 1, 1, Color.BLACK);
		Border leftBorder = BorderFactory.createMatteBorder(1, 3, 1, 1, Color.BLACK);
		Border centreBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
		Border bottomBorder = BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK);
		Border rightBorder = BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK);
		Border topAndRightBorder = BorderFactory.createMatteBorder(3, 1, 1, 3, Color.BLACK);
		Border leftAndBottomBorder = BorderFactory.createMatteBorder(1, 3, 3, 1, Color.BLACK);
		Border bottomAndRightBorder = BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK);

		for (int row = 0; row < Sudoku.GRID_SIZE; row++) {
			for (int col = 0; col < Sudoku.GRID_SIZE; col++) {
				if (row == 0 || row == 3 || row == 6)
					if (col == 0 || col == 3 || col == 6)
						tfCells[row][col].setBorder(topAndLeftBorder);
					else if (col != 8)
						tfCells[row][col].setBorder(topBorder);
					else
						tfCells[row][col].setBorder(topAndRightBorder);
				else if (row != 8)
					if (col == 0 || col == 3 || col == 6)
						tfCells[row][col].setBorder(leftBorder);
					else if (col != 8)
						tfCells[row][col].setBorder(centreBorder);
					else
						tfCells[row][col].setBorder(rightBorder);
				else if (col == 0 || col == 3 || col == 6)
					tfCells[row][col].setBorder(leftAndBottomBorder);
				else if (col != 8)
					tfCells[row][col].setBorder(bottomBorder);
				else
					tfCells[row][col].setBorder(bottomAndRightBorder);
			}
		}
		this.setBorder(gridBorder);


	}

	public void setProgressText(JLabel jl){
		this.progressText = jl;
	}

	public void findSelectedCell(JTextField cell) {
		// Scan JTextFiled for all rows and columns, and match with the source object
		this.rowSelected = -1;
		this.colSelected = -1;

		found:
		for (int row = 0; row < Sudoku.GRID_SIZE; ++row) {
			for (int col = 0; col < Sudoku.GRID_SIZE; ++col) {
				if (tfCells[row][col] == cell) {
					this.rowSelected = row;
					this.colSelected = col;
					break found;
				}
			}
		}
	}

	public void isSelectedCellInputValid() {
		if (this.rowSelected == -1 || this.colSelected == -1)
			return;
		String[] numbers = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		if (Arrays.binarySearch(numbers, tfCells[rowSelected][colSelected].getText()) >= 0) {
			tfCells[rowSelected][colSelected].setBackground(FILLED_CELL_BGCOLOR);
			//输入合法时添加光标失去聚焦
			tfCells[rowSelected][colSelected].transferFocus();
		} else {
			tfCells[rowSelected][colSelected].setBackground(WRONG_INPUT_BGCOLOR);
			JOptionPane.showMessageDialog(null, "Wrong input!Please re-enter.");
			tfCells[rowSelected][colSelected].setText("");
		}
	}

	public void isTyping() {
		if (this.rowSelected == -1 || this.colSelected == -1)
			return;
		tfCells[rowSelected][colSelected].setBackground(FILLING_CELL_BGCOLOR);
	}

	public void isGameOver() {
		boolean checkWin = true;
		for (int row = 0; row < Sudoku.GRID_SIZE; ++row)
			for (int col = 0; col < Sudoku.GRID_SIZE; ++col)
				if (test.getMasks()[row][col]) {
					if (tfCells[row][col].getText().equals(""))
						return;
					if (!tfCells[row][col].getBackground().equals(FILLED_CELL_BGCOLOR))
						return;
					else if (!test.isNumValid(Integer.parseInt(tfCells[row][col].getText()), row, col))
						checkWin = false;
				}
		if (checkWin) {
			for (int row = 0; row < Sudoku.GRID_SIZE; ++row)
				for (int col = 0; col < Sudoku.GRID_SIZE; ++col)
					if (test.getMasks()[row][col]) {
						tfCells[row][col].setBackground(CORRECT_BGCOLOR);
						tfCells[row][col].setEditable(false);
						tfCells[row][col].removeActionListener(inputListener);
						tfCells[row][col].removeKeyListener(keyListener);
					}
			JOptionPane.showMessageDialog(null, "Congratulation!You have correctly finished the sudoku game.");
		} else
			JOptionPane.showMessageDialog(null, "Some Mistakes !");
	}

	public void resetGame() {
		for (int row = 0; row < Sudoku.GRID_SIZE; ++row)
			for (int col = 0; col < Sudoku.GRID_SIZE; ++col)
				if (test.getMasks()[row][col]) {
					tfCells[row][col].setText("");
					tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
				}
	}

	public void getProgress(){
		int count = 0;
		for (int row = 0; row < Sudoku.GRID_SIZE; ++row)
			for (int col = 0; col < Sudoku.GRID_SIZE; ++col)
				if (test.getMasks()[row][col]) {
					if(tfCells[row][col].getBackground().equals(FILLED_CELL_BGCOLOR))
						count++;
				}
		progressNum = count;
		progressText.setText("Degree Of Completion:"+progressNum+"/"+test.getTaskNum());
	}
}
