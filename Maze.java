import java.util.Random;
import java.util.Stack;

public class Maze {

    private final int ROWS = 10;
    private final int COLS = 50;
    private final Cell[][] maze;
    private final int[] start, end;

    private Stack<Cell> coordinates;

    public Maze(){
        maze = generateGrid(ROWS, COLS);
        start = generateCoordniates();
        end = generateCoordniates();

        coordinates.push(maze[start[0]][start[1]]);

        // Sets the surrounding cells distance to 1
        maze[start[0] - 1][start[1]].setDistance(1);
        maze[start[0]][start[1] + 1].setDistance(1);
        maze[start[0]][start[1] - 1].setDistance(1);
        maze[start[0]][start[1] - 1].setDistance(1);

        solveMaze();
    }

    /**
     * 
     * @param rows
     * @param cols
     * @return: 2d Array representing the maze, has walls around the border and walls with in the array
     */
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
            }
        }

        return grid;
    }

    /**
     * 
     * @return: Random int array with two coordinates within the 2d array
     */
    private int[] generateCoordniates(){
        Random rand = new Random();
        return new int[] {rand.nextInt(ROWS - 2) + 1, rand.nextInt(COLS - 2) + 1};
    }

    private void solveMaze(){
            
        while (!coordinates.isEmpty()){

            Cell current = coordinates.pop();
            current.setVisited(true);

            int[] cellCords = current.getCoordinates();


            Cell north = maze[cellCords[0] - 1][cellCords[1]];
            Cell east = maze[cellCords[0]][cellCords[1] + 1];
            Cell south = maze[cellCords[0]][cellCords[1] - 1];
            Cell west = maze[cellCords[0]][cellCords[1] - 1];

            if (!north.isWall() && !north.wasVisited()){
                coordinates.push(north);
            }

            if (!east.isWall() && !east.wasVisited()){
                coordinates.push(east);
            }

            if (!south.isWall() && !south.wasVisited()){
                coordinates.push(south);
            }

            if (!west.isWall() && !west.wasVisited()){
                coordinates.push(west);
            }
        }
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