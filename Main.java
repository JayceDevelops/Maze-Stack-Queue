public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze("queue");

        System.out.println();
        System.out.println("Queue: Breadth First");
        System.out.println("--------------------");
        maze.solveMazeQueue();
        System.out.println(maze);

        System.out.println("Stack: Depth First");
        System.out.println("--------------------");
        maze.solveMazeStack();
        System.out.println(maze);
    }
}
