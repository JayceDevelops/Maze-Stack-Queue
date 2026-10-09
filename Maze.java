import java.util.Random;
import java.util.Stack;

public class Maze {

    private final int ROWS = 10;
    private final int COLS = 10;
    private final Cell[][] maze;
    private int[] start, end = new int[2];
    private int distance;

    private Stack<Cell> coordinates = new Stack<>();

    public Maze(){
        maze = generateGrid(ROWS, COLS);
        start = generateCoordniates();
        end = generateCoordniates();
        distance = 0;

        coordinates.push(maze[start[0]][start[1]]);

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
                else if (row == end[0] && col == end[1]){
                    newCell.setIsWall(false);
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

    private Stack<Cell> solveMaze(){
        
        while (!coordinates.isEmpty()){

            Cell current = coordinates.pop();

            if (current.getCoordinates()[0] == end[0] && current.getCoordinates()[1] == end[1]){
                System.out.println("Solved.");
                return backtrack();
            }

            current.setVisited(true);
            distance++;

            int[] cellCords = current.getCoordinates();

            Cell north = maze[cellCords[0] - 1][cellCords[1]];
            Cell east = maze[cellCords[0]][cellCords[1] + 1];
            Cell south = maze[cellCords[0]][cellCords[1] + 1];
            Cell west = maze[cellCords[0]][cellCords[1] - 1];

            if (!north.isWall() && !north.wasVisited()){

                if(north.getCoordinates()[0] == end[0] && north.getCoordinates()[1] == end[1]){
                    System.out.println("Solved");
                    return backtrack();
                }

                coordinates.push(north);
                System.out.println("North: " + north.getCoordinates()[0] + ", " + north.getCoordinates()[1]);
                north.setDistance(distance);
            }

            if (!east.isWall() && !east.wasVisited()){

                if(east.getCoordinates()[0] == end[0] && east.getCoordinates()[1] == end[1]){
                    System.out.println("Solved");
                    return backtrack();
                }

                coordinates.push(east);
                System.out.println("East: " + east.getCoordinates()[0] + ", " + east.getCoordinates()[1]);
                east.setDistance(distance);
            }

            if (!south.isWall() && !south.wasVisited()){

                if(south.getCoordinates()[0] == end[0] && south.getCoordinates()[1] == end[1]){
                    System.out.println("Solved");
                    return backtrack();
                }

                coordinates.push(south);
                System.out.println("South: " + south.getCoordinates()[0] + ", " + south.getCoordinates()[1]);
                south.setDistance(distance);
            }

            if (!west.isWall() && !west.wasVisited()){

                if(west.getCoordinates()[0] == end[0] && west.getCoordinates()[1] == end[1]){
                    System.out.println("Solved");
                    return backtrack();
                }

                coordinates.push(west);
                System.out.println("West: " + west.getCoordinates()[0] + ", " + west.getCoordinates()[1]);
                west.setDistance(distance);
            }

            System.out.println(coordinates);
        }

        return new Stack<>();
    }

    private Stack<Cell> backtrack(){

        Stack<Cell> coords = new Stack<>();
        Cell current = maze[end[0]][end[1]];
        coords.push(current);

        while(current.getCoordinates()[0] != start[0] && current.getCoordinates()[1] != start[1]){

            int[] cellCords = current.getCoordinates();

            Cell north = maze[cellCords[0] - 1][cellCords[1]];
            Cell east = maze[cellCords[0]][cellCords[1] + 1];
            Cell south = maze[cellCords[0]][cellCords[1] - 1];
            Cell west = maze[cellCords[0]][cellCords[1] - 1];

            Cell lowest = null;
            lowest.setDistance(0);

            if (lowest.getDistance() > north.getDistance() && !north.isWall()){
                lowest = north;
            }

            if (lowest.getDistance() > east.getDistance() && !east.isWall()){
                lowest = east;
            }

            if (lowest.getDistance() > south.getDistance() && !south.isWall()){
                lowest = south;
            }

            if (lowest.getDistance() > west.getDistance() && !west.isWall()){
                lowest = west;
            }

            coords.push(lowest);
            current = coords.peek();
        }

        return coords;
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
                    output += "X";
                }
            }
            output += '\n';
        }

        return output;
    }
}