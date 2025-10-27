package cell;

import gui.GUISimulator;
import java.awt.*;

// Note : "implements Simulable" n'est pas nécessaire ici,
// car AbstractSimulator l'implémente déjà.
public class ConwaySimulator extends AbstractSimulator {

    private final GUISimulator gui;

    public ConwaySimulator(GUISimulator gui) {
        // 1. Appelle le constructeur parent, qui fait gui.setSimulable(this)
        super(gui);

        // 2. Initialise les variables spécifiques à cette classe
        this.gui = gui;
        super.grid = new GridConway(100);

        // 3. Maintenant que tout est initialisé, on peut dessiner
        draw();
    }

    @Override
    protected void draw() {
        gui.reset(); // Efface la fenêtre avant de redessiner

        Cell[][] cells = grid.getGrid(); // Récupère la grille
        int size = grid.getSize();      // Taille du tableau (ex : 10 → 10x10)
        int cellSize = gui.getPanelWidth() / size; // Taille d'une cellule à l'écran

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = j * cellSize;
                int y = i * cellSize;
                Color color;

                // Si cellule vivante → noire, sinon → blanche
                if (cells[i][j].getState() == 1) {
                    color = Color.BLACK;
                } else {color = Color.WHITE;}

                // Dessine un carré rempli pour représenter la cellule
                gui.addGraphicalElement(new gui.Rectangle(x, y, color, color, cellSize));
            }
        }
    }

    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(400, 400, Color.WHITE);
        new ConwaySimulator(gui);
    }
}