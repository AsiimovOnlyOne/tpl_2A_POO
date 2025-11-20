package cell.grid;

import cell.Cell;

public class GridConway extends AbstractGrid {

    /**
     * Constructeur.
     * Appelle le constructeur parent avec size et (implicitement) 2 états.
     */
    public GridConway(int size) {
        super(size);
    }

    /**
     * Implémentation des règles du Jeu de la Vie (B3/S23).
     * B3 : Birth (Naissance) si 3 voisins.
     * S23 : Survival (Survie) si 2 ou 3 voisins.
     */
    @Override
    protected int nextCellState(int i, int j) {
        int alive = countAliveNeighbors(i, j);
        int state = cells[i][j].getState();

        // Règle de Survie : Une cellule vivante reste vivante avec 2 ou 3 voisins
        if (state == 1 && (alive == 2 || alive == 3)) {
            return 1;
        }
        // Règle de Naissance : Une cellule morte devient vivante avec exactement 3 voisins
        else if (state == 0 && alive == 3) {
            return 1;
        }
        // Règle de Mort : Dans tous les autres cas (isolement <2 ou surpopulation >3)
        else {
            return 0;
        }
    }

    /**
     * Initialisation spécifique au Jeu de la Vie.
     */
    @Override
    public void generateGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // On remplit avec une densité d'environ 30% de cellules vivantes
                this.cells[i][j] = new Cell(Math.random() < 0.3 ? 1 : 0);
            }
        }
    }
}