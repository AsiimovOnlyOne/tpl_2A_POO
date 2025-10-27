package cell;

import gui.GUISimulator;
import gui.Simulable;
import java.awt.*;

public class ConwaySimulator implements Simulable {

    private final GUISimulator gui;
    private final GridConway grid;

    public ConwaySimulator(GUISimulator gui) {
        this.gui = gui;
        this.grid = new GridConway(100);
        grid.generateGrid();

        gui.setSimulable(this);
        draw();
    }

    private void draw() {
        gui.reset(); // Efface la fenêtre avant de redessiner

        Cell[][] cells = grid.getGrid(); // Récupère la grille
        int size = grid.getSize();      // Taille du tableau (ex : 10 → 10x10)
        int cellSize = gui.getPanelWidth() / size; // Taille d'une cellule à l'écran

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                // Si cellule vivante → blanche, sinon → gris foncé
                Color color = (cells[i][j].isAlive()) ? Color.BLACK : Color.WHITE;

                // Dessine un carré rempli pour représenter la cellule
                gui.addGraphicalElement(new gui.Rectangle(x, y, color, color, cellSize));
            }
        }
    }


    @Override
    public void next() {
        grid.next();
        draw();
    }

    @Override
    public void restart() {
        grid.reset();
        draw();
    }

    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(400, 400, Color.WHITE);
        new ConwaySimulator(gui);
    }
}