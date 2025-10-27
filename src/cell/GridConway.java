package cell;

import java.util.Arrays;
import java.util.Random;

public class GridConway {
    private Cell[][] cells;
    private Cell[][] initCells;
    private final int size;

    // --- Constructeurs ---

    public GridConway(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        initRandom(); // initialise aléatoirement
    }

    public void setInit(){
        this.initCells = new Cell[cells.length][];

        for (int i = 0; i < cells.length; i++) {
            this.initCells[i] = Arrays.copyOf(cells[i], cells[i].length);
        }
    }

    public void reset(){
        if (initCells == null) return; // sécurité

        this.cells = new Cell[initCells.length][];
        for (int i = 0; i < initCells.length; i++) {
            this.cells[i] = Arrays.copyOf(initCells[i], initCells[i].length);
        }
    }

    public void generateGrid(){
        initRandom();
        setInit();
    }

    // --- Initialisation aléatoire ---
    public void initRandom() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new Cell(random.nextBoolean());
            }
        }
    }

    // --- Calcul du nombre de voisines vivantes ---
    private int countAliveNeighbors(int i, int j) {
        int count = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) continue; // ignore la cellule elle-même
                int ni = i + di;
                int nj = j + dj;
                if (ni >= 0 && ni < size && nj >= 0 && nj < size && cells[ni][nj].isAlive()) {
                    count++;
                }
            }
        }
        return count;
    }

    // --- Passage au temps t+1 ---
    public void next() {
        Cell[][] nextGen = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int neighbors = countAliveNeighbors(i, j);
                boolean nextState;

                if (cells[i][j].isAlive()) {
                    nextState = (neighbors == 2 || neighbors == 3);
                } else {
                    nextState = (neighbors == 3);
                }

                nextGen[i][j] = new Cell(nextState);
            }
        }

        this.cells = nextGen;
    }

    // --- Accesseurs utiles ---
    public int getSize() {
        return size;
    }

    // --- Représentation texte de la grille ---
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(cells[i][j].isAlive() ? "⬛" : "⬜");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Cell[][] getGrid() {
        return this.cells;
    }
}