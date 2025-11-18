package boid;

import gui.GUISimulator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class DrawBoids extends EventBoids{

    public DrawBoids(int date, GUISimulator gui, Boids boids) {
        super(date, gui, boids);
    }

    /** dessine la famille de boids */
    protected void draw() {
        Boids boids = super.getBoids();
        ArrayList<Point> p = boids.getCoordBoids();
        ArrayList<Point2D> v = boids.getSpeedBoids();
        for (int i = 0; i < boids.size(); i++) {
            super.getGui().addGraphicalElement(new EllipseOrientee(p.get(i).x, p.get(i).y, boids.gethauteur(), boids.getlargeur(), v.get(i).getX(), v.get(i).getY(), boids.getCouleur()));
        }
    }

    /** execution du dessin de la famille de boids*/
    public void execute(){
        draw();
    }
}
