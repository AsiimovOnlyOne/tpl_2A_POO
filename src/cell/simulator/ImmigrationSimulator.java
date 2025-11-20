package cell.simulator;

import cell.Cell;
import cell.event.EventManager;
import cell.event.GridEvent;
import cell.grid.GridImmigration;
import gui.GUISimulator;
import java.awt.*;


public class ImmigrationSimulator extends AbstractSimulator {

    private final GUISimulator gui;
    private final EventManager eventManager; // Ajout du gestionnaire

    public ImmigrationSimulator(GUISimulator gui) {
        // 1. Appelle le constructeur parent, qui fait gui.setSimulable(this)
        super(gui);

        // 2. Initialise les variables spécifiques à cette classe
        this.gui = gui;
        int NombreEtats = 4;
        super.grid = new GridImmigration(100, NombreEtats);

        // 3. Création et démarrage du gestionnaire d'événement
        this.eventManager = new EventManager();
        // On lance le premier événement à t=0
        this.eventManager.addEvent(new GridEvent(0, grid, eventManager));

        // 4. Premier affichage
        draw();
    }

    @Override
    protected void draw() {
        gui.reset(); // Efface la fenêtre avant de redessiner

        Cell[][] cells = grid.getGrid(); // Récupère la grille
        int size = grid.getSize();      // Taille du tableau (ex : 10 → 10x10)
        int cellSize = gui.getPanelWidth() / size; // Taille d'une cellule à l'écran
        int nStates = grid.getnStates(); //Nombre d'état du jeu possible

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = j * cellSize;
                int y = i * cellSize;
                Color color;

                // A chaque état est associé une couleur
                int state = cells[i][j].getState();
                float color_code = 1 - (float)state / (nStates-1);
                color = new Color(color_code, color_code, color_code);

                // Dessine un carré rempli pour représenter la cellule
                gui.addGraphicalElement(new gui.Rectangle(x, y, color, color, cellSize));
            }
        }
    }

    @Override
    public void next() {
        eventManager.next(); // Fait avancer le temps et exécute GridEvent
        draw();              // Dessine le résultat
    }

    @Override
    public void restart() {
        grid.reset();
        eventManager.reset();
        eventManager.addEvent(new GridEvent(0, grid, eventManager)); // On relance la machine
        draw();
    }

    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(400, 400, Color.WHITE);
        new ImmigrationSimulator(gui);
    }
}