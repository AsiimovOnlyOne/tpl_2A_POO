package boid.event;

import boid.model.Boids;
import gui.GUISimulator;

/**
 * Sous classe d'événement pour une famille de boid pour factoriser le code
 */
public abstract class EventBoids extends Event {
    final GUISimulator gui;
    final Boids boids;

    public EventBoids(int date, GUISimulator gui, Boids boids) {
        super(date);
        this.gui = gui;
        this.boids = boids;
    }

    public GUISimulator getGui() {
        return gui;
    }

    public Boids getBoids() {
        return boids;
    }

    public abstract void execute();
}
