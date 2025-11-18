package cell;

/**
 * Représente une cellule unique dans la grille.
 * Elle ne connaît pas sa position, seulement son état courant.
 */
public class Cell {

    // L'état de la cellule.
    // 0 = Morte
    // 1, 2, 3... = Vivante (ou différentes couleurs pour le jeu de l'Immigration)
    private int state;

    /**
     * Constructeur
     * @param state L'état initial de la cellule.
     */
    public Cell(int state) {
        this.state = state;
    }

    /**
     * Accesseur (Getter)
     * Permet de lire l'état sans donner accès direct à la variable.
     */
    public int getState() {
        return state;
    }

    /**
     * Mutateur (Setter)
     * Permet de modifier l'état.
     */
    public void setState(int state) {
        // On pourrait ajouter ici une validation (ex: if state < 0 throw Error)
        this.state = state;
    }
}