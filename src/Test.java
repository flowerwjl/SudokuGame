
import java.util.Random;

public class Test {

    private final Random random = new Random();

    private int[][] puzzle = new int[9][9];

    private boolean[][] masks = new boolean[9][9];//initialized false
    private static int taskNum;

    public Test(){
        this.createRandomMatrix();
        this.createTasks();
    }

    public int[][] getPuzzle(){
        return this.puzzle;
    }
    public boolean[][] getMasks(){
        return this.masks;
    }

    public void createRandomMatrix(){

        for (int row = 0;row < puzzle.length;)
        {
            if(row == 0){
                this.puzzle[row] = buildRandomArray();
                row++;
            } else {
                this.puzzle[row] = buildRandomArray();
                boolean flag = true;
                for(int col = 0;col < puzzle.length;col++){
                    if(isNumValidInCol(puzzle[row][col],row,col) && isNumValidInBlock(puzzle[row][col],row,col))
                        continue;
                    else{
                        flag = false;
                        break;
                    }
                }
                if(flag)
                    row++;
            }
        }
    }

    public void createTasks(){
        for(int count = 0;count < taskNum;){
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if(masks[row][col])
                continue;
            masks[row][col] = true;
            count++;
        }
    }

    public int[] buildRandomArray(){
        int[] result = new int[9];
        int tmpRandom;
        for(int count = 0;count < result.length;){
            tmpRandom = random.nextInt(9) + 1;
            boolean flag = true;
            for (int j : result) {
                if (j == tmpRandom) {
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = tmpRandom;
                count++;
            }
        }
        return result;
    }

    public boolean isNumValidInRow(int number,int row,int col){
        for(int i = 0;i < puzzle.length;i++){
            if(i == col)
                continue;
            if(puzzle[row][i] == number)
                return false;
        }
        return true;
    }
    public boolean isNumValidInCol(int number,int row,int col){
        for(int i = 0;i < puzzle.length;i++){
            if(i == row)
                continue;
            if(puzzle[i][col] == number)
                return false;
        }
        return true;
    }
    public boolean isNumValidInBlock(int number,int row,int col){
        int subRow = row / 3;
        int subCol = col / 3;
        for(int i = subRow * 3;i < (subRow + 1) * 3;i++){
            for (int j = subCol * 3;j < (subCol + 1) * 3;j++){
                if(i == row && j == col)
                    continue;
                if(puzzle[i][j] == number)
                    return false;
            }
        }
        return true;
    }
    public boolean isNumValid(int number,int row,int col){
        return isNumValidInRow(number,row, col) && isNumValidInCol(number,row, col) && isNumValidInBlock(number,row, col);
    }

    public static void setTaskNum(int n){
        taskNum = n;
    }
    public int getTaskNum(){
        return taskNum;
    }
}
