package cell;

public class GridConway extends AbstractGrid {

    public GridConway(int size) {
        super(size);
    }

    @Override
    protected int nextCellState(int i, int j) {
        int alive = countAliveNeighbors(i, j);
        int state = cells[i][j].getState();

        if (state == 1 && (alive == 2 || alive == 3)) return 1;  // survie
        else if (state == 0 && alive == 3) return 1;             // naissance
        else return 0;                                           // mort
    }
}
