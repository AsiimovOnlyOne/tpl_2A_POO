package boid;

import gui.GUISimulator;

public class TranslateBoids extends EventBoids{

    public TranslateBoids(int date, GUISimulator gui, Boids boids) {
        super(date, gui, boids);
    }

    /** on translate et on dessine */
    @Override
    public void execute(){
        super.getBoids().translate(super.getGui().getWidth(), super.getGui().getHeight());
        super.draw();
    }
}
