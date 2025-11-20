package boid;

import boid.event.DrawBoids;
import boid.event.EventManager;
import boid.event.RestartBoids;
import boid.event.TranslateBoids;
import boid.model.Boids;
import boid.model.BoidsPredateur;
import boid.model.BoidsProies;
import gui.GUISimulator;
import gui.Simulable;
import java.awt.*;

/**
 * Classe BoidsSimulator qui implémente la classe Simulable
 * pour le modèle proies prédateurs
 */
public class BoidsSimulator implements Simulable {

    // pour la simulation graphique
    private final GUISimulator gui;

    // premiere famille de Boids, les prédateurs
    private final Boids boidsone;

    // deuxieme famille de Boids, les proies
    private final Boids boidstow;

    // getionaire des événements que l'on va simuler
    private final EventManager events;

    /** Création de la simulation */
    public BoidsSimulator(GUISimulator gui) {
        this.gui = gui;
        this.boidsone = new BoidsPredateur();
        boidsone.generateBoids(gui.getWidth(), gui.getHeight());
        this.boidstow = new BoidsProies();
        boidstow.generateBoids(gui.getWidth(), gui.getHeight());
        this.events = new EventManager();

        gui.setSimulable(this);
    }

    /** A chaque pas de la simulation, on translate 1 fois les prédateurs et 2 fois les proies puis on dessine le résultat */
    @Override
    public void next() {
        gui.reset();
        this.events.addEvent(new TranslateBoids(0, gui, boidsone, boidstow));
        this.events.next();
        this.events.addEvent(new TranslateBoids(0, gui, boidstow, boidsone));
        this.events.next();

        this.events.addEvent(new TranslateBoids(0, gui, boidstow, boidsone));
        this.events.next();

        this.events.addEvent(new DrawBoids(0, gui, boidsone));
        this.events.next();
        this.events.addEvent(new DrawBoids(0, gui, boidstow));
        this.events.next();

    }

    @Override
    public void restart() {
        // on ajoute un event de reset des boids et on les dessines
        this.events.addEvent(new RestartBoids(0, gui, boidsone));
        this.events.next();
        this.events.addEvent(new RestartBoids(0, gui, boidstow));
        this.events.next();
        this.events.addEvent(new DrawBoids(0, gui, boidsone));
        this.events.next();
        this.events.addEvent(new DrawBoids(0, gui, boidstow));
        this.events.next();
    }

    /** on lance la simulation */
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(1000, 1000, Color.WHITE);
        new BoidsSimulator(gui);
    }
}

