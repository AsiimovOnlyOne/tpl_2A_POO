package boid;

import gui.GUISimulator;
import gui.Simulable;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BoidsSimulator implements Simulable {

    private final GUISimulator gui;
    private final Boids boidsone;
    private final Boids boidstow;
    private final EventManager events;

    public BoidsSimulator(GUISimulator gui) {
        this.gui = gui;
        this.boidsone = new BoidsPredateur();
        boidsone.generateBoids(gui.getWidth(), gui.getHeight());
        this.boidstow = new BoidsProies();
        boidstow.generateBoids(gui.getWidth(), gui.getHeight());
        this.events = new EventManager();

        gui.setSimulable(this);
    }

    @Override
    public void next() {
        /** on ajoute un event de translation des boids et on l'execute*/
        gui.reset();
        this.events.addEvent(new TranslateBoids(0, gui, boidsone, boidstow));
        this.events.next();
        this.events.addEvent(new TranslateBoids(0, gui, boidstow, boidsone));
        this.events.next();

        this.events.addEvent(new DrawBoids(0, gui, boidsone));
        this.events.next();
        this.events.addEvent(new DrawBoids(0, gui, boidstow));
        this.events.next();

        gui.reset();
        this.events.addEvent(new TranslateBoids(0, gui, boidstow, boidsone));
        this.events.next();

        this.events.addEvent(new DrawBoids(0, gui, boidsone));
        this.events.next();
        this.events.addEvent(new DrawBoids(0, gui, boidstow));
        this.events.next();

    }

    @Override
    public void restart() {
        /** on ajoute un event de reset des boids et on les dessines*/
        this.events.addEvent(new RestartBoids(0, gui, boidsone));
        this.events.next();
        this.events.addEvent(new RestartBoids(0, gui, boidstow));
        this.events.next();
        this.events.addEvent(new DrawBoids(0, gui, boidsone));
        this.events.next();
        this.events.addEvent(new DrawBoids(0, gui, boidstow));
        this.events.next();
    }

    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(1000, 1000, Color.WHITE);
        new BoidsSimulator(gui);
    }
}

