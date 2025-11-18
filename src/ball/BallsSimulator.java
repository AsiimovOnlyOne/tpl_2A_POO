package ball;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;
import java.awt.*;

public class BallsSimulator implements Simulable {

    // Référence vers l'interface graphique (Vue)
    private final GUISimulator gui;

    // Rayon des balles (utilisé pour la logique ET l'affichage)
    private final int radius = 15;

    // Référence vers le gestionnaire de données (Modèle)
    private final Balls balls;

    public BallsSimulator(GUISimulator gui) {
        this.gui = gui;

        // 1. Instanciation du modèle
        this.balls = new Balls();

        // 2. Initialisation des données (création des points aléatoires)
        // Note : On passe la taille du GUI pour que les balles restent dedans
        balls.generateBalls(10, gui.getWidth(), gui.getHeight(), radius);

        // 3. Enregistrement du simulateur auprès du GUI.
        gui.setSimulable(this);

        // 4. Premier affichage (état initial)
        draw();
    }

    /**
     * Méthode d'affichage.
     * Stratégie : On efface tout et on redessine tout à chaque étape.
     */
    private void draw() {
        gui.reset(); // Vide le panneau graphique (supprime les anciens ovales)

        // Récupère les positions actuelles depuis le modèle
        for (Point p : balls.getCoordBalls()) {
            // Ajoute une forme graphique pour chaque balle
            // ATTENTION : Voir point "Vigilance" plus bas concernant les coordonnées
            gui.addGraphicalElement(new Oval(p.x, p.y, Color.BLACK, Color.RED, 2 * radius));
        }
    }

    /**
     * Appelé par le GUI à chaque "pas" de temps (timer) ou clic sur "Next".
     */
    @Override
    public void next() {
        // 1. Mise à jour du modèle (calcul des nouvelles positions)
        balls.translate(gui.getWidth(), gui.getHeight(), radius);
        // 2. Mise à jour de la vue
        draw();
    }

    /**
     * Appelé par le GUI lors du clic sur le bouton "Début" / "Restart".
     */
    @Override
    public void restart() {
        // 1. Restauration des positions initiales dans le modèle
        balls.reset();
        // 2. Mise à jour de la vue
        draw();
    }

    public static void main(String[] args) {
        // Configuration de la fenêtre (400x400, fond blanc)
        GUISimulator gui = new GUISimulator(400, 400, Color.WHITE);
        // Démarrage du contrôleur
        new BallsSimulator(gui);
    }
}