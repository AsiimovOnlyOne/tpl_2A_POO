package boid.event;

import boid.model.Boids;
import gui.GUISimulator;

/**
 * Sous classe d'evenement de Boid qui translate boid en prenant en compte les interaction avec l'autre famille otherboid
 */
public class TranslateBoids extends EventBoids {

    final Boids otherboid; // la famille de Boid qui cohabite avec boid

    public TranslateBoids(int date, GUISimulator gui, Boids boids, Boids otherboid) {
        super(date, gui, boids);
        this.otherboid = otherboid;
    }

    /** on translate */
    @Override
    public void execute(){
        super.getBoids().translate(super.getGui().getWidth(), super.getGui().getHeight(), otherboid);
    }
}
