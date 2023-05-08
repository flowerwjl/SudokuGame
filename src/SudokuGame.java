
import javax.swing.*;
import java.awt.*;

public class SudokuGame extends JFrame{
	public SudokuGame() {

		new DifficultyButton();
		this.setLayout(new BorderLayout());
		Sudoku sudoku = new Sudoku();

		//Menu settings
		JMenu jMenu = new JMenu("File");

		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem resetGame = new JMenuItem("Reset Game");
		JMenuItem exit = new JMenuItem("Exit");

		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.add(jMenu);

		jMenu.add(newGame);
		jMenu.add(resetGame);
		jMenu.addSeparator();
		jMenu.add(exit);

		MyMenuListener myMenuListener = new MyMenuListener(this,sudoku);

		newGame.addActionListener(myMenuListener);
		resetGame.addActionListener(myMenuListener);
		exit.addActionListener(myMenuListener);

		//Label(progress bar) settings
		JLabel progressBar = new JLabel();
		progressBar.setText("Degree Of Completion:0/"+sudoku.test.getTaskNum());
		sudoku.setProgressText(progressBar);

		this.add(sudoku,BorderLayout.CENTER);
		this.add(progressBar,BorderLayout.SOUTH);
		this.setJMenuBar(jMenuBar);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setTitle("Sudoku");
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new SudokuGame();
   }

}
