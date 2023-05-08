
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMenuListener implements ActionListener {

    private SudokuGame sudokuGame;
    private Sudoku sudoku;

    public MyMenuListener(SudokuGame sg,Sudoku s){
        this.sudokuGame = sg;
        this.sudoku = s;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd.equals("New Game")){
            sudokuGame.dispose();
            new SudokuGame();
        }
        if(cmd.equals("Reset Game")){
            sudoku.resetGame();
            sudoku.getProgress();
        }
        if(cmd.equals("Exit"))
            System.exit(0);
    }
}
