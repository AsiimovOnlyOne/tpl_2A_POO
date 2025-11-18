package cell;

public abstract class AbstractGrid {
    protected Cell[][] cells;
    protected Cell[][] initCells;
    protected int nStates;
    protected int size;

    public AbstractGrid(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        generateGrid();
        setInit();
    }

    public AbstractGrid(int size, int nStates) {
        this.size = size;
        this.nStates = nStates;
        this.cells = new Cell[size][size];
        generateGrid();
        setInit();
    }

    public void setInit() {
        this.initCells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.initCells[i][j] = new Cell(cells[i][j].getState());
            }
        }
    }

    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j].setState(initCells[i][j].getState());
            }
        }
    }

    protected abstract int nextCellState(int i, int j);

    // ⚙️ Évolution d'une génération
    public void next() {
        Cell[][] newCells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newCells[i][j] = new Cell(nextCellState(i, j));
            }
        }
        this.cells = newCells;
    }

    public void generateGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.cells[i][j] = new Cell(Math.random() < 0.3 ? 1 : 0);
            }
        }
    }

    protected int countAliveNeighbors(int i, int j) {
        int count = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) continue;
                int ni = (i + di + size) % size;
                int nj = (j + dj + size) % size;
                if (cells[ni][nj].getState() > 0) count++;
            }
        }
        return count;
    }

    public Cell[][] getGrid() {
        return cells;
    }

    public int getSize() {
        return size;
    }

    public int getnStates() {
        return nStates;
    }   
}
