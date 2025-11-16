package boid;

import gui.GUISimulator;
import gui.GraphicalElement;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.*;

public class EllipseOrientee implements GraphicalElement {

    private double cx, cy;    // centre
    private double w, h;      // largeur/hauteur
    private double angle;     // en radians
    private Color color;

    public EllipseOrientee(double cx, double cy, double w, double h,
                           double vx, double vy, Color color) {
        this.cx = cx;
        this.cy = cy;
        this.w = w;
        this.h = h;
        this.angle = Math.atan2(vy, vx);  // orientation
        this.color = color;
    }

    @Override
    public void paint(Graphics2D g) {

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // ellipse non orientée
        Ellipse2D ellipse = new Ellipse2D.Double(cx - w/2, cy - h/2, w, h);

        // rotation autour du centre
        AffineTransform at = new AffineTransform();
        at.rotate(angle, cx, cy);

        Shape rotated = at.createTransformedShape(ellipse);

        // remplissage
        g.setColor(color);
        g.fill(rotated);

        // contour
        g.setColor(Color.BLACK);
        g.draw(rotated);
    }
}