package ball;

import ball.event.*;
import gui.GUISimulator;
import gui.Simulable;
import java.awt.Color;
import java.awt.Point;

public class BallsSimulator implements Simulable {

    private final GUISimulator gui;
    private final Balls balls;
    private final EventManager eventManager;
    private final int radius = 15;

    public BallsSimulator(GUISimulator gui) {
        this.gui = gui;
        this.balls = new Balls();
        this.eventManager = new EventManager(); // Création du gestionnaire

        balls.generateBalls(10, gui.getWidth(), gui.getHeight(), radius);

        // --- INITIALISATION DE L'ÉVÉNEMENT ---
        // On ajoute le tout premier événement de mouvement à la date 0
        eventManager.addEvent(new BallsMoveEvent(0, balls, eventManager, gui.getWidth(), gui.getHeight(), radius));

        gui.setSimulable(this);
        draw();
    }

    private void draw() {
        gui.reset();
        for (Point p : balls.getCoordBalls()) {
            gui.addGraphicalElement(new gui.Oval(p.x - radius, p.y - radius, Color.BLACK, Color.RED, 2 * radius));
        }
    }

    @Override
    public void next() {
        eventManager.next();

        // Puis on dessine le résultat
        draw();
    }

    @Override
    public void restart() {
        balls.reset();
        eventManager.reset();

        // IMPORTANT : On doit relancer la boucle d'événements après un reset
        eventManager.addEvent(new BallsMoveEvent(0, balls, eventManager, gui.getWidth(), gui.getHeight(), radius));

        draw();
    }

    public static void main(String[] args) {
        // Configuration de la fenêtre (400x400, fond blanc)
        GUISimulator gui = new GUISimulator(400, 400, Color.WHITE);
        // Démarrage du contrôleur
        new BallsSimulator(gui);
    }
}