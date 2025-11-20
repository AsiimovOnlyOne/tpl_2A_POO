package cell.simulator;

import cell.Cell;
import cell.grid.GridConway;
import gui.GUISimulator;
import gui.Rectangle; // Import explicite pour plus de clarté
import java.awt.*;

public class ConwaySimulator extends AbstractSimulator {

    // On doit stocker 'gui' ici car AbstractSimulator ne le stocke pas dans un champ protégé.
    // C'est nécessaire pour appeler gui.reset() et gui.addGraphicalElement() plus tard.
    private final GUISimulator gui;

    public ConwaySimulator(GUISimulator gui) {
        // 1. Configuration initiale via le parent (liaison des boutons Next/Restart)
        super(gui);

        this.gui = gui;

        // 2. Instanciation du modèle spécifique (Jeu de la Vie standard)
        // On utilise la variable 'grid' héritée de AbstractSimulator
        super.grid = new GridConway(100); // Création d'une grille 100x100

        // 3. Premier affichage
        draw();
    }

    @Override
    protected void draw() {
        gui.reset(); // Vide le panneau blanc

        Cell[][] cells = grid.getGrid();
        int size = grid.getSize();

        // Calcul dynamique de la taille des cases pour qu'elles remplissent toujours la fenêtre.
        // Si la fenêtre fait 400px et size=100, chaque cellule fera 4px.
        int cellSize = gui.getPanelWidth() / size;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                // --- Conversion Matrice vers Écran ---
                // i = ligne (axe vertical Y)
                // j = colonne (axe horizontal X)
                int x = j * cellSize;
                int y = i * cellSize;

                Color color;

                if (cells[i][j].getState() == 1) {
                    color = Color.BLACK; // Vivant
                } else {
                    color = Color.WHITE; // Mort
                }

                // Dessin de la cellule.
                gui.addGraphicalElement(new Rectangle(x, y, color, color, cellSize));
            }
        }
    }

    public static void main(String[] args) {
        // Création de la fenêtre
        GUISimulator gui = new GUISimulator(400, 400, Color.WHITE);
        // Lancement du contrôleur
        new ConwaySimulator(gui);
    }
}