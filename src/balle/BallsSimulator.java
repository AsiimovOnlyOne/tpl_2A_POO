package balle;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.awt.*;

public class BallsSimulator implements Simulable {

    private final GUISimulator gui;
    private final int radius = 15;
    private final Balls balls;

    public BallsSimulator(GUISimulator gui) {
        this.gui = gui;
        this.balls = new Balls();
        balls.generateBalls(10, gui.getWidth(), gui.getHeight(), radius);

        gui.setSimulable(this);
        draw();
    }

    private void draw() {
        gui.reset();
        for (Point p : balls.getCoordBalls()) {
            gui.addGraphicalElement(new Oval(p.x, p.y, Color.BLACK, Color.RED, 2 * radius));
        }

    }

    @Override
    public void next() {
        balls.translate(gui.getWidth(), gui.getHeight(), radius);
        draw();
    }

    @Override
    public void restart() {
        balls.reInit();
        draw();
    }

    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(400, 400, Color.WHITE);
        new BallsSimulator(gui);
    }
}

