package boid;

import gui.GUISimulator;
import gui.Simulable;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BoidsSimulator implements Simulable {

    private final GUISimulator gui;
    private final int radius = 15;
    private final Boids boids;

    public BoidsSimulator(GUISimulator gui) {
        this.gui = gui;
        this.boids = new Boids();
        boids.generateBoids(10, gui.getWidth(), gui.getHeight(), radius);

        gui.setSimulable(this);
        draw();
    }

    private void draw() {
        gui.reset();
        ArrayList<Point> p = boids.getCoordBoids();
        ArrayList<Point2D> v = boids.getSpeedBoids();
        for (int i = 0; i < boids.size(); i++) {
            gui.addGraphicalElement(new EllipseOrientee(p.get(i).x, p.get(i).y, radius, 2*radius, v.get(i).getX(), v.get(i).getY(), Color.RED));
        }
    }

    @Override
    public void next() {
        boids.translate(gui.getWidth(), gui.getHeight(), radius);
        draw();
    }

    @Override
    public void restart() {
        boids.reset();
        draw();
    }

    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(1000, 1000, Color.WHITE);
        new BoidsSimulator(gui);
    }
}

