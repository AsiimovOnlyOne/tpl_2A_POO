package cell.event;

import cell.grid.AbstractGrid;

/**
 * Événement périodique qui déclenche la méthode next() d'une grille.
 */
public class GridEvent extends Event {

    private final AbstractGrid grid;
    private final EventManager manager;
    private final int delay; // Délai entre deux générations (souvent 1)

    /**
     * @param date Date d'exécution de cet événement
     * @param grid La grille à mettre à jour (Conway ou Immigration)
     * @param manager Le gestionnaire (pour pouvoir se replanifier soi-même)
     */
    public GridEvent(long date, AbstractGrid grid, EventManager manager) {
        super(date);
        this.grid = grid;
        this.manager = manager;
        this.delay = 1; // Par défaut, on avance à chaque "tick"
    }

    @Override
    public void execute() {
        // 1. Action : Calculer la génération suivante de cellules
        grid.next();

        // 2. Récurrence : On ajoute un NOUVEL événement pour le tour suivant
        // Cela crée une boucle infinie tant que le simulateur tourne
        manager.addEvent(new GridEvent(this.getDate() + delay, grid, manager));
    }
}
