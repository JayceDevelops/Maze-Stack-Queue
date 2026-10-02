import java.util.Stack;

public class Maze {

    private Cell[][] maze;
    private Stack coordinates;
    private int[] start, end;

    public Maze(){
        generateGrid(5, 5);
    }

    private Cell[][] generateGrid(int rows, int cols){
        Cell[][] grid = new Cell[rows][cols];

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){

                Cell newCell = new Cell(row, col, false, 0);

                if (row == 0 || col == 0 || row == rows - 1 || col == cols - 1){
                    newCell.setIsWall(true);
                }
                else {
                    double random = Math.random();

                    if (random > 0.5){
                        newCell.setIsWall(true);
                    }
                }

                grid[row][col] = newCell;
                System.out.println(newCell);
            }
        }

        return grid;
    }
}