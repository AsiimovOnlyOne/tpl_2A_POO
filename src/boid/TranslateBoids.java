package boid;

import gui.GUISimulator;

public class TranslateBoids extends EventBoids{

    private Boids otherboid; // la famille de Boid qui cohabite avec boid

    public TranslateBoids(int date, GUISimulator gui, Boids boids, Boids otherboid) {
        super(date, gui, boids);
        this.otherboid = otherboid;
    }

    /** on translate et on dessine */
    @Override
    public void execute(){
        super.getBoids().translate(super.getGui().getWidth(), super.getGui().getHeight(), otherboid);
    }
}
