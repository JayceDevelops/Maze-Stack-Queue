import java.util.Set;

public class Cell {
    private int row, column, distance;
    private boolean wall, visited;
    
    public Cell(int row, int col, boolean wall, int dist) {
        setCoordinates(row, col);
        setDistance(dist);
        setIsWall(wall);
    }

    public final void setCoordinates(int row, int col){
        this.row = row;
        this.column = col;
    }

    public final void setDistance(int dist){
        this.distance = dist;
    }

    public final void setIsWall(boolean isWall){
        this.wall = isWall;
    }

    /**
     * 
     * @returns a Set type, that contains the coordinates the cell is located at
     */
    public Set getCoordinates(){
        return Set.of(this.row, this.column);
    }

    /**
     * 
     * @returns a int type, of the distance the cell is from the start
     */
    public int getDistance() {
        return this.distance;
    }

    public boolean isWall(){
        return this.wall;
    }

    public boolean wasVisited() {
        return this.visited;
    }

    @Override 
    public String toString(){
        return String.format("Coordinates: (%d, %d) | From Start: %d | Wall: %B", this.row, this.column, this.distance, this.wall);
    }
}
