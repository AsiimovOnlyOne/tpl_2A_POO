package cell;

public class Cell {

    boolean state;

    public Cell(boolean state) {
        this.state = state;
    }

    public boolean isAlive() {
        return state;
    }
}
