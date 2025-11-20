package cell.grid;

import cell.Cell;

public abstract class AbstractGrid {
    protected Cell[][] cells;
    protected Cell[][] initCells;
    protected int nStates;
    protected int size;

    /**
     * Constructeur par défaut (pour le Jeu de la Vie standard : 2 états)
     */
    public AbstractGrid(int size) {
        this(size, 2); // Appelle l'autre constructeur
    }

    /**
     * Constructeur principal
     */
    public AbstractGrid(int size, int nStates) {
        this.size = size;
        this.nStates = nStates;
        this.cells = new Cell[size][size];

        generateGrid(); // Remplit la grille aléatoirement
        setInit();      // Sauvegarde l'état initial
    }

    /**
     * Crée une copie profonde (Deep Copy) de la grille pour le bouton Reset.
     */
    public void setInit() {
        this.initCells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // On crée une nouvelle cellule avec le même état que l'actuelle
                this.initCells[i][j] = new Cell(cells[i][j].getState());
            }
        }
    }

    /**
     * Restaure la grille à son état initial.
     */
    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // On met à jour l'état sans recréer l'objet Cell (plus performant)
                cells[i][j].setState(initCells[i][j].getState());
            }
        }
    }

    /**
     * Méthode abstraite : C'est ici que la règle du jeu (Vie, Immigration, etc.) sera définie.
     */
    protected abstract int nextCellState(int i, int j);


    /**
     * Méthode abstraite : Génère une grille aléatoire en prenant en compte nStates.
     */
    public abstract void generateGrid();

    /**
     * Calcule la génération suivante (Mise à jour synchrone).
     */
    public void next() {
        Cell[][] newCells = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Calcul de l'état futur basé sur l'ancienne grille (this.cells)
                int nextState = nextCellState(i, j);
                newCells[i][j] = new Cell(nextState);
            }
        }
        // Remplacement de la grille actuelle par la nouvelle
        this.cells = newCells;
    }

    /**
     * Compte les voisins vivants (état > 0) autour de (i, j).
     * Utilise une topologie torique (bords connectés).
     */
    protected int countAliveNeighbors(int i, int j) {
        int count = 0;
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) continue;

                // Formule magique pour le tore (wrap-around)
                // Le + size avant le modulo gère les coordonnées négatives (-1 devient size-1)
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
