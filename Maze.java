import java.util.Random;
import java.util.Stack;
import java.util.Set;

public class Maze {

    private Cell[][] maze;
    private Stack coordinates;
    private int[] start, end;

    public Maze(){
        maze = generateGrid(10, 10);
        start = generateCoordniates();
        end = generateCoordniates();
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

                    if (random < 0.5){
                        newCell.setIsWall(true);
                    }
                }

                grid[row][col] = newCell;
                System.out.println(newCell);
            }
        }

        return grid;
    }

    private int[] generateCoordniates(){
        Random rand = new Random();
        return new int[] {rand.nextInt(10 - 2) + 1, rand.nextInt(10 - 2) + 1};
    }

    @Override 
    public String toString(){

        String output = "";
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
            
                if (start[0] == row && start[1] == col){
                    output += "S";
                }
                else if (end[0] == row && end[1] == col){
                    output += "E";
                }
                else if (maze[row][col].isWall()) {
                    output += "W";
                }
                else {
                    output += " ";
                }
            }
            output += '\n';
        }

        return output;
    }
}