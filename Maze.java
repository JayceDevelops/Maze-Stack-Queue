import java.util.*;

public class Maze {

    /* Colors used to print maze */
    private static final String RESET  = "\u001B[0m";
    private static final String GREEN  = "\u001B[32m";
    private static final String RED    = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GRAY   = "\u001B[90m";

    private final int ROWS = 30;
    private final int COLS = 30;

    private final Cell[][] maze;
    private int[] start, end;
    private Stack<Cell> path;
    int[][] DIRECTIONS = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };

    public Maze(String type) {
        start = generateCoordinates();
        
        do {
            end = generateCoordinates();
        } while (Arrays.equals(start, end));
        
        maze = generateGrid(ROWS, COLS);
    }

    /* Creates a 2D randomized maze for "Robot" to solve. NOTE: MAZE IS NOT ALWAYS SOLVEABLE */
    private Cell[][] generateGrid(int rows, int cols) {
        Cell[][] grid = new Cell[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                Cell cell = new Cell(row, col, false, 0);
                boolean border = row == 0 || col == 0 || row == rows - 1 || col == cols - 1;
                boolean isStartOrEnd = (row == start[0] && col == start[1]) || (row == end[0] && col == end[1]);

                if (border) {
                    cell.setIsWall(true);
                } else if (!isStartOrEnd && Math.random() < 0.5) {
                    cell.setIsWall(true);
                }
                grid[row][col] = cell;
            }
        }
        return grid;
    }

    /* Returns int array of two random coordinates on the maze. This method is used for the start and end coordinates. */
    private int[] generateCoordinates() {
        Random rand = new Random();
        return new int[] { rand.nextInt(ROWS - 2) + 1, rand.nextInt(COLS - 2) + 1 };
    }

    private void resetCells(){
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                maze[row][col].setVisited(false);
                maze[row][col].setDistance(0);
            }
        }
        path = null;
    }

    /* Breadth-first search using queue: guarantees the shortest path */ 
    public Stack<Cell> solveMazeQueue() {

        resetCells();

        Queue<Cell> queue = new LinkedList<>();

        Cell startCell = maze[start[0]][start[1]];
        startCell.setVisited(true);
        startCell.setDistance(0);
        queue.add(startCell);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            int row = current.getCoordinates()[0];
            int col = current.getCoordinates()[1];

            if (row == end[0] && col == end[1]) {
                System.out.println("Solved in " + current.getDistance() + " steps.");
                return path = backtrack();
            }

            for (int[] dir : DIRECTIONS) {
                int nextRow = row + dir[0], nextCol = col + dir[1];
                Cell next = maze[nextRow][nextCol];

                if (!next.isWall() && !next.wasVisited()) {
                    next.setVisited(true);          
                    next.setDistance(current.getDistance() + 1);
                    queue.add(next);
                }
            }
        }

        System.out.println("No path exists.");
        return new Stack<>();
    }

    /* Depth-first search using stack */ 
    public Stack<Cell> solveMazeStack() {

        resetCells();
        
        Stack<Cell> stack = new Stack<>();

        Cell startCell = maze[start[0]][start[1]];
        startCell.setVisited(true);
        startCell.setDistance(0);
        stack.push(startCell);

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            int row = current.getCoordinates()[0];
            int col = current.getCoordinates()[1];

            if (row == end[0] && col == end[1]) {
                System.out.println("Solved in " + current.getDistance() + " steps.");
                return path = backtrack();
            }

            for (int[] dir : DIRECTIONS) {
                int nextRow = row + dir[0], nextCol = col + dir[1];
                Cell next = maze[nextRow][nextCol]; 

                if (!next.isWall() && !next.wasVisited()) {
                    next.setVisited(true);          
                    next.setDistance(current.getDistance() + 1);
                    stack.push(next);
                }
            }
        }

        System.out.println("No path exists.");
        return new Stack<>();
    }

    /* Walks back from the end to the start by following decreasing distances */
    private Stack<Cell> backtrack() {
        Stack<Cell> result = new Stack<>();

        Cell current = maze[end[0]][end[1]];
        result.push(current);

        while (current.getDistance() > 0) {
            int row = current.getCoordinates()[0];
            int col = current.getCoordinates()[1];

            for (int[] dir : DIRECTIONS) {
                Cell next = maze[row + dir[0]][col + dir[1]];

                if (!next.isWall() && next.wasVisited() && next.getDistance() == current.getDistance() - 1) {
                    current = next;
                    result.push(current);
                    break;   
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String output = "";

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Cell cell = maze[row][col];

                if (row == start[0] && col == start[1])  
                    output += GREEN + "S ";
                else if (row == end[0] && col == end[1]) 
                    output += RED + "E ";
                else if (cell.isWall())                  
                    output += GRAY + "█ ";
                else if (path != null && path.contains(cell))          
                    output += YELLOW + "* ";
                else                                     
                    output += ". ";

                output += RESET;
            }
            output += '\n';
        }
        return output;
    }
}
