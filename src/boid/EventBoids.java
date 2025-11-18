package boid;

import gui.GUISimulator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class EventBoids extends Event{
    /** on crée une classe d'evenement liée au Boids permettant de dessiner dans le simulateur pour factoriser le code*/

    private GUISimulator gui;
    private Boids boids;

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

    /** dessine la famille de boids */
    protected void draw() {
        ArrayList<Point> p = boids.getCoordBoids();
        ArrayList<Point2D> v = boids.getSpeedBoids();
        for (int i = 0; i < boids.size(); i++) {
            gui.addGraphicalElement(new EllipseOrientee(p.get(i).x, p.get(i).y, boids.gethauteur(), boids.getlargeur(), v.get(i).getX(), v.get(i).getY(), boids.getCouleur()));
        }
    }

    /** execution depend du type d'evenement effectué sur les Boids */
    public abstract void execute();
}
