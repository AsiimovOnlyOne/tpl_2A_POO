package cell.simulator;

import cell.Cell;
import cell.event.EventManager;
import cell.event.GridEvent;
import cell.grid.GridSchelling;
import gui.GUISimulator;
import java.awt.*;

/**
 * Simulateur graphique du modèle de ségrégation de Schelling.
 */
public class SchellingSimulator extends AbstractSimulator {

    /** Interface graphique utilisée pour l'affichage */
    private final GUISimulator gui;

    /** Gestionnaire d'événements */
    private final EventManager eventManager;
    
    /** Palette de couleurs pour représenter les différents groupes de population
     *  Index 0 : blanc (logement vacant)
     *  Index 1+ : couleurs des différents groupes */
    private final Color[] populationColors;

    /**
     * Constructeur du simulateur de Schelling.
     */
    public SchellingSimulator(GUISimulator gui) {
        super(gui);
        this.gui = gui;

        // Définition des couleurs : blanc pour vacant, puis rouge, bleu, vert pour les groupes
        this.populationColors = new Color[]{Color.WHITE, Color.RED, Color.BLUE, Color.GREEN};
        
        // Création de la grille : 50x50, 3 groupes de population, seuil de tolérance = 3
        this.grid = new GridSchelling(50, populationColors.length - 1, 3);

        // Création et démarrage du gestionnaire d'événement
        this.eventManager = new EventManager();
        // On lance le premier événement à t=0
        this.eventManager.addEvent(new GridEvent(0, grid, eventManager));

        // Affichage initial
        drawPopulationGrid();
    }

    /**
     * Dessine l'état actuel de la grille dans l'interface graphique.
     */
    @Override
    protected void draw() {
        drawPopulationGrid();
    }
    
    /**
     * Dessine la grille de population avec les couleurs appropriées.
     */
    private void drawPopulationGrid() {
        // Efface l'affichage précédent
        gui.reset();
        
        Cell[][] cells = grid.getGrid();
        int gridSize = grid.getSize();
        int cellPixelSize = gui.getPanelWidth() / gridSize;

        // Parcours de toutes les cellules pour les dessiner
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int populationType = cells[row][col].getState();
                Color cellColor = populationColors[populationType];
                
                // Dessine un rectangle pour cette cellule
                gui.addGraphicalElement(new gui.Rectangle(col * cellPixelSize, row * cellPixelSize, cellColor, cellColor, cellPixelSize));
            }
        }
    }

    /**
     * Passe à l'étape suivante de la simulation.
     */
    @Override
    public void next() {
        eventManager.next();  // Fait avancer le temps et exécute GridEvent
        drawPopulationGrid(); // Dessine le résultat
    }

    @Override
    public void restart() {
        grid.reset();
        eventManager.reset();
        eventManager.addEvent(new GridEvent(0, grid, eventManager)); // On relance la machine
        drawPopulationGrid();
    }

    /**
     * Point d'entrée principal pour tester le simulateur.
     */
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(600, 600, Color.WHITE);
        new SchellingSimulator(gui);
    }
}
