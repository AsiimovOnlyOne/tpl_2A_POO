package cell.grid;

import cell.Cell;

public class GridImmigration extends AbstractGrid {
    
    public GridImmigration(int size, int nStates) {
        super(size, nStates);
    }

    @Override
    protected int nextCellState(int i, int j) {

        int[] count = new int[nStates];
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) continue;
                int ni = (i + di + size) % size;
                int nj = (j + dj + size) % size;
                int s = cells[ni][nj].getState();
                count[s]++;
            }
        }

        int current = cells[i][j].getState();
        int next = current;
        int max = count[current];

        for (int k = 0; k < nStates; k++) {
            if (count[k] > max) {
                max = count[k];
                next = k;
            }
        }

        return next;
    }

    @Override
    public void generateGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.cells[i][j] = new Cell((int) (Math.random() * nStates));
            }
        }
    }
}

